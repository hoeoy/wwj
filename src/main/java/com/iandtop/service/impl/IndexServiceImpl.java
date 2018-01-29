package com.iandtop.service.impl;

import com.iandtop.dao.PublicDAO;
import com.iandtop.dao.RoleDAO;
import com.iandtop.model.system.NavigationModel;
import com.iandtop.model.system.RoleModel;
import com.iandtop.model.system.UserModel;
import com.iandtop.service.IndexService;
import com.iandtop.utils.BaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lz on 2017/5/18.
 */
@Service
@Transactional
public class IndexServiceImpl implements IndexService{

    @Autowired
    private PublicDAO publicDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Override
    public List<NavigationModel> retrieveAllNavigation() throws RuntimeException {
        List<Map> resultList = publicDAO.retrieveBySql("select * from sm_navigation");

        List<NavigationModel> result = BaseUtils.mapToBean(NavigationModel.class,resultList);
        List<NavigationModel> nodeList = new ArrayList<NavigationModel>();

        for(NavigationModel node1 : result){

            node1.setTreeId(node1.getPk_navigation().toString());
            node1.setTreeText(node1.getNavigation_name());

            boolean mark = false;
            for(NavigationModel node2 : result){
                if(node1.getPk_navigation()!=null && node1.getPk_father_navigation() != null && node1.getPk_father_navigation().equals(node2.getPk_navigation())){
                    mark = true;
                    if(node2.getChildren_data() == null)
                        node2.setChildren_data(new ArrayList<String>());
                    node2.getChildren_data().add(node1);
                    break;
                }
            }
            if(!mark){
                nodeList.add(node1);
            }
        }


        return nodeList;
    }

    @Override
    public List<NavigationModel> retrieveWithUser(UserModel model) throws RuntimeException {
        List<NavigationModel> result = null;
        List<NavigationModel> nodeList = new ArrayList<NavigationModel>();


            if(RoleModel.Super_Admin.equals(model.getUser_code())){
                result = retrieveAllNavigation();
            }else{
                String sql = "select a.* " +
                        "from sm_navigation a " +
                        "left join sm_jurisdiction b on a.pk_navigation = b.pk_navigation " +
                        "left join sm_user c on b.pk_role = c.PK_ROLE " +
                        "where c.PK_USER='"+model.getPk_user()+"'";
                result = BaseUtils.mapToBean(NavigationModel.class,publicDAO.retrieveBySql(sql));
            }


        if(result != null ){
            for(NavigationModel node1 : result){

                node1.setTreeId(node1.getPk_navigation().toString());
                node1.setTreeText(node1.getNavigation_name());

                boolean mark = false;
                for(NavigationModel node2 : result){
                    if(node1.getPk_navigation()!=null && node1.getPk_father_navigation() != null && node1.getPk_father_navigation().equals(node2.getPk_navigation())){
                        mark = true;
                        if(node2.getChildren_data() == null)
                            node2.setChildren_data(new ArrayList<String>());
                        node2.getChildren_data().add(node1);
                        break;
                    }
                }
                if(!mark){
                    nodeList.add(node1);
                }
            }
        }


        return nodeList;
    }

    @Override
    public List<NavigationModel> retrieveWithUserRole(UserModel model) {
        List<Map> resultList = publicDAO.retrieveBySql("select * from sm_navigation");

        List<NavigationModel> result = BaseUtils.mapToBean(NavigationModel.class,resultList);

        List<Map> roleresultList = publicDAO.retrieveBySql("select a.* from sm_navigation a " +
                "left join sm_jurisdiction b on a.pk_navigation = b.pk_navigation " +
                " where b.pk_role='"+model.getPk_role()+"'");
        List<NavigationModel> roleresult = BaseUtils.mapToBean(NavigationModel.class,roleresultList);

        for (NavigationModel vo1 : result) {
            for (NavigationModel vo2 : roleresult) {
                if(vo1.getPk_navigation().equals(vo2.getPk_navigation())){
                    vo1.setChecked(true);
                    break;
                }else{
                    vo1.setChecked(false);
                }
            }
        }

        List<NavigationModel> nodeList = new ArrayList<NavigationModel>();

        for(NavigationModel node1 : result){

            node1.setTreeId(node1.getPk_navigation().toString());
            node1.setTreeText(node1.getNavigation_name());

            boolean mark = false;
            for(NavigationModel node2 : result){
                if(node1.getPk_navigation()!=null && node1.getPk_father_navigation() != null && node1.getPk_father_navigation().equals(node2.getPk_navigation())){
                    mark = true;
                    if(node2.getChildren_data() == null)
                        node2.setChildren_data(new ArrayList<String>());
                    node2.getChildren_data().add(node1);
                    break;
                }
            }
            if(!mark){
                nodeList.add(node1);
            }
        }


        return nodeList;
    }

}
