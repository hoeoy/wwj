package com.iandtop.dao;

import com.iandtop.model.meal.MealRuleModel;
import com.iandtop.model.meal.MerchantModel;

import java.util.List;

/**
 * RoleDAO
 */
public interface MealRuleDAO {
    /**
     * 查询全部
     * @return
     */
    List<MealRuleModel> retrieveAll();

    /**
     * 分页查询,包括条件查询
     * @param vo
     * @return
     */
    List<MealRuleModel> retrieveAllWithPage(MealRuleModel vo);

    /**
     * 插入数据
     * @param model
     * @return
     */
    int insertByMo(MealRuleModel model);

    /**
     * 更新数据
     * @param model
     * @return
     */
    int updateByPk(MealRuleModel model);

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
    List<MealRuleModel> retrieveByCode(String merchant_code);

    /**
     * 按pk查询
     * @param pk
     * @return
     */
    List<MealRuleModel> retrieveByPk(String pk);

}



