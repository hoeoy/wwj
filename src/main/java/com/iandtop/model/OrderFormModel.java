package com.iandtop.model;

/**
 * Created by Administrator on 2017/5/18.
 */
public class OrderFormModel {
    private int  pk_formcode;	//订单编号
    private int  formstatus;	//订单状态
    private String  formpeople;	//订单人
    private String  formtime;	//下单日期
    private double  amount	;	//总金额
    private String  operationtime;   //操作时间
    private String  operator;	//操作人

    //此处的staff_code实际值是为pk_staff
    private String  staff_code; //员工编码
    private String stopTime; //截止时间
    private String passwrod;
    private double meal_money;
    private String card_code;
    private String pk_device;

    public String getPk_device() {
        return pk_device;
    }

    public void setPk_device(String pk_device) {
        this.pk_device = pk_device;
    }

    public String getCard_code() {
        return card_code;
    }

    public void setCard_code(String card_code) {
        this.card_code = card_code;
    }

    public double getMeal_money() {
        return meal_money;
    }

    public void setMeal_money(double meal_money) {
        this.meal_money = meal_money;
    }

    public String getPasswrod() {
        return passwrod;
    }

    public void setPasswrod(String passwrod) {
        this.passwrod = passwrod;
    }

    public String getStaff_code() {
        return staff_code;
    }

    public void setStaff_code(String staff_code) {
        this.staff_code = staff_code;
    }


    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public int getPk_formcode() {
        return pk_formcode;
    }

    public void setPk_formcode(int pk_formcode) {
        this.pk_formcode = pk_formcode;
    }

    public int getFormstatus() {
        return formstatus;
    }

    public void setFormstatus(int formstatus) {
        this.formstatus = formstatus;
    }

    public String getFormpeople() {
        return formpeople;
    }

    public void setFormpeople(String formpeople) {
        this.formpeople = formpeople;
    }

    public String getFormtime() {
        return formtime;
    }

    public void setFormtime(String formtime) {
        this.formtime = formtime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getOperationtime() {
        return operationtime;
    }

    public void setOperationtime(String operationtime) {
        this.operationtime = operationtime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
