package com.iandtop.service;

import com.github.pagehelper.PageInfo;
import com.iandtop.model.OrderModel;
import com.iandtop.model.form.ChargeForSModel;

import java.util.List;

/**
 * Created by Seven on 2017/9/27.
 */
public interface ChargeForSService {
    //补助发放记录汇总表
    PageInfo<ChargeForSModel> ChargeRecordByPage(ChargeForSModel model, Integer pageNo, Integer pageSize);
    //人员补贴汇总表
    PageInfo<ChargeForSModel> ChargeStaffByPage(ChargeForSModel model, Integer pageNo, Integer pageSize);
    //部门补贴汇总表
    PageInfo<ChargeForSModel> ChargeDepartment(ChargeForSModel model, Integer pageNo, Integer pageSize);
    //商户补贴汇总表
    PageInfo<ChargeForSModel> ChargeMerchant(ChargeForSModel model, Integer pageNo, Integer pageSize);
}
