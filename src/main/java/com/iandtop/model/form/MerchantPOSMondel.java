package com.iandtop.model.form;

import com.iandtop.model.pub.SuperModel;

/**
 * Created by xss on 2017-06-03.
 */
public class MerchantPOSMondel extends SuperModel {

    private String sum_date;
    private String  merchant_code;
    private String merchant_name;
    private String  device_code;
    private String device_name;
    private String  breakfast_num;
    private Double breakfast_money;
    private String lunch_num;
    private Double lunch_money;
    private String dinner_num;
    private Double dinner_money;
    private String night_num;
    private Double Night_money;
    private String else_num;
    private Double else_money;
    private String sum_num;
    private Double sum_money;
    private String memo;

    public String getSum_date() {
        return sum_date;
    }

    public void setSum_date(String sum_date) {
        this.sum_date = sum_date;
    }

    public String getMerchant_code() {
        return merchant_code;
    }

    public void setMerchant_code(String merchant_code) {
        this.merchant_code = merchant_code;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
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

    public String getBreakfast_num() {
        return breakfast_num;
    }

    public void setBreakfast_num(String breakfast_num) {
        this.breakfast_num = breakfast_num;
    }

    public String getLunch_num() {
        return lunch_num;
    }

    public void setLunch_num(String lunch_num) {
        this.lunch_num = lunch_num;
    }

    public String getDinner_num() {
        return dinner_num;
    }

    public void setDinner_num(String dinner_num) {
        this.dinner_num = dinner_num;
    }

    public String getNight_num() {
        return night_num;
    }

    public void setNight_num(String night_num) {
        this.night_num = night_num;
    }

    public String getElse_num() {
        return else_num;
    }

    public void setElse_num(String else_num) {
        this.else_num = else_num;
    }

    public String getSum_num() {
        return sum_num;
    }

    public void setSum_num(String sum_num) {
        this.sum_num = sum_num;
    }

    public Double getBreakfast_money() {
        return breakfast_money;
    }

    public void setBreakfast_money(Double breakfast_money) {
        this.breakfast_money = breakfast_money;
    }

    public Double getLunch_money() {
        return lunch_money;
    }

    public void setLunch_money(Double lunch_money) {
        this.lunch_money = lunch_money;
    }

    public Double getDinner_money() {
        return dinner_money;
    }

    public void setDinner_money(Double dinner_money) {
        this.dinner_money = dinner_money;
    }

    public Double getNight_money() {
        return Night_money;
    }

    public void setNight_money(Double night_money) {
        Night_money = night_money;
    }

    public Double getElse_money() {
        return else_money;
    }

    public void setElse_money(Double else_money) {
        this.else_money = else_money;
    }

    public Double getSum_money() {
        return sum_money;
    }

    public void setSum_money(Double sum_money) {
        this.sum_money = sum_money;
    }

    @Override
    public String getMemo() {
        return memo;
    }

    @Override
    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String getTableName() {
        return null;
    }
}
