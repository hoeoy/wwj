package com.iandtop.service;

import com.iandtop.model.device.DeviceTimeSubModel;

import java.text.ParseException;
import java.util.List;

public interface DeviceTimeSubService {
    int saveTime(DeviceTimeSubModel model) throws ParseException;

    List<DeviceTimeSubModel> findTime(String pk_device_time);

    int updateTime(DeviceTimeSubModel model);

    int delTime(String pk_device_time_sub);
}
