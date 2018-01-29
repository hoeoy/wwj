package com.iandtop.service;

import com.github.pagehelper.PageInfo;
import com.iandtop.model.OrderModel;
import com.iandtop.utils.APIRestResponse;

import java.util.List;

/**
 * Created by Administrator on 2017/5/12.
 */
public interface OrderService {

    //分类查询以及分页显示
    List<OrderModel> findgoods(int goodstype);
    PageInfo<OrderModel> queryByPage(int goodstype, Integer pageNo, Integer pageSize);
    //商品查询以及分页显示
    List<OrderModel> findgood(OrderModel ordermodel);
    PageInfo<OrderModel> findgoodByPage(OrderModel ordermodel, Integer pageNo, Integer pageSize);
    //商品详情
    List<OrderModel>gooddetails(int id);
    //查询所有商品
    List<OrderModel> find();
    PageInfo<OrderModel> findByPage(Integer pageNo, Integer pageSize);
    //商品大类查询以及分页
    List<OrderModel> findstylegoods(int goodstype);
    PageInfo<OrderModel> findstylegoodsByPage(int goodstype, Integer pageNo, Integer pageSize);

    //商品删除
    int delgood(OrderModel ordermodel);
    //修改商品
    int updategood(OrderModel ordermodel);
    //小类查询商品
    List<OrderModel> typefindgoods(OrderModel ordermodel);


    /**
     * 线上支付
     * @param staff_code 人员编码
     * @param passwrod  支付密码
     * @param meal_money   金额(单位:元)
     * @return
     */
    int payOrderOnLine(String staff_code, String passwrod, Double meal_money);


    /**
     * 线下支付
     * @param staff_code 人员编码
     * @param meal_money   金额(单位:元)
     * @return
     */
    int payOrderOffline(String staff_code, Double meal_money,String pk_device);

}
