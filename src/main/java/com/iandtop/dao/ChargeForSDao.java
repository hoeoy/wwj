package com.iandtop.dao;

import com.iandtop.model.form.ChargeForSModel;

import java.util.List;

/**
 * Created by Seven on 2017/9/27.
 */
public interface ChargeForSDao {
    //补助发放记录汇总表
    List<ChargeForSModel> ChargeRecord(ChargeForSModel model);
    //人员补贴汇总表
    List<ChargeForSModel> ChargeStaff(ChargeForSModel model);
    //部门补贴汇总表
    List<ChargeForSModel> ChargeDepartment(ChargeForSModel model);
    //商户补贴汇总表
    List<ChargeForSModel> ChargeMerchant(ChargeForSModel model);
}
