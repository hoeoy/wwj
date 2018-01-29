package com.iandtop.model.card;

import com.iandtop.model.pub.SuperModel;

/**
 * Created by lz on 2017/5/13.
 */
public class CardRefundRecordModel extends SuperModel {

    public static final String Refund_Type_Cash = "0";
    public static final String Refund_Type_Allowance = "1";
    public static final String Refund_Type_Deposit = "2";

    private String pk_refund_record;
    private String pk_card;
    private String pk_staff;
    private String card_code;
    private String staff_code;
    private Double refund_money;
    private Double money_retain;
    private String refund_type;
    private String refund_ts;
    private String operator;
    private Integer card_batchnum;

    //冗余
    private String staff_name;
    private String department_name;
    private String user_name;

    public String getPk_refund_record() {
        return pk_refund_record;
    }

    public void setPk_refund_record(String pk_refund_record) {
        this.pk_refund_record = pk_refund_record;
    }

    public String getPk_card() {
        return pk_card;
    }

    public void setPk_card(String pk_card) {
        this.pk_card = pk_card;
    }

    public String getPk_staff() {
        return pk_staff;
    }

    public void setPk_staff(String pk_staff) {
        this.pk_staff = pk_staff;
    }

    public String getCard_code() {
        return card_code;
    }

    public void setCard_code(String card_code) {
        this.card_code = card_code;
    }

    public String getStaff_code() {
        return staff_code;
    }

    public void setStaff_code(String staff_code) {
        this.staff_code = staff_code;
    }

    public Double getRefund_money() {
        return refund_money;
    }

    public void setRefund_money(Double refund_money) {
        this.refund_money = refund_money;
    }

    public Double getMoney_retain() {
        return money_retain;
    }

    public void setMoney_retain(Double money_retain) {
        this.money_retain = money_retain;
    }

    public String getRefund_type() {
        return refund_type;
    }

    public void setRefund_type(String refund_type) {
        this.refund_type = refund_type;
    }

    public String getRefund_ts() {
        return refund_ts;
    }

    public void setRefund_ts(String refund_ts) {
        this.refund_ts = refund_ts;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getCard_batchnum() {
        return card_batchnum;
    }

    public void setCard_batchnum(Integer card_batchnum) {
        this.card_batchnum = card_batchnum;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public String getTableName() {
        return "card_refund_record";
    }
}
