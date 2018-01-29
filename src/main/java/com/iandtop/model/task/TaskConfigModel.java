package com.iandtop.model.task;

import com.iandtop.model.pub.SuperModel;

/**
 * Created by lz on 2017/7/20.
 */
public class TaskConfigModel extends SuperModel {

    private String pk_task_config;
    private String company_code;
    private String url;
    private String user;
    private String password;
    private Integer frequency;
    private String last_time;

    public String getPk_task_config() {
        return pk_task_config;
    }

    public void setPk_task_config(String pk_task_config) {
        this.pk_task_config = pk_task_config;
    }

    public String getCompany_code() {
        return company_code;
    }

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public String getLast_time() {
        return last_time;
    }

    public void setLast_time(String last_time) {
        this.last_time = last_time;
    }

    @Override
    public String getTableName() {
        return "";
    }
}
