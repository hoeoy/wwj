package com.iandtop.service.impl;

import com.iandtop.model.OrderDetailModel;
import com.iandtop.service.OrderDetailService;
import com.iandtop.dao.OrderDetailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */
@Service
@Transactional
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailDao OrderDetailDao;
    @Override
    public List<OrderDetailModel> findOrderDetail(OrderDetailModel Detail) {
        return OrderDetailDao.findOrderDetail(Detail);
    }
}
