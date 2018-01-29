package com.iandtop.dao;

import com.iandtop.model.meal.DiningModel;
import com.iandtop.model.meal.MerchantModel;

import java.util.List;

/**
 * RoleDAO
 */
public interface DiningDAO {
    /**
     * 查询全部
     * @return
     */
    List<DiningModel> retrieveAll();

    /**
     * 分页查询,包括条件查询
     * @param vo
     * @return
     */
    List<DiningModel> retrieveAllWithPage(DiningModel vo);

    /**
     * 插入数据
     * @param model
     * @return
     */
    int insertByMo(DiningModel model);

    /**
     * 更新数据
     * @param model
     * @return
     */
    int updateByPk(DiningModel model);

    /**
     * 按pk批量删除
     * @param pks
     * @return
     */
    int deleteByPks(List<String> pks);

    /**
     * 按编码查询
     * @param code
     * @return
     */
    List<DiningModel> retrieveByCode(String code);

    /**
     * 按pk查询
     * @param pk
     * @return
     */
    List<DiningModel> retrieveByPk(String pk);

}



