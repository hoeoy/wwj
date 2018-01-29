package com.iandtop.service;

import com.iandtop.model.meal.MealRuleModel;
import com.iandtop.model.meal.MerchantModel;

import java.util.List;

/**
 * Created by lz on 2017/5/19.
 */
public interface MealRuleService {

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
     * @param meal_rule_code
     * @return
     */
    List<MealRuleModel> retrieveByCode(String meal_rule_code);

    /**
     * 按pk查询
     * @param pk
     * @return
     */
    List<MealRuleModel> retrieveByPk(String pk);

    /**
     * 创建消费表
     * @param
     * @return
     */
    List<String> createMealRecordForm();

    /**
     * 删除所有的消费表
     * @return
     */
    int deleteMealRecordForms();
}
