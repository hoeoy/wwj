package com.iandtop.model.card;

import com.iandtop.model.pub.SuperModel;

/**
 * Created by lz on 2017/3/10.
 */
public class CardChangeRecordModel extends SuperModel {

    public static final String Operator_Type_Issue = "0";
    public static final String Operator_Type_Return = "1";
    public static final String Operator_Type_Change = "2";

    private String pk_change_record;
    private String pk_card;
    private String pk_staff;
    private String operator;

    private String card_code;
    private String staff_code;

    private String operation_type;          //操作类型   发卡0、退卡1、补卡2
    private String operation_ts;            //操作时间

    //如果是补卡操作,下面这些属性会记录新卡的信息
    private String cardcode_new;            //新卡号
    private Double money_remain;            //补卡时卡余额
    private Integer old_serial_number;      //补卡时流水号

    //冗余字段
    //冗余字段
    private boolean be_return_cash;
    private boolean be_return_allowance;
    private boolean be_return_deposit;
    private Double card_deposit;
    private Double card_cost;

    //冗余
    private String staff_name;
    private String department_name;
    private String user_name;
    private String cash_money;
    private String patch_money;
    private String cost_money;
    private String charge_money;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public boolean isBe_return_deposit() {
        return be_return_deposit;
    }

    public void setBe_return_deposit(boolean be_return_deposit) {
        this.be_return_deposit = be_return_deposit;
    }

    public boolean isBe_return_allowance() {
        return be_return_allowance;
    }

    public void setBe_return_allowance(boolean be_return_allowance) {
        this.be_return_allowance = be_return_allowance;
    }

    public boolean isBe_return_cash() {
        return be_return_cash;
    }

    public void setBe_return_cash(boolean be_return_cash) {
        this.be_return_cash = be_return_cash;
    }

    public static String getOperator_Type_Issue() {
        return Operator_Type_Issue;
    }

    public static String getOperator_Type_Return() {
        return Operator_Type_Return;
    }

    public static String getOperator_Type_Change() {
        return Operator_Type_Change;
    }

    public String getPk_change_record() {
        return pk_change_record;
    }

    public void setPk_change_record(String pk_change_record) {
        this.pk_change_record = pk_change_record;
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

    public String getOperation_type() {
        return operation_type;
    }

    public void setOperation_type(String operation_type) {
        this.operation_type = operation_type;
    }

    public String getOperation_ts() {
        return operation_ts;
    }

    public void setOperation_ts(String operation_ts) {
        this.operation_ts = operation_ts;
    }

    public String getCardcode_new() {
        return cardcode_new;
    }

    public void setCardcode_new(String cardcode_new) {
        this.cardcode_new = cardcode_new;
    }

    public Double getMoney_remain() {
        return money_remain;
    }

    public void setMoney_remain(Double money_remain) {
        this.money_remain = money_remain;
    }

    public Integer getOld_serial_number() {
        return old_serial_number;
    }

    public void setOld_serial_number(Integer old_serial_number) {
        this.old_serial_number = old_serial_number;
    }

    public Double getCard_deposit() {
        return card_deposit;
    }

    public void setCard_deposit(Double card_deposit) {
        this.card_deposit = card_deposit;
    }

    public Double getCard_cost() {
        return card_cost;
    }

    public void setCard_cost(Double card_cost) {
        this.card_cost = card_cost;
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

    public String getCash_money() {
        return cash_money;
    }

    public void setCash_money(String cash_money) {
        this.cash_money = cash_money;
    }

    public String getPatch_money() {
        return patch_money;
    }

    public void setPatch_money(String patch_money) {
        this.patch_money = patch_money;
    }

    public String getCost_money() {
        return cost_money;
    }

    public void setCost_money(String cost_money) {
        this.cost_money = cost_money;
    }

    public String getCharge_money() {
        return charge_money;
    }

    public void setCharge_money(String charge_money) {
        this.charge_money = charge_money;
    }

    @Override
    public String getTableName() {
        return "card_change_record";
    }
}
