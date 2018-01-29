package com.iandtop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iandtop.dao.ChargeForSDao;
import com.iandtop.dao.OrderDao;
import com.iandtop.model.OrderModel;
import com.iandtop.model.form.ChargeForSModel;
import com.iandtop.service.ChargeForSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Seven on 2017/9/27.
 */
@Service
public class ChargeForSServiceImpl implements ChargeForSService {

    @Autowired
    ChargeForSDao dao;

    @Override
    public PageInfo<ChargeForSModel> ChargeRecordByPage(ChargeForSModel model, Integer pageNo, Integer pageSize) {
        pageNo = pageNo == null?1:pageNo;
        pageSize = pageSize == null?10:pageSize;
        PageHelper.startPage(pageNo, pageSize);

        List<ChargeForSModel> annlist = dao.ChargeRecord(model);
        List<ChargeForSModel> list = annlist;
        PageInfo<ChargeForSModel> page = new PageInfo<ChargeForSModel>(list);
        return page;
    }

    @Override
    public PageInfo<ChargeForSModel> ChargeStaffByPage(ChargeForSModel model, Integer pageNo, Integer pageSize) {
        pageNo = pageNo == null?1:pageNo;
        pageSize = pageSize == null?10:pageSize;
        PageHelper.startPage(pageNo, pageSize);

        List<ChargeForSModel> annlist = dao.ChargeStaff(model);
        List<ChargeForSModel> list = annlist;
        PageInfo<ChargeForSModel> page = new PageInfo<ChargeForSModel>(list);
        return page;
    }

    @Override
    public PageInfo<ChargeForSModel> ChargeDepartment(ChargeForSModel model, Integer pageNo, Integer pageSize) {
        pageNo = pageNo == null?1:pageNo;
        pageSize = pageSize == null?10:pageSize;
        PageHelper.startPage(pageNo, pageSize);

        List<ChargeForSModel> annlist = dao.ChargeDepartment(model);
        List<ChargeForSModel> list = annlist;
        PageInfo<ChargeForSModel> page = new PageInfo<ChargeForSModel>(list);
        return page;
    }

    @Override
    public PageInfo<ChargeForSModel> ChargeMerchant(ChargeForSModel model, Integer pageNo, Integer pageSize) {
        pageNo = pageNo == null?1:pageNo;
        pageSize = pageSize == null?10:pageSize;
        PageHelper.startPage(pageNo, pageSize);

        List<ChargeForSModel> annlist = dao.ChargeMerchant(model);
        List<ChargeForSModel> list = annlist;
        PageInfo<ChargeForSModel> page = new PageInfo<ChargeForSModel>(list);
        return page;
    }


}
