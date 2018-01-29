package com.iandtop.model.card;

import com.iandtop.model.pub.SuperModel;

/**
 * Created by lz on 2017/5/13.
 */
public class CardChargeRecordModel extends SuperModel{

    public static final String Charge_Type_LineCash = "0";
    public static final String Charge_Type_Deposit = "1";
    public static final String Charge_Type_Cost = "2";
    public static final String Charge_Type_Allowance_Clear = "3";
    public static final String Charge_Type_Allowance_Add = "4";

    private String pk_charge_record;
    private String pk_card;
    private String pk_staff;
    private String card_code;
    private String staff_code;
    private Double charge_money;
    private Double money_retain;
    private String operator;
    private String charge_type;             //充值类型  0线下现金 1卡押金 2卡成本
    private long card_batchnum;
    private String charge_ts;
    private String isChecked;//用于判断是否清零

    //冗余
    private String staff_name;
    private String department_name;
    private String user_name;
    private String serial;

    public long getCard_batchnum() {
        return card_batchnum;
    }

    public void setCard_batchnum(long card_batchnum) {
        this.card_batchnum = card_batchnum;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }

    public String getCharge_ts() {
        return charge_ts;
    }

    public void setCharge_ts(String charge_ts) {
        this.charge_ts = charge_ts;
    }

    public String getPk_charge_record() {
        return pk_charge_record;
    }

    public void setPk_charge_record(String pk_charge_record) {
        this.pk_charge_record = pk_charge_record;
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

    public Double getCharge_money() {
        return charge_money;
    }

    public void setCharge_money(Double charge_money) {
        this.charge_money = charge_money;
    }

    public Double getMoney_retain() {
        return money_retain;
    }

    public void setMoney_retain(Double money_retain) {
        this.money_retain = money_retain;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getCharge_type() {
        return charge_type;
    }

    public void setCharge_type(String charge_type) {
        this.charge_type = charge_type;
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

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    @Override
    public String getTableName() {
        return "card_charge_record";
    }
}
