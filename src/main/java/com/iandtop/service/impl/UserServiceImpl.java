package com.iandtop.service.impl;

import com.iandtop.dao.PublicDAO;
import com.iandtop.dao.UserDAO;
import com.iandtop.model.StaffModel;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.model.system.UserModel;
import com.iandtop.service.UserService;
import com.iandtop.utils.BaseUtils;
import com.iandtop.utils.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lz on 2017/5/18.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PublicDAO publicDAO;

    @Override
    public List<UserModel> retrieveAll() {

        List<UserModel> result = userDAO.retrieveAll();

        for (UserModel model: result) {
            model.setPassword(Encode.decode(model.getPassword()));
        }

        return result;
    }

    @Override
    public List<UserModel> retrieveAllWithPage(UserModel vo) {

        List<UserModel> result = userDAO.retrieveAllWithPage(vo);

        for (UserModel model: result) {
            model.setPassword(Encode.decode(model.getPassword()));
        }

        return result;
    }

    @Override
    public int insertByMo(UserModel model) {

        List<UserModel> resultList = userDAO.retrieveByCode(model.getUser_code());
        if(resultList != null && resultList.size() > 0){
            return StatusCodeConstants.Code_Repeat;
        }

        model.setPassword(Encode.encode(model.getPassword()));

        Integer result = userDAO.insertByMo(model);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }

    @Override
    public int updateByPk(UserModel model) {

        if(model.getPassword() != null && model.getPassword().trim().length() > 0){
            model.setPassword(Encode.encode(model.getPassword()));
        }

        Integer result = userDAO.updateByPk(model);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }

    @Override
    public int deleteByPks(List<String> pks) {

        Integer result = userDAO.deleteByPks(pks);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }

    @Override
    public UserModel checkByCodeAndPwd(UserModel model) {

        model.setPassword(Encode.encode(model.getPassword()));

        List<UserModel> resultList = userDAO.retrieveByCodeAndPwd(model);

        return resultList != null && resultList.size() == 1 ? resultList.get(0) : null;
    }

    @Override
    public List<StaffModel> retrievenus(String department_code) {

        String sql = "select * from db_staff a " +
                " left join db_department c on a.department_code = c.department_code " +
                " where not EXISTS(select * from sm_user b where a.pk_staff=b.pk_staff) and c.department_code LIKE '"+department_code+"%' and" +
                " a.staff_type='"+StaffModel.Staff_Type_OnJob+"' ";

        List<StaffModel> result = BaseUtils.mapToBean(StaffModel.class,publicDAO.retrieveBySql(sql));

        return result;
    }

    @Override
    public int userself(List<UserModel> vos) {

        Integer result;

        //校验
        List<UserModel> allVOList = userDAO.retrieveAll();
        List<String> allCodes = new ArrayList<String>();
        for (UserModel mo : allVOList) {
            allCodes.add(mo.getUser_code());
        }

        for (UserModel  mo : vos) {
            if(allCodes.contains(mo.getUser_code())){
                return StatusCodeConstants.Code_Repeat;
            }
        }

        for (UserModel vo: vos) {
            vo.setPassword(Encode.encode(vo.getPassword()));
            result = userDAO.insertByMo(vo);
            if(result < 1){
                return StatusCodeConstants.Fail;
            }
        }

        return StatusCodeConstants.Success;
    }

    @Override
    public  List<UserModel> loginByCodeAndPwd(UserModel model) {
        model.setPassword(Encode.encode(model.getPassword()));
        List<UserModel> resultList = userDAO.loginByCodeAndPwd(model);
        return resultList;
    }
    @Override
    public int updatePwd(UserModel model) {
        model.setPassword(Encode.encode(model.getPassword()));
        return userDAO.updatePwd(model);
    }

    @Override
    public List<UserModel> oldPwd(UserModel model) {
        model.setOldpwd(Encode.encode(model.getOldpwd()));
        List<UserModel> List =userDAO.oldPwd(model);
        return List;
    }

}
