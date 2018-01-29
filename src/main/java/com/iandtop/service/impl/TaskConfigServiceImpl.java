package com.iandtop.service.impl;

import com.iandtop.dao.*;
import com.iandtop.model.CompanyModel;
import com.iandtop.model.DeptModelVO;
import com.iandtop.model.StaffModel;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.model.sync.SyncDeptModel;
import com.iandtop.model.system.RoleModel;
import com.iandtop.model.task.TaskConfigModel;
import com.iandtop.model.task.TaskLogModel;
import com.iandtop.service.TaskConfigService;
import com.iandtop.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lz on 2017/7/20.
 */
@Service
public class TaskConfigServiceImpl implements TaskConfigService {
    private static final Logger logger = LoggerFactory.getLogger(TaskConfigServiceImpl.class);

    @Autowired
    private CompanyDAO companyDAO;

    @Autowired
    private TaskLogDAO taskLogDAO;

    @Autowired
    private TaskConfigDAO taskConfigDAO;

    @Autowired
    private DeptDAO deptDAO;

    @Autowired
    private StaffDAO staffDAO;

    @Bean(name = "oracleConnection")
    @Lazy
    public Connection getOracleConnection(String url, String user, String password) {
        Connection conn = BaseUtils.getOrclConn(url, user, password);
        return conn;
    }

    @Transactional
    @Override
    public APIRestResponse Sync(String company_code, String url, String user, String password) throws Exception {

        TaskLogModel taskLogModel;
        Integer result;
        TaskConfigModel taskConfigModel;
        // Connection conn = (Connection) ApplicationUtil.getBean("oracleConnection",url,user,password);
        Connection conn = getOracleConnection(url, user, password);

        long t1 = System.currentTimeMillis();
        logger.info("Begin, start={}", t1);
        List<CompanyModel> companyModels = companyDAO.retrieveByCode(company_code);
        List<TaskConfigModel> taskConfigModels = taskConfigDAO.retrieveAll();
        long t2 = System.currentTimeMillis();
        logger.warn("TaskConfigModel end.Cost={}", t2 - t1);

        if ((taskConfigModels == null || taskConfigModels.size() == 0)) {
            taskLogModel = new TaskLogModel();
            taskLogModel.setLog_ts(DateUtils.currentDatetime());
            taskLogModel.setResult("N");
            taskLogModel.setMemo("同步失败,配置信息错误!");
            taskLogDAO.insertByMo(taskLogModel);
            return ResponseUtils.getSuccessAPI(false, "同步失败,配置信息错误!", RestOperateCode.INSERT_DATA);
        }

        if (taskConfigModels.size() > 1) {
            taskLogModel = new TaskLogModel();
            taskLogModel.setLog_ts(DateUtils.currentDatetime());
            taskLogModel.setResult("N");
            taskLogModel.setMemo("同步失败,配置信息错误!");
            taskLogDAO.insertByMo(taskLogModel);
            return ResponseUtils.getSuccessAPI(false, "同步失败,配置信息错误!", RestOperateCode.INSERT_DATA);
        }

        if (companyModels == null || companyModels.size() == 0) {
            taskLogModel = new TaskLogModel();
            taskLogModel.setLog_ts(DateUtils.currentDatetime());
            taskLogModel.setResult("N");
            taskLogModel.setMemo("未找到公司信息!");
            taskLogDAO.insertByMo(taskLogModel);
            return ResponseUtils.getSuccessAPI(false, "未找到公司信息", RestOperateCode.INSERT_DATA);
        }


        try {

            if(conn == null){
                logger.info("------没有任何可用数据-1----");
                return ResponseUtils.getSuccessAPI(false, "系统内部错误", RestOperateCode.INSERT_DATA);
            }
            Statement stmt = conn.createStatement();

            //获取部门信息
            String getDeptSql = "select * from t_org_view";

            ResultSet resultSetDept = stmt.executeQuery(getDeptSql);
            long t3 = System.currentTimeMillis();
            logger.warn("Get ORGs end.Cost={}", t3 - t2);

            List<DeptModelVO> deptVOs = new ArrayList<DeptModelVO>();
            DeptModelVO deptVO;
            String department_code;
            String department_name;
            String parent_code;
            boolean flag = false;
            while (resultSetDept.next()) {

                department_code = resultSetDept.getString("B0110_0");
                department_name = resultSetDept.getString("B0110");
                parent_code = resultSetDept.getString("PARENTID");

                //如果根节点的parent有值,则显示不出来,和我们的写法有关系
                if (department_code.equals(parent_code)) {
                    parent_code = "";
                }

                /**
                 * 校验部门信息,
                 *  1:校验部门是否重复,没有则插入
                 *  2:如果已经存在相同部门,校验部门信息是否有改动(部门名称,等等)
                 */
                //TODO 优化1
                deptVO = deptDAO.retrieveByDeptCode(department_code);

                if(department_name == null) {
                    logger.info("------没有任何可用数据-----");
                    return ResponseUtils.getSuccessAPI(false, "部门名称信息为空，请检查同步信息", RestOperateCode.INSERT_DATA);
                }else{
                    //截取/后面的字符
                    department_name = department_name.substring(department_name.indexOf("/") + 1);
                    logger.info("-1-/截取/后面的字符---{}--------", department_name);
                }


                if (deptVO == null || deptVO.getPk_department() == null) {
                    deptVO = new DeptModelVO();
                    deptVO.setCompany_code(company_code);
                    deptVO.setDepartment_code(department_code);
                    //截取改动
                    deptVO.setDepartment_name(department_name);
                    deptVO.setParent_code(parent_code);
                    deptVOs.add(deptVO);
                } else {
                    if (!department_name.equals(deptVO.getDepartment_name())) {
                        //截取改动
                        deptVO.setDepartment_name(department_name);
                        flag = true;
                        logger.info("-2-//截取改动--{}--------", department_name);
                    }
                    if (!parent_code.equals(deptVO.getParent_code())) {
                        deptVO.setParent_code(parent_code);
                        flag = true;
                    }
                    if (flag) {
                        result = deptDAO.updateByPk(deptVO);
                        if (result < 1) {
                            taskLogModel = new TaskLogModel();
                            taskLogModel.setLog_ts(DateUtils.currentDatetime());
                            taskLogModel.setResult("N");
                            taskLogModel.setMemo("系统内部错误!");
                            taskLogDAO.insertByMo(taskLogModel);
                            return ResponseUtils.getSuccessAPI(false, "系统内部错误", RestOperateCode.INSERT_DATA);
                        }
                    }
                    flag = false;
                }
            }//end of while

            if (deptVOs.size() > 0) {
                result = deptDAO.addBatch(deptVOs);
                if (result < 1) {
                    taskLogModel = new TaskLogModel();
                    taskLogModel.setLog_ts(DateUtils.currentDatetime());
                    taskLogModel.setResult("N");
                    taskLogModel.setMemo("系统内部错误!");
                    taskLogDAO.insertByMo(taskLogModel);
                    return ResponseUtils.getSuccessAPI(false, "系统内部错误", RestOperateCode.INSERT_DATA);
                }
            }
            long t4 = System.currentTimeMillis();
            logger.warn("Insert ORG end, cost={}", t4 - t3);

            //获取人事信息
            List<StaffModel> staffModels = new ArrayList<StaffModel>();
            List<StaffModel> checkStaffModels;
            StaffModel staffModel;
            String getPsnSql = "select * from t_hr_view WHERE A01CK  IS NOT NULL";
            ResultSet resultSetStaff = stmt.executeQuery(getPsnSql);

            if(resultSetStaff == null) {
                logger.info("------没有任何可用数据-----");
                return ResponseUtils.getSuccessAPI(false, "系统内部错误", RestOperateCode.INSERT_DATA);
            }

            String staff_code = "";
            String staff_name = "";
            String id_card = "";
            String sex = "";
            String leave_data = "";
            Integer sys_flag;
            String wwjid = "";
            String def1 = "";
            while (resultSetStaff.next()) {
                department_code = resultSetStaff.getString("E0122_0");
                if(department_code == null || department_code.trim().length()<0){
                    department_code = resultSetStaff.getString("B0110_0");
                }
                staff_code = resultSetStaff.getString("A01ck");
                staff_name = resultSetStaff.getString("A0101");
                id_card = resultSetStaff.getString("A0177");
                sys_flag = resultSetStaff.getInt("SYS_FLAG");
                leave_data = resultSetStaff.getString("A6605");
                def1 = resultSetStaff.getString("B0110_0");
                if (leave_data != null && leave_data.trim().length() > 0) {
                    leave_data = DateUtils.formatDate(DateUtils.parseDate(leave_data));
                }
                wwjid = resultSetStaff.getString("A0100");
                sex = resultSetStaff.getString("A0107");

                /**
                 * 校验人事信息:
                 *  1:根据宏景人事表提供的员工id校验人员是否重复,没有则插入,注意:需要插入到对应部门下面
                 *  2:如果重复,校验人员基本信息是否有改动(人员姓名，编码,等等)
                 *  3:  sys_flag = 0 已同步
                 *               = 1 新增
                 *               = 2 更新
                 *               = 3 删除
                 */
                checkStaffModels = staffDAO.retrieveByIDCard(wwjid);

                if (checkStaffModels == null || checkStaffModels.size() == 0) {
                    staffModel = new StaffModel();
                    staffModel.setDepartment_code(department_code);
                   // staffModel.setStaff_type(sys_flag == 3 ? StaffModel.Staff_Type_LeaveJob : StaffModel.Staff_Type_OnJob);
                    staffModel.setStaff_type(leave_data != null ? StaffModel.Staff_Type_LeaveJob : StaffModel.Staff_Type_OnJob);
                    staffModel.setCompany_code(company_code);
                    staffModel.setStaff_code(staff_code);
                    staffModel.setStaff_name(staff_name);
                    staffModel.setId_card(id_card);
                    staffModel.setLeave_date(leave_data);
                    staffModel.setSex(sex);
                    staffModel.setWwjid(wwjid);
                    staffModel.setDef1(def1);
                    staffModels.add(staffModel);

                } else if (checkStaffModels != null && checkStaffModels.size() == 1) {

                    if (sys_flag == 3) {
                        //删除
                        staffModel = checkStaffModels.get(0);
                        staffModel.setStaff_type(StaffModel.Staff_Type_LeaveJob);
                        result = staffDAO.updateByPk(staffModel);
                        if (result < 1) {
                            taskLogModel = new TaskLogModel();
                            taskLogModel.setLog_ts(DateUtils.currentDatetime());
                            taskLogModel.setResult("N");
                            taskLogModel.setMemo("系统内部错误!");
                            taskLogDAO.insertByMo(taskLogModel);
                            return ResponseUtils.getSuccessAPI(false, "系统内部错误", RestOperateCode.INSERT_DATA);
                        }
                    } else {
                        //更新
                        staffModel = checkStaffModels.get(0);
                        if (department_code != null && !department_code.equals(staffModel.getDepartment_code())) {
                            staffModel.setDepartment_code(department_code);
                            flag = true;
                        }
                        if (leave_data != null) {
                            staffModel.setStaff_type(StaffModel.Staff_Type_LeaveJob);
                            staffModel.setLeave_date(leave_data);
                            flag = true;
                        }
                        if (leave_data == null) {
                            staffModel.setStaff_type(StaffModel.Staff_Type_OnJob);
                            staffModel.setLeave_date("");
                            flag = true;
                        }
                        if (staff_code != null && !staffModel.getStaff_code().equals(staff_code)) {
                            staffModel.setStaff_code(staff_code);
                            flag = true;
                        }
                        if (staff_name != null && !staffModel.getStaff_name().equals(staff_name)) {
                            staffModel.setStaff_name(staff_name);
                            flag = true;
                        }
                        if (def1 != null  ) {
//                            if(staffModel.getDef1() != null){
//                                if(!staffModel.getDef1().equals(def1)){
//
//                                }
//                            }
                            staffModel.setDef1(def1);
                            flag = true;
                        }
                        if (sex != null && !staffModel.getSex().equals(sex)) {
                            staffModel.setSex(sex);
                            flag = true;
                        }
                        if (flag) {
                            result = staffDAO.updateByPk(staffModel);
                            if (result < 1) {
                                taskLogModel = new TaskLogModel();
                                taskLogModel.setLog_ts(DateUtils.currentDatetime());
                                taskLogModel.setResult("N");
                                taskLogModel.setMemo("系统内部错误!");
                                taskLogDAO.insertByMo(taskLogModel);
                                return ResponseUtils.getSuccessAPI(false, "系统内部错误", RestOperateCode.INSERT_DATA);
                            }
                        }

                        flag = false;

                    }

//                    switch (sys_flag) {
//                        case 2:
//
//                            //更新
//                            staffModel = checkStaffModels.get(0);
//                            staffModel.setDepartment_code(department_code);
//                            staffModel.setCompany_code(company_code);
//                            staffModel.setStaff_code(staff_code);
//                            staffModel.setStaff_name(staff_name);
//                            staffModel.setSex(sex);
//                            result = staffDAO.updateByPk(staffModel);
//                            if (result < 1) {
//                                taskLogModel = new TaskLogModel();
//                                taskLogModel.setLog_ts(DateUtils.currentDatetime());
//                                taskLogModel.setResult("N");
//                                taskLogModel.setMemo("系统内部错误!");
//                                taskLogDAO.insertByMo(taskLogModel);
//                                return ResponseUtils.getSuccessAPI(false, "系统内部错误", RestOperateCode.INSERT_DATA);
//                            }
//                            break;
//
//                        case 3:
//
//                            //删除
//                            staffModel = checkStaffModels.get(0);
//                            staffModel.setStaff_type(StaffModel.Staff_Type_LeaveJob);
//                            result = staffDAO.updateByPk(staffModel);
//                            if (result < 1) {
//                                taskLogModel = new TaskLogModel();
//                                taskLogModel.setLog_ts(DateUtils.currentDatetime());
//                                taskLogModel.setResult("N");
//                                taskLogModel.setMemo("系统内部错误!");
//                                taskLogDAO.insertByMo(taskLogModel);
//                                return ResponseUtils.getSuccessAPI(false, "系统内部错误", RestOperateCode.INSERT_DATA);
//                            }
//                            break;
//
//                    }

                } else {
                    taskLogModel = new TaskLogModel();
                    taskLogModel.setLog_ts(DateUtils.currentDatetime());
                    taskLogModel.setResult("N");
                    System.out.println(wwjid);
                    taskLogModel.setMemo("系统中存在多个员工编码重复的人员,请检查!");
                    taskLogDAO.insertByMo(taskLogModel);
                    return ResponseUtils.getSuccessAPI(false, "系统中存在多个员工编码重复的人员,请检查!", RestOperateCode.INSERT_DATA);
                }


            }

            if (staffModels.size() > 0) {
                result = staffDAO.addBatch(staffModels);
                if (result < 1) {
                    taskLogModel = new TaskLogModel();
                    taskLogModel.setLog_ts(DateUtils.currentDatetime());
                    taskLogModel.setResult("N");
                    taskLogModel.setMemo("系统内部错误!");
                    taskLogDAO.insertByMo(taskLogModel);
                    return ResponseUtils.getSuccessAPI(false, "系统内部错误!", RestOperateCode.INSERT_DATA);
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();

            //记录日志
            taskLogModel = new TaskLogModel();
            taskLogModel.setLog_ts(DateUtils.currentDatetime());
            taskLogModel.setResult("N");
            taskLogModel.setMemo("系统内部错误");
            taskLogDAO.insertByMo(taskLogModel);

        }

        taskConfigModel = taskConfigModels.get(0);
        taskConfigModel.setLast_time(DateUtils.currentDatetime());
        result = taskConfigDAO.updateByPk(taskConfigModel);
        if (result < 1) {
            taskLogModel = new TaskLogModel();
            taskLogModel.setLog_ts(DateUtils.currentDatetime());
            taskLogModel.setResult("N");
            taskLogModel.setMemo("系统内部错误");
            taskLogDAO.insertByMo(taskLogModel);
            return ResponseUtils.getSuccessAPI(false, "系统内部错误!", RestOperateCode.INSERT_DATA);
        }

        taskLogModel = new TaskLogModel();
        taskLogModel.setLog_ts(DateUtils.currentDatetime());
        taskLogModel.setResult("Y");
        taskLogModel.setMemo("成功!");
        taskLogDAO.insertByMo(taskLogModel);
        return ResponseUtils.getSuccessAPI(true, "成功!", RestOperateCode.INSERT_DATA);
    }

    @Override
    public APIRestResponse SyncNow() throws Exception {
        List<TaskConfigModel> models = taskConfigDAO.retrieveAll();

        if (models != null && models.size() == 1) {

            return Sync(models.get(0).getCompany_code(), models.get(0).getUrl(), models.get(0).getUser(), models.get(0).getPassword());

        } else {
            TaskLogModel taskLogModel = new TaskLogModel();
            taskLogModel.setLog_ts(DateUtils.currentDatetime());
            taskLogModel.setResult("N");
            taskLogModel.setMemo("同步失败,配置信息错误!");
            taskLogDAO.insertByMo(taskLogModel);
            return ResponseUtils.getSuccessAPI(false, "同步失败,配置信息错误!!", RestOperateCode.INSERT_DATA);
        }
    }

    @Override
    public int insertByMo(TaskConfigModel model) {

        model.setLast_time(DateUtils.currentDatetime());
        Integer result = taskConfigDAO.insertByMo(model);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }

    @Override
    public int updateByPk(TaskConfigModel model) {

        Integer result = taskConfigDAO.updateByPk(model);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }

    @Override
    public int deleteByPks(List<String> pks) {

        Integer result = taskConfigDAO.deleteByPks(pks);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }

    @Override
    public List<TaskConfigModel> retrieveAll() {
        return taskConfigDAO.retrieveAll();
    }
}
