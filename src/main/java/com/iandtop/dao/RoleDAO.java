package com.iandtop.dao;

import com.iandtop.model.system.RoleModel;
import com.iandtop.model.system.UserModel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * RoleDAO
 */
public interface RoleDAO {
    /**
     * 查询全部
     * @return
     */
    List<RoleModel> retrieveAll();

    /**
     * 分页查询,包括条件查询
     * @param vo
     * @return
     */
    List<RoleModel> retrieveAllWithPage(RoleModel vo);

    /**
     * 插入数据
     * @param model
     * @return
     */
    int insertByMo(RoleModel model);

    /**
     * 更新数据
     * @param model
     * @return
     */
    int updateByPk(RoleModel model);

    /**
     * 按pk批量删除
     * @param pks
     * @return
     */
    int deleteByPks(List<String> pks);

    /**
     * 按编码查询
     * @param role_code
     * @return
     */
    List<RoleModel> retrieveByCode(String role_code);

    /**
     * 按pk查询
     * @param pk
     * @return
     */
    List<RoleModel> retrieveByPk(String pk);

}



