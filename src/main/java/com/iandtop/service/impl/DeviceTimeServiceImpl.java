package com.iandtop.service.impl;

import com.iandtop.dao.DeviceTimeDAO;
import com.iandtop.dao.DeviceTimeSubDAO;
import com.iandtop.model.device.DeviceModel;
import com.iandtop.model.device.DeviceTimeModel;
import com.iandtop.model.device.DeviceTimeSubModel;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.service.DeviceTimeService;
import com.iandtop.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Klin
 * @description 描述
 * @create 2017/5/24 0024
 * @Version 1.0
 */
@Service
public class DeviceTimeServiceImpl implements DeviceTimeService {

    @Autowired
    private DeviceTimeDAO timeDAO;

    @Autowired
    private DeviceTimeSubDAO subDAO;

    public int saveTime(DeviceTimeModel model) {

        Integer result;
        result = timeDAO.saveTime(model);
        if(result < 1){
            return StatusCodeConstants.Fail;
        }

        List<DeviceTimeSubModel> subModels = model.getSubs();

        for (DeviceTimeSubModel sub : subModels) {
            sub.setPk_device_time(model.getPk_device_time().toString());
            result = subDAO.saveTime(sub);
            if(result < 1){
                return StatusCodeConstants.Fail;
            }
        }

        return StatusCodeConstants.Success;
    }

    public List<DeviceTimeModel> findTime(String pk_device_time) {
        return timeDAO.retrieveByPK(pk_device_time);
    }

    public int updateTime(DeviceTimeModel model) {

        Integer result;

        result = timeDAO.updateByModel(model);

        if(result < 1){
            return StatusCodeConstants.Fail;
        }

        List<DeviceTimeSubModel> subs = model.getSubs();
        for (DeviceTimeSubModel sub : subs) {

            if(sub.getPk_device_time_sub() == null || sub.getPk_device_time_sub().trim().length() == 0){
                result = subDAO.saveTime(sub);
            }else{
                result = subDAO.updateTime(sub);
            }
            if(result < 1){
                return StatusCodeConstants.Fail;
            }
        }

        return StatusCodeConstants.Success;
    }

    public int delTime(String pk_device_time) {

        Integer result;
        //删子表
        result = subDAO.deleteByTimePk(pk_device_time);
        //删主表
        result = timeDAO.deleteByPk(pk_device_time);
        if(result < 1){
            return StatusCodeConstants.Fail;
        }

        return StatusCodeConstants.Success;
    }

    @Override
    public List<DeviceTimeModel> retrieveTree() {

        List<DeviceTimeModel> models = timeDAO.retrieveAll();

        for (DeviceTimeModel mo : models) {
            mo.setTreeId(mo.getPk_device_time().toString());
            mo.setTreeText(mo.getTime_name());
        }

        return models;
    }
}
