package com.iandtop.service;

import com.iandtop.model.system.RoleModel;
import com.iandtop.model.system.UserModel;

import java.util.List;

/**
 * Created by lz on 2017/5/18.
 */
public interface RoleService {

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
}
