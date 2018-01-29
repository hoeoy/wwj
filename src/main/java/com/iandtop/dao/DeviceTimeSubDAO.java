package com.iandtop.dao;

import com.iandtop.model.device.DeviceTimeSubModel;

import java.util.List;

public interface DeviceTimeSubDAO {

    int saveTime(DeviceTimeSubModel model);

    List<DeviceTimeSubModel> findTime(String pk_device_time);

    int updateTime(DeviceTimeSubModel model);

    int delTime(String pk_device_time_sub);

    int deleteByTimePk(String pk_device_time);

}
