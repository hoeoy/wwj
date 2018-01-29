package com.iandtop.service;

import com.iandtop.model.OrderDetailModel;

import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */
public interface OrderDetailService {
    //通过单号查询订单明细
    List<OrderDetailModel> findOrderDetail(OrderDetailModel Detail);
}
