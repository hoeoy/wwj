package com.iandtop.dao;

import com.iandtop.model.device.DeviceModel;
import com.iandtop.model.meal.MerchantModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xss on 2017-06-03.
 */
public interface TenantPosDAO {
    List<MerchantModel> queryMerchant();

    List<DeviceModel> queryDevice(@Param("pk_merchant") String pk_merchant);
}
