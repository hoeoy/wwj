package com.iandtop.service;

import com.iandtop.model.form.ChargeSumModel;
import com.iandtop.model.meal.MealRecordSumModel;

import java.util.List;

/**
 * Created by xss on 2017-05-24.
 */
public interface ChargeSumService {

    List<ChargeSumModel> queryChargeSum(String pk_user, String start_ts, String end_ts);

    List<ChargeSumModel> queryChargeSum(String pk_user, String start_ts, String end_ts, String if_user_sum);

    List<ChargeSumModel> queryChargeSumOne(String pk_user, String start_ts, String end_ts, String if_sum);

    List<ChargeSumModel> queryMealCharge(String start_ts, String end_ts);

    //消费充值汇总中间表计算
    int updrecordsum(String start_ts, String end_ts);

}
