package com.iandtop.dao;

import com.iandtop.model.device.DeviceTimeModel;

import java.util.List;

public interface DeviceTimeDAO {

    List<DeviceTimeModel> retrieveAll();

    int saveTime(DeviceTimeModel model);

    List<DeviceTimeModel> retrieveByPK(String pk_device_time);

    int updateByModel(DeviceTimeModel model);

    int deleteByPk(String pk_device_time);
}
