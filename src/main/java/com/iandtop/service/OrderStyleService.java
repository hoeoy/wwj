package com.iandtop.service;

import com.iandtop.model.OrderModelVo;
import com.iandtop.model.OrderStyleModel;

import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */
public interface OrderStyleService {
    //查询大类
    List<OrderStyleModel> findStyle();
    //查询小类
    List<OrderModelVo> findtype(int pk_parentid);
    //添加大类
    int addOrderStyle(OrderStyleModel orderStyleModel);
    //添加小类
    int addOrderType(OrderModelVo orderModelVo);
    //删除大类
    int delOrderStyle(OrderStyleModel orderStyleModel);
    //删除小类
    int delOrderType(OrderModelVo orderModelVo);
    //更改大类名字
    int updateOrderStyle(OrderStyleModel orderStyleModel);
    //更改小类名字
    int updateOrderType(OrderModelVo orderModelVo);
    //通过条件查询大类
    List<OrderStyleModel> findStyleforcondition(OrderStyleModel orderStyleModel);
    //查询有无相同名字的小类
    List<OrderModelVo> findtypeforcondition(OrderModelVo orderModelVo);
}
