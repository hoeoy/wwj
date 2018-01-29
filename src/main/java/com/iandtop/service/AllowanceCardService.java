package com.iandtop.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.iandtop.model.card.CardChargeRecordModel;
import com.iandtop.model.card.CardModel;
import com.iandtop.model.form.ChargeForSModel;
import com.iandtop.model.meal.MealAllowanceModel;
import com.iandtop.model.meal.MealAllowanceNumModel;

public interface AllowanceCardService {
	List<CardModel> retrieveAllWithPage(CardModel vo);
	//int chargeByVO(CardChargeRecordModel vo);
	int insertMealAllowanceNum(List<CardChargeRecordModel> vos);
	int allowanceSend(String ids, String operator);
	int deleteRecord(String ids);
	List<MealAllowanceNumModel> queryMainData(MealAllowanceNumModel vo);
	List<MealAllowanceModel> queryDeatilData(MealAllowanceModel vo);
	PageInfo<MealAllowanceModel> queryDeatilDataByPage(MealAllowanceModel vo, Integer pageNo, Integer pageSize);
	List<MealAllowanceModel> queryDeatilDataSum(MealAllowanceModel vo);
	int save(MealAllowanceModel vos, long batch_num_id);
	public long insertBatchNum(String operator);
}
