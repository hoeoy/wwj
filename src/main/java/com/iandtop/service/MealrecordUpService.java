package com.iandtop.service;

import com.iandtop.model.card.CardModel;
import com.iandtop.model.meal.MealRecordModel;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Administrator on 2017/6/12.
 */
public interface MealrecordUpService {
    List<MealRecordModel> queryorder(MealRecordModel mealRecordModel, String start_ts, String end_ts)throws ParseException;

    List<String> getTableName(String start_ts, String end_ts);

    int updateMealrecord(MealRecordModel mealRecordModel, Double money)throws RuntimeException;


}
