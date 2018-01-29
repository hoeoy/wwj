package com.iandtop.service;

import com.iandtop.model.device.DeviceModel;
import com.iandtop.model.device.DeviceTimeModel;

import java.util.List;

public interface DeviceTimeService {
    int saveTime(DeviceTimeModel model);

    List<DeviceTimeModel> findTime(String pk_device_time);

    int updateTime(DeviceTimeModel model);

    int delTime(String pk_device_time_sub);

    List<DeviceTimeModel> retrieveTree();
}
