package com.iandtop.dao;

import com.iandtop.model.card.CardModel;

import java.util.List;

public interface MealSupplementDao {

    List<CardModel> findCardByPage(CardModel vo);

}
