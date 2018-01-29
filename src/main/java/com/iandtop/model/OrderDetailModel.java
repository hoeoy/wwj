package com.iandtop.model;

/**
 * Created by Administrator on 2017/5/18.
 */
public class OrderDetailModel {
    private int  detail;//明细id
    private int  fk_formcode;//订单编号
    private String  foodname;//商品名称
    private Double price;
    private int  count;//数量
    //订单表数据实体
    private String  formpeople;	//订单人
    private double  amount	;	//总金额
    private int  formstatus;	//订单状态

    public String getFormpeople() {
        return formpeople;
    }

    public void setFormpeople(String formpeople) {
        this.formpeople = formpeople;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getFormstatus() {
        return formstatus;
    }

    public void setFormstatus(int formstatus) {
        this.formstatus = formstatus;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getDetail() {
        return detail;
    }

    public void setDetail(int detail) {
        this.detail = detail;
    }

    public int getFk_formcode() {
        return fk_formcode;
    }

    public void setFk_formcode(int fk_formcode) {
        this.fk_formcode = fk_formcode;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
