package com.iandtop.service.impl;


import com.iandtop.dao.CompanyDAO;
import com.iandtop.dao.DeptDAO;
import com.iandtop.dao.PublicDAO;
import com.iandtop.model.CompanyModel;
import com.iandtop.model.DeptModelVO;
import com.iandtop.model.pub.TreeNodeModel;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lz on 2017/5/4.
 */
@Service
@Transactional
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptDAO deptDAO;

    @Autowired
    private CompanyDAO companyDAO;

    @Autowired
    private PublicDAO publicDAO;

    @Override
    public List<DeptModelVO> retrieveAll() {
        return deptDAO.retrieveAll();
    }

    public List<DeptModelVO> retrieveAllWithPage(DeptModelVO vo) {
        return deptDAO.retrieveAllWithPage(vo);
    }

    @Override
    public int insertByMo(DeptModelVO model) {

        //校验,同一个公司中部门编码不能重复
        String sql = "select * from db_department where company_code='"+model.getCompany_code()+"' and department_code='"+model.getDepartment_code()+"'";

        List<Map> result = publicDAO.retrieveBySql(sql);

        if(result != null && result.size() > 0){
            return 0;
        }

        return deptDAO.insertByMo(model);
    }

    @Override
    public int updateByPk(DeptModelVO roleModel) {
        return deptDAO.updateByPk(roleModel);
    }

    @Override
    public int deleteByPks(List<String> pks) {
        return deptDAO.deleteByPks(pks);
    }

    @Override
    public List<CompanyModel> retrieveDeptTree() {



        List<CompanyModel> companyModels = companyDAO.retrieveAll();

        for (CompanyModel model : companyModels) {
            List<DeptModelVO> all = deptDAO.retrieveByCompany(model.getCompany_code());

            model.setTreeText(model.getCompany_name());
            model.setTreeId(model.getCompany_code());
            model.setTreeNodeName(TreeNodeModel.Node_Name_Company);

            List<DeptModelVO> nodeList = new ArrayList<DeptModelVO>();
            //拼接树形结构
            for(DeptModelVO node1 : all){

                node1.setTreeId(node1.getDepartment_code());
                node1.setTreeText(node1.getDepartment_name());
                node1.setTreeNodeName(TreeNodeModel.Node_Name_Dept);

                boolean mark = false;
                for(DeptModelVO node2 : all){
                    if(node1.getDepartment_code()!=null && node1.getParent_code() != null && node1.getParent_code().equals(node2.getDepartment_code())){
                        mark = true;
                        if(node2.getChildren_data() == null)
                            node2.setChildren_data(new ArrayList<DeptModelVO>());
                        node2.getChildren_data().add(node1);
                        break;
                    }
                }
                if(!mark){
                    nodeList.add(node1);
                }
            }
            model.setChildren_data(nodeList);
        }

        return companyModels;
    }

    @Override
    public DeptModelVO retrieveByCode(DeptModelVO deptModel) {

        return deptDAO.retrieveByCode(deptModel);
    }

    @Override
    public DeptModelVO retrieveByPk(String pk_department) {
        return deptDAO.retrieveByPk(pk_department);
    }

    @Override
    public DeptModelVO queryretrieve(String department_name) {
        return deptDAO.queryretrieve(department_name);
    }
}
