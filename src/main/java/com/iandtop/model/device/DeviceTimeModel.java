package com.iandtop.model.device;

import com.iandtop.model.pub.SuperModel;

import java.util.List;

/**
 * Created by lz on 2017/5/22.
 */
public class DeviceTimeModel extends SuperModel{

    private Integer pk_device_time;
    private String time_name;

    //冗余字段,接收前台信息
    private List<DeviceTimeSubModel> subs;

    public List<DeviceTimeSubModel> getSubs() {
        return subs;
    }

    public void setSubs(List<DeviceTimeSubModel> subs) {
        this.subs = subs;
    }

    public Integer getPk_device_time() {
        return pk_device_time;
    }

    public void setPk_device_time(Integer pk_device_time) {
        this.pk_device_time = pk_device_time;
    }

    public String getTime_name() {
        return time_name;
    }

    public void setTime_name(String time_name) {
        this.time_name = time_name;
    }

    @Override
    public String getTableName() {
        return "device_time";
    }
}
