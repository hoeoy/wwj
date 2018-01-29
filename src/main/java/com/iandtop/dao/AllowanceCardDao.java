package com.iandtop.dao;

import java.util.List;

import com.iandtop.model.card.CardChargeRecordModel;
import com.iandtop.model.card.CardModel;
import com.iandtop.model.meal.MealAllowanceModel;
import com.iandtop.model.meal.MealAllowanceNumModel;

public interface AllowanceCardDao {
	/**
     * 查询正常状态的卡
     * @param vo
     * @return
     */
    List<CardModel> retrieveAllNormalWithPage(CardModel vo);
    
    /**
     * 更新卡表数据
     * @param vo
     * @return
     */
    int updateByVO(MealAllowanceModel vo);
    
    int updateMealAllowanceNum(MealAllowanceNumModel vo);
    
    String getMaxAllowanceNumCode(String currentDate);

    String getMaxChargeCardBatchnum();
    
    int insertMealAllowanceNum(MealAllowanceNumModel vo);

    int insertMealAllowance(MealAllowanceModel vo);

    int saveByVO(CardChargeRecordModel vo);

	List<MealAllowanceNumModel> queryMealAllowanceNumModelData(
            MealAllowanceNumModel vo);

	List<MealAllowanceModel> queryMealAllowanceModelData(MealAllowanceModel vo);

	int deleteByAllowanceNumIds(MealAllowanceNumModel manm);

	int deleteByAllowanceIds(MealAllowanceNumModel mam);

    List<MealAllowanceModel> queryDeatilDataSum(MealAllowanceModel vo);

}
