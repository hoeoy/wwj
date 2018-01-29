package com.iandtop.service;

import com.github.pagehelper.PageInfo;
import com.iandtop.model.card.CardModel;
import com.iandtop.model.meal.MealRecordModel;

import java.util.List;

public interface MealSupplementService {
    PageInfo<CardModel> findCardByPage(CardModel model, Integer pageNo, Integer pageSize);

    int supplement(MealRecordModel model);

}
