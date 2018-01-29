package com.iandtop.dao;

import com.iandtop.model.form.ChargeSumModel;
import com.iandtop.model.meal.MealRecordSumModel;

import java.util.List;

/**
 * Created by xss on 2017-05-24.
 */
public interface ChargeSumDAO {
    List<ChargeSumModel> queryChargeSum(String operator, String start_ts, String end_ts);

    int delrecordsum(String start_ts, String end_ts);
    //int insrecordsum(MealRecordSumModel model);
    List<ChargeSumModel> queryMealCharge(String start_ts, String end_ts);
    //清空计算表内为空值的数据。
    int delnull();
    //查出计算表需要删除的行数
    int getline(String start_ts, String end_ts);
}
