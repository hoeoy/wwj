package com.iandtop.service.impl;

import com.iandtop.dao.GoodsDAO;
import com.iandtop.model.OrderModel;
import com.iandtop.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Klin
 * @description 描述
 * @create 2017/5/19 0019
 * @Version 1.0
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsDAO dao;
    @Override
    public Integer insertGoods(OrderModel model) {
        return dao.insertGoods(model);
    }
}
