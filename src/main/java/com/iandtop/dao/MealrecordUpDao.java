package com.iandtop.dao;

import com.iandtop.model.card.CardModel;
import com.iandtop.model.meal.MealRecordModel;

import java.util.List;

/**
 * Created by Administrator on 2017/6/12.
 */
public interface MealrecordUpDao {

    //List<String> queryorder(MealRecordModel mealRecordModel);
     int updateMealrecord(MealRecordModel mealRecordModel);
     int updatecard(CardModel cardModel);
     CardModel findcard(String pk_card);
}
