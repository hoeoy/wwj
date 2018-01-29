package com.iandtop.service;

import com.iandtop.model.device.DeviceModel;
import com.iandtop.model.form.MerchantPOSMondel;
import com.iandtop.model.form.MerchantPos;
import com.iandtop.model.meal.MerchantModel;

import java.util.List;

/**
 * Created by xss on 2017-05-24.
 */
public interface TenantPosService {

    List<MerchantModel> queryMerchant();

    List<DeviceModel> queryDevice(String pk_merchant);

    List<MerchantPOSMondel> queryMerchantDay(String pk_merchant, String pk_device, String start_ts, String end_ts);

    List<MerchantPOSMondel> queryMerchantSum(String pk_merchant, String pk_device, String start_ts, String end_ts);

    List<MerchantPos> findSumbyMerchant( Long pk_merchant, String start_ts, String end_ts);

    List<MerchantPos> findSumbyMerchantExtend( Long pk_merchant, String start_ts, String end_ts);

}
