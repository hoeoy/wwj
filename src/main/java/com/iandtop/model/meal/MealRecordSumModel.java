package com.iandtop.model.meal;

import com.iandtop.model.pub.SuperModel;

/**
 * Created by Seven on 2017/8/1.
 */
public class MealRecordSumModel extends SuperModel {
    private String id;
    private String meal_ts;//时间
    private String pk_device;//设备pk
    private String dining_code;//餐别
    private Double meal_money;//消费金额

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMeal_ts() {
        return meal_ts;
    }

    public void setMeal_ts(String meal_ts) {
        this.meal_ts = meal_ts;
    }

    public String getPk_device() {
        return pk_device;
    }

    public void setPk_device(String pk_device) {
        this.pk_device = pk_device;
    }

    public String getDining_code() {
        return dining_code;
    }

    public void setDining_code(String dining_code) {
        this.dining_code = dining_code;
    }

    public Double getMeal_money() {
        return meal_money;
    }

    public void setMeal_money(Double meal_money) {
        this.meal_money = meal_money;
    }

    @Override
    public String getTableName() {
        return null;
    }
}
