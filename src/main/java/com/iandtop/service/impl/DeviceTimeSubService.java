package com.iandtop.service.impl;

import com.iandtop.dao.DeviceTimeSubDAO;
import com.iandtop.model.device.DeviceTimeSubModel;
import com.iandtop.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Klin
 * @description 描述
 * @create 2017/5/23 0023
 * @Version 1.0
 */
@Service
public class DeviceTimeSubService implements com.iandtop.service.DeviceTimeSubService {

    @Autowired
    private DeviceTimeSubDAO dao;


    public int saveTime(DeviceTimeSubModel model) throws ParseException {
        model.setStart_time(String.valueOf(DateUtils.strToTimeMi(model.getStart_time())));
        model.setEnd_time(String.valueOf(DateUtils.strToTimeMi(model.getEnd_time())));
        return dao.saveTime(model);
    }

    public List<DeviceTimeSubModel> findTime(String pk_device_time) {

        if(pk_device_time == null || pk_device_time.trim().length() == 0){
            return new ArrayList<DeviceTimeSubModel>();
        }

        return dao.findTime(pk_device_time);
    }

    public int updateTime(DeviceTimeSubModel model) {
        return dao.updateTime(model);
    }

    public int delTime(String pk_device_time_sub) {
        return dao.delTime(pk_device_time_sub);
    }
}
