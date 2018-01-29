package com.iandtop.model.device;

import com.iandtop.model.pub.SuperModel;

/**
 * Created by lz on 2017/5/22.
 */
public class DeviceTimeSubModel extends SuperModel {

    private String pk_device_time_sub;
    private String pk_device_time;
    private String sub_name;
    private String start_time;
    private String end_time;
    private Integer frequency_time;

    public Integer getFrequency_time() {
        return frequency_time;
    }

    public void setFrequency_time(Integer frequency_time) {
        this.frequency_time = frequency_time;
    }

    public String getPk_device_time_sub() {
        return pk_device_time_sub;
    }

    public void setPk_device_time_sub(String pk_device_time_sub) {
        this.pk_device_time_sub = pk_device_time_sub;
    }

    public String getPk_device_time() {
        return pk_device_time;
    }

    public void setPk_device_time(String pk_device_time) {
        this.pk_device_time = pk_device_time;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    @Override
    public String getTableName() {
        return "device_time_sub";
    }
}
