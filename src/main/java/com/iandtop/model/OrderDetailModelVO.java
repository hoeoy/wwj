package com.iandtop.model;

/**
 * Created by Administrator on 2017/5/18.
 */
public class OrderDetailModelVO extends OrderDetailModel{
    private String staff_code;
    private String passwrod;
    private String formpeople;
    private Double meal_money;
    private String user_name;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getStaff_code() {
        return staff_code;
    }

    public void setStaff_code(String staff_code) {
        this.staff_code = staff_code;
    }

    public String getPasswrod() {
        return passwrod;
    }

    public void setPasswrod(String passwrod) {
        this.passwrod = passwrod;
    }

    @Override
    public String getFormpeople() {
        return formpeople;
    }

    @Override
    public void setFormpeople(String formpeople) {
        this.formpeople = formpeople;
    }

    public Double getMeal_money() {
        return meal_money;
    }

    public void setMeal_money(Double meal_money) {
        this.meal_money = meal_money;
    }
}
