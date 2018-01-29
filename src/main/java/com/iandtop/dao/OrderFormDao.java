package com.iandtop.dao;

import com.iandtop.model.OrderDetailModel;
import com.iandtop.model.OrderDetailModelVO;
import com.iandtop.model.OrderFormModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/18.
 */
public interface OrderFormDao {
    //通过员工姓名查找订单
    List<OrderFormModel> findformforname(OrderFormModel orderform);

   // int insertForm(OrderDetailModelVO model);

   // int insertdetail(List<OrderDetailModel> OrderDetailModel);
    //线上支付
    int insertdetail(Map<String, String> Parameters);
    int insertForm(Map<String, String> parameter);
    //线下支付
    int insertdetail2(Map<String, String> Parameters);
    int insertForm2(Map<String, String> parameter);
    //售货员操作订单
    //线上支付线下取货
    int updateForm(OrderFormModel orderFormModel);
    //线上未支付先下取货
    int updateForm2(OrderFormModel orderFormModel);
}
