package com.iandtop.service;

import com.iandtop.model.DeptModelVO;
import com.iandtop.model.StaffModel;
import com.iandtop.model.system.UserModel;

import java.util.List;

/**
 * Created by lz on 2017/5/18.
 */
public interface UserService {

    /**
     * 查询全部
     * @return
     */
    List<UserModel> retrieveAll();

    /**
     * 分页查询,包括条件查询
     * @param vo
     * @return
     */
    List<UserModel> retrieveAllWithPage(UserModel vo);

    /**
     * 插入数据
     * @param model
     * @return
     */
    int insertByMo(UserModel model);

    /**
     * 更新数据
     * @param model
     * @return
     */
    int updateByPk(UserModel model);

    /**
     * 按pk批量删除
     * @param pks
     * @return
     */
    int deleteByPks(List<String> pks);

    /**
     * 按编码，密码查询用户
     * @param user_code
     * @param password
     * @return
     */
    UserModel checkByCodeAndPwd(UserModel model);

    /**
     * 根据部门编码查询不是用户的人员
     * @param department_code
     * @return
     */
    List<StaffModel> retrievenus(String department_code);

    /**
     * 人员转用户
     * @param vos
     * @return
     */
    int userself(List<UserModel> vos);
    //用户登录
    List<UserModel> loginByCodeAndPwd(UserModel model);
    //修改密码
    int updatePwd(UserModel model);

    List<UserModel> oldPwd(UserModel model);
}
