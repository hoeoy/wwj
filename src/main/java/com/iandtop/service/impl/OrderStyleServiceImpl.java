package com.iandtop.service.impl;

import com.iandtop.dao.OrderStyleDao;
import com.iandtop.model.OrderModelVo;
import com.iandtop.model.OrderStyleModel;
import com.iandtop.service.OrderStyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */
@Service
@Transactional
public class OrderStyleServiceImpl implements OrderStyleService {
    @Autowired
    private OrderStyleDao orderStyleDao;
    @Override
    public List<OrderStyleModel> findStyle() {
        return orderStyleDao.findStyle();
    }
    @Override
    public List<OrderModelVo> findtype(int pk_parentid) {
        return orderStyleDao.findtype(pk_parentid);
    }
    @Override
    public int addOrderStyle(OrderStyleModel orderStyleModel) {
        return orderStyleDao.addOrderStyle(orderStyleModel);
    }
    @Override
    public int addOrderType(OrderModelVo orderModelVo) {
        return orderStyleDao.addOrderType(orderModelVo);
    }
    @Override
    public int delOrderStyle(OrderStyleModel orderStyleModel) {
        return orderStyleDao.delOrderStyle(orderStyleModel);
    }
    @Override
    public int delOrderType(OrderModelVo orderModelVo) {
        return orderStyleDao.delOrderType(orderModelVo);
    }
    @Override
    public int updateOrderStyle(OrderStyleModel orderStyleModel) {
        return orderStyleDao.updateOrderStyle(orderStyleModel);
    }
    @Override
    public int updateOrderType(OrderModelVo orderModelVo) {
        return orderStyleDao.updateOrderType(orderModelVo);
    }
    @Override
    public List<OrderStyleModel> findStyleforcondition(OrderStyleModel orderStyleModel) {
        return orderStyleDao.findStyleforcondition(orderStyleModel);
    }
    @Override
    public List<OrderModelVo> findtypeforcondition(OrderModelVo orderModelVo) {
        return orderStyleDao.findtypeforcondition(orderModelVo);
    }

}
