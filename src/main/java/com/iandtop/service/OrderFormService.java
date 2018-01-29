package com.iandtop.service;

import com.github.pagehelper.PageInfo;
import com.iandtop.model.OrderDetailModel;
import com.iandtop.model.OrderDetailModelVO;
import com.iandtop.model.OrderFormModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/18.
 */
public interface OrderFormService {

    List<OrderFormModel> findformforname(OrderFormModel orderform);
    PageInfo<OrderFormModel> findformfornameByPage(OrderFormModel orderform, Integer pageNo, Integer pageSize);
 //   int insertForm(OrderDetailModelVO model);
  //  int insertdetail(List<OrderDetailModel> OrderDetailModel);
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
