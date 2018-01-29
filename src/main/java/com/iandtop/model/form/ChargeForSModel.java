package com.iandtop.model.form;

/**
 * Created by Seven on 2017/9/27.
 */
public class ChargeForSModel {

    private String department_code; //部门code
    private String department_name; //部门名称
    private String user_name;       //操作员姓名
    private String pk_user;         //操作员PK
    private String charge_ts;       //充值时间
    private Double money;           //金额
    private String headcount;       //总人数
    private String start_date;      //开始日期
    private String end_date;        //结束日期
    private String staff_code;      //人员编号
    private String numbercount;     //补贴次数 总次数
    private String staff_name;      //人员姓名
    private Double moneysum;        //补贴合计
    private Double charge_money;    //补贴金额
    //商户补贴
    private String pk_merchant;     //商户pk
    private String merchant_code;   //商户code
    private String merchant_name;   //商户名称

    public Double getCharge_money() {
        return charge_money;
    }

    public void setCharge_money(Double charge_money) {
        this.charge_money = charge_money;
    }

    public String getPk_merchant() {
        return pk_merchant;
    }

    public void setPk_merchant(String pk_merchant) {
        this.pk_merchant = pk_merchant;
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

    public String getStaff_code() {
        return staff_code;
    }

    public void setStaff_code(String staff_code) {
        this.staff_code = staff_code;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getNumbercount() {
        return numbercount;
    }

    public void setNumbercount(String numbercount) {
        this.numbercount = numbercount;
    }

    public Double getMoneysum() {
        return moneysum;
    }

    public void setMoneysum(Double moneysum) {
        this.moneysum = moneysum;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public String getDepartment_code() {
        return department_code;
    }

    public void setDepartment_code(String department_code) {
        this.department_code = department_code;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPk_user() {
        return pk_user;
    }

    public void setPk_user(String pk_user) {
        this.pk_user = pk_user;
    }

    public String getCharge_ts() {
        return charge_ts;
    }

    public void setCharge_ts(String charge_ts) {
        this.charge_ts = charge_ts;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getHeadcount() {
        return headcount;
    }

    public void setHeadcount(String headcount) {
        this.headcount = headcount;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
