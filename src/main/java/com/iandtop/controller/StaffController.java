package com.iandtop.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iandtop.dao.PublicDAO;
import com.iandtop.model.DeptModelVO;
import com.iandtop.model.OrderStyleModel;
import com.iandtop.model.StaffModel;
import com.iandtop.model.device.DeviceModel;
import com.iandtop.model.meal.MealAllowanceModel;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.service.DeptService;
import com.iandtop.service.StaffService;
import com.iandtop.utils.APIRestResponse;
import com.iandtop.utils.BaseUtils;
import com.iandtop.utils.ResponseUtils;
import com.iandtop.utils.RestOperateCode;
import com.iandtop.utils.excel.ExcelDataFormatter;
import com.iandtop.utils.excel.ExcelUtils;
import com.iandtop.utils.excel.MealAllowanceExcel;
import com.iandtop.utils.excel.StaffExcel;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.List;

/**
 * Created by lz on 2017/5/4.
 */

@Controller
@RequestMapping("/staff")
public class StaffController {
    private static final Logger LOG = LoggerFactory.getLogger(StaffController.class);

    @Autowired
    private StaffService staffService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private PublicDAO publicDAO;

    @ResponseBody
    @RequestMapping(value="/retrieve")
    public String retrieve(StaffModel vo, HttpServletRequest request) {

        List<StaffModel> result = staffService.retrieveAllWithPage(vo);

        List<StaffModel> resultAll = staffService.retrieveAllWithPageCount(vo);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total",resultAll.size());
        jsonObject.put("rows",JSONArray.toJSON(result));

        return JSONArray.toJSON(jsonObject).toString();
    }


    @ResponseBody
    @RequestMapping("/save")
    public APIRestResponse save(@RequestBody StaffModel model) {

        Integer num = 0;
        if(model !=null){

            if(model.getPk_staff()!= null && model.getPk_staff().trim() != ""){//如果前端传递过来pk,则判断为更新操作
                num = staffService.updateByPk(model);
            }else{
                num = staffService.insertByMo(model);
            }

            if(num == StatusCodeConstants.Success){
                return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.UPDATE_DATA);
            }else{
                return ResponseUtils.getSuccessAPI(false,"false", RestOperateCode.UPDATE_DATA);
            }
        }else{
            return ResponseUtils.getSuccessAPI(false,"false", RestOperateCode.UPDATE_DATA,"参数不可为null");
        }
    }

    @ResponseBody
    @RequestMapping("/delete")
    public APIRestResponse delete(@RequestBody List<String> pks) {
        Integer num = staffService.deleteByPks(pks);
        if(num == StatusCodeConstants.Fail){
            return ResponseUtils.getSuccessAPI(false,"false", RestOperateCode.DELETE_DATA);
        }else{
            return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.DELETE_DATA);
        }

    }
    @ResponseBody
    @RequestMapping("ULE")
    public APIRestResponse ule(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin","*");
        int status = 0;
        int line = 0;
        String str = "";
        String queryDept;
        String queryPsn;
        String queryId_card;
        String querycard;
        ExcelDataFormatter edf = new ExcelDataFormatter();
        File newFile = null;
        try {
            String name = file.getOriginalFilename();
            String rootPath = request.getContextPath();
            //获取项目路径，创建临时文件
            //windows下
            if ("\\".equals(File.separator)) {
                newFile = new File(rootPath + "\\" + name);
            }
            //linux下
            if ("/".equals(File.separator)) {
                newFile = new File(rootPath + "/" + name);
            }
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newFile));
            stream.write(file.getBytes());
            stream.close();

            //读取Excel文件并解析
            List<StaffExcel> vos = new ExcelUtils<StaffExcel>(new StaffExcel()).readFromFile(edf, newFile);
            for (StaffExcel tmp : vos) {
                line=line +1;
                StaffModel model = new StaffModel();
                queryPsn = "select staff_code from db_staff where staff_code = '"+""+tmp.getStaff_code()+"'";
                List<DeptModelVO> deptModelPsn = BaseUtils.mapToBean(DeptModelVO.class,publicDAO.retrieveBySql(queryPsn));
                if(deptModelPsn!=null){
                    if(deptModelPsn.size()>0){
                        str+="行号为“"+line+"”插入失败,找到了相同为“"+tmp.getStaff_code()+"”的员工编码.<br>";
                        continue;
                    }else{
                        model.setStaff_code(tmp.getStaff_code());  //员工编码
                    }
                }
                model.setStaff_name(tmp.getStaff_name());           //员工名字
                queryDept = "select department_code from db_department where department_name = '" +""+tmp.getDepartment_code()+"'";
                List<DeptModelVO> deptModelVOS = BaseUtils.mapToBean(DeptModelVO.class,publicDAO.retrieveBySql(queryDept));
                if(deptModelVOS==null || deptModelVOS.size()==0){
                    str+="行号为“"+line+"”,未找到为“"+tmp.getDepartment_code()+"”的部门.<br>";
                    continue;
                }else{
                    DeptModelVO deptModelVO= deptService.queryretrieve(tmp.getDepartment_code());
                    model.setDepartment_code(deptModelVO.getDepartment_code()); //关联部门

                }
                model.setStaff_type(tmp.getStaff_type());           //员工状态
                if(tmp.getStaff_type().equals("在职")){
                    model.setStaff_type("1");
                }else if (tmp.getStaff_type().equals("离职")){
                    model.setStaff_type("0");
                }else{
                    model.setStaff_type(tmp.getStaff_type());
                }
                if(tmp.getSex().equals("男")){
                    model.setSex("1");
                }else if (tmp.getSex().equals("女")){
                    model.setSex("0");
                }else{
                    model.setSex(tmp.getSex());
                }
                queryId_card = "select staff_code from db_staff where id_card = '"+""+tmp.getId_card()+"'";
                List<DeptModelVO> deptModelId_card = BaseUtils.mapToBean(DeptModelVO.class,publicDAO.retrieveBySql(queryId_card));
                if(deptModelId_card!=null){
                    if(deptModelId_card.size()>0){
                        str+="行号为“"+line+"”插入失败,找到了相同为“"+tmp.getId_card()+"”的身份证号.<br>";
                        continue;
                    }else{
                        model.setId_card(tmp.getId_card());                 //身份证号
                    }
                }
                model.setJob_code(tmp.getJob_code());               //职务编码
                model.setEdu_code(tmp.getEdu_code());               //学历编码
                model.setNation_code(tmp.getNation_code());         //民族编码
                model.setBirth_date(tmp.getBirth_date());           //生日
                model.setHire_date(tmp.getHire_date());             //入职日期
                model.setLeave_date(tmp.getLeave_date());           //离职日期
                model.setEmail(tmp.getEmail());                     //电子邮箱
                model.setPhone(tmp.getPhone());                     //电话
                status += staffService.insertbyexcel(model);
                //LOG.debug("记录:{}", tmp.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            //删除临时文件
            if (newFile != null) {
                newFile.delete();
            }
        }
        return ResponseUtils.getSuccessAPI(status>0?true:false,status>0?"插入成功"+status+"条,插入失败"+(line-status)+"条":"插入失败"+(line-status)+"条", RestOperateCode.INSERT_DATA,str);
    }
    private static Logger logger = LoggerFactory.getLogger(StaffController.class);
    /**
     * 获取请求数据模型
     */
    public JSONObject getRequestJSONObject(HttpServletRequest request) throws Exception {
        try {
            String strRequest = new String(request.getParameter("data").getBytes("UTF-8"), "UTF-8");
            String strURLDecodeResquet = URLDecoder.decode(strRequest, "utf-8");
            logger.info("-----------------------------------------------------------------");
            logger.info("客户端请求!!!!!!!<==" + strURLDecodeResquet);
            JSONObject jsonObject = JSON.parseObject(strURLDecodeResquet);
            return jsonObject;
        } catch (Exception exx) {
            throw exx;
        }
    }

    //@Param("pk_staff") List<String> pk_staff,@Param("pk_merchant") String pk_merchant
    @ResponseBody
    @RequestMapping("/batchupdate")
    public APIRestResponse batchupdate(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
            Integer num = 0;
            try{
                StaffModel model = new StaffModel();
                JSONObject json = getRequestJSONObject(request);
                String pk_merchant = json.getString("pk_merchant");
                JSONArray arr = json.getJSONArray("pk_staff");
                model.setPk_merchant(pk_merchant);
                for (int i = 0; i < arr.size(); i++) {
                    model.setPk_staff(String.valueOf(arr.get(i)));
                    num += staffService.batchupdate(model);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        if(num > 0){
            return ResponseUtils.getSuccessAPI(true,"成功批量修改"+num+"位人员", RestOperateCode.UPDATE_DATA);
        }else{
            return ResponseUtils.getSuccessAPI(false,"修改失败，请重试。", RestOperateCode.UPDATE_DATA);
        }
    }

    @ResponseBody
    @RequestMapping("/findAllForupd")
    public APIRestResponse findAllForupd(StaffModel model) {

        List<StaffModel> result = staffService.findAllForupd(model);

        if(result.size()>0){
            return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.GET_DATA,result);
        }
        return ResponseUtils.getSuccessAPI(false,"false", RestOperateCode.GET_DATA);
    }

}
