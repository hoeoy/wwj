package com.iandtop.model.device;

import com.iandtop.model.pub.SuperModel;

/**
 * Created by lz on 2017/5/19.
 */
public class DeviceModel extends SuperModel {
    private String pk_device;
    private String pk_device_time;
    private String pk_merchant;
    private String device_code;
    private String device_name;
    private String device_ip;
    private String device_port;
    private String device_meal_type;
    private String be_control_time;
    private String device_type;



    //冗余字段
    private String time_name;

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getPk_device_time() {
        return pk_device_time;
    }

    public void setPk_device_time(String pk_device_time) {
        this.pk_device_time = pk_device_time;
    }

    public String getTime_name() {
        return time_name;
    }

    public void setTime_name(String time_name) {
        this.time_name = time_name;
    }

    public String getPk_device() {
        return pk_device;
    }

    public void setPk_device(String pk_device) {
        this.pk_device = pk_device;
    }

    public String getPk_merchant() {
        return pk_merchant;
    }

    public void setPk_merchant(String pk_merchant) {
        this.pk_merchant = pk_merchant;
    }

    public String getDevice_code() {
        return device_code;
    }

    public void setDevice_code(String device_code) {
        this.device_code = device_code;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getDevice_ip() {
        return device_ip;
    }

    public void setDevice_ip(String device_ip) {
        this.device_ip = device_ip;
    }

    public String getDevice_port() {
        return device_port;
    }

    public void setDevice_port(String device_port) {
        this.device_port = device_port;
    }

    public String getDevice_meal_type() {
        return device_meal_type;
    }

    public void setDevice_meal_type(String device_meal_type) {
        this.device_meal_type = device_meal_type;
    }

    public String getBe_control_time() {
        return be_control_time;
    }

    public void setBe_control_time(String be_control_time) {
        this.be_control_time = be_control_time;
    }

    @Override
    public String getTableName() {
        return "meal_device";
    }
}
