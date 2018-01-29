package com.iandtop.dao;

import com.iandtop.model.meal.MerchantModel;
import com.iandtop.model.system.RoleModel;

import java.util.List;

/**
 * RoleDAO
 */
public interface MerchantDAO {
    /**
     * 查询全部
     * @return
     */
    List<MerchantModel> retrieveAll();

    /**
     * 分页查询,包括条件查询
     * @param vo
     * @return
     */
    List<MerchantModel> retrieveAllWithPage(MerchantModel vo);

    /**
     * 插入数据
     * @param model
     * @return
     */
    int insertByMo(MerchantModel model);

    /**
     * 更新数据
     * @param model
     * @return
     */
    int updateByPk(MerchantModel model);

    /**
     * 按pk批量删除
     * @param pks
     * @return
     */
    int deleteByPks(List<String> pks);

    /**
     * 按编码查询
     * @param merchant_code
     * @return
     */
    List<MerchantModel> retrieveByCode(String merchant_code);

    /**
     * 按pk查询
     * @param pk
     * @return
     */
    List<MerchantModel> retrieveByPk(String pk);

}



