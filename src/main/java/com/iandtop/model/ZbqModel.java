package com.iandtop.model;

/**
 * User: Mr.zheng
 * Date: 2018/1/27
 * Time: 13:07
 */
public class ZbqModel {

    private String typename; //商品类别名称
    private String foodname; //商品名称
    private String formpeople; //下单人员
    private Integer count; //商品数量
    private Integer amount; //商品金额
    private String formtime; //下单时间
    private String stylename; //食堂名称

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getFormpeople() {
        return formpeople;
    }

    public void setFormpeople(String formpeople) {
        this.formpeople = formpeople;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getFormtime() {
        return formtime;
    }

    public void setFormtime(String formtime) {
        this.formtime = formtime;
    }

    public String getStylename() {
        return stylename;
    }

    public void setStylename(String stylename) {
        this.stylename = stylename;
    }
}
