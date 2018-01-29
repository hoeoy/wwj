package com.iandtop.dao;

import com.iandtop.model.system.UserModel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CompanyDAO
 */
@Service
public interface UserDAO {
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
    List<UserModel> retrieveByCodeAndPwd(UserModel model);

    /**
     * 按编码查询
     * @param user_code
     * @return
     */
    List<UserModel> retrieveByCode(String user_code);

    //用户登录
    List<UserModel> loginByCodeAndPwd(UserModel model);
    //修改密码
    int updatePwd(UserModel model);
    List<UserModel> oldPwd(UserModel model);
}



