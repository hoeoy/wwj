package com.iandtop.model.meal;

import com.iandtop.model.pub.SuperModel;

/**
 * Created by lz on 2017/5/20.
 */
public class DiningModel extends SuperModel {

    private String pk_dining;
    private String dining_code;
    private String dining_name;
    private String begin_time;
    private String end_time;
    private Double price;
    private String be_valid;

    public String getPk_dining() {
        return pk_dining;
    }

    public void setPk_dining(String pk_dining) {
        this.pk_dining = pk_dining;
    }

    public String getDining_code() {
        return dining_code;
    }

    public void setDining_code(String dining_code) {
        this.dining_code = dining_code;
    }

    public String getDining_name() {
        return dining_name;
    }

    public void setDining_name(String dining_name) {
        this.dining_name = dining_name;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBe_valid() {
        return be_valid;
    }

    public void setBe_valid(String be_valid) {
        this.be_valid = be_valid;
    }

    @Override
    public String getTableName() {
        return "meal_dining";
    }
}
