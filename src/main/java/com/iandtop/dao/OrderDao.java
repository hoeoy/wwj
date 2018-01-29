package com.iandtop.dao;

import com.iandtop.model.OrderModel;
import com.iandtop.model.card.CardModel;
import com.iandtop.model.meal.MealRecordModel;
import com.iandtop.utils.BaseUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/5/12.
 */
public interface OrderDao {
    //查询分类商品
    List<OrderModel> findgoods(int goodstype);
    //查询所有商品
    List<OrderModel> find();
    //商品查询
    List<OrderModel> findgood(OrderModel ordermodel);
    //商品详情
    List<OrderModel>gooddetails(int id);
    //商品大类查询以及分页
    List<OrderModel> findstylegoods(int goodstype);
    //商品删除
    int delgood(OrderModel ordermodel);
    //修改商品
    int updategood(OrderModel ordermodel);
    //小类查询商品
    List<OrderModel> typefindgoods(OrderModel ordermodel);

    MealRecordModel mealRecordModel(String device_code);

    MealRecordModel findDeviceCode (String pk_device);

}
