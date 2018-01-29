package com.iandtop.model.card;

import com.iandtop.model.pub.SuperModel;

/**
 * Created by lz on 2017/5/13.
 */
public class CardModel extends SuperModel {

    public static final String State_Normal = "10";
    public static final String State_Lost = "20";
    public static final String State_Retuen = "40";

    private String pk_card;
    private String pk_staff;
    private String card_code;
    private String card_state;
    private String card_issue_ts;
    private String card_ineffectived_ts;
    private Double card_costing;
    private Double card_deposit;
    private Double money_cash;
    private Double money_allowance;
    private String pk_meal_rule;
    private String password;
    private Integer serial;

    private String staff_code;
    private String staff_name;

    private String operator_type;
    private String department_code;

    //冗余字段
    private Integer effective_months;
    private String operator;
    private String department_name;
    private Double money_sum;
    private String meal_rule_name;
    private String staff_type;
    private String pk_merchant;

    public String getPk_merchant() {
        return pk_merchant;
    }

    public void setPk_merchant(String pk_merchant) {
        this.pk_merchant = pk_merchant;
    }

    public String getStaff_type() {
        return staff_type;
    }

    public void setStaff_type(String staff_type) {
        this.staff_type = staff_type;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getEffective_months() {
        return effective_months;
    }

    public void setEffective_months(Integer effective_months) {
        this.effective_months = effective_months;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public String getDepartment_code() {
        return department_code;
    }

    public void setDepartment_code(String department_code) {
        this.department_code = department_code;
    }

    public String getOperator_type() {
        return operator_type;
    }

    public void setOperator_type(String operator_type) {
        this.operator_type = operator_type;
    }

    public String getStaff_code() {
        return staff_code;
    }

    public void setStaff_code(String staff_code) {
        this.staff_code = staff_code;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
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

    public String getCard_state() {
        return card_state;
    }

    public void setCard_state(String card_state) {
        this.card_state = card_state;
    }

    public String getCard_issue_ts() {
        return card_issue_ts;
    }

    public void setCard_issue_ts(String card_issue_ts) {
        this.card_issue_ts = card_issue_ts;
    }

    public String getCard_ineffectived_ts() {
        return card_ineffectived_ts;
    }

    public void setCard_ineffectived_ts(String card_ineffectived_ts) {
        this.card_ineffectived_ts = card_ineffectived_ts;
    }

    public Double getCard_costing() {
        return card_costing;
    }

    public void setCard_costing(Double card_costing) {
        this.card_costing = card_costing;
    }

    public Double getCard_deposit() {
        return card_deposit;
    }

    public void setCard_deposit(Double card_deposit) {
        this.card_deposit = card_deposit;
    }

    public Double getMoney_cash() {
        return money_cash;
    }

    public void setMoney_cash(Double money_cash) {
        this.money_cash = money_cash;
    }

    public Double getMoney_allowance() {
        return money_allowance;
    }

    public void setMoney_allowance(Double money_allowance) {
        this.money_allowance = money_allowance;
    }

    public String getPk_meal_rule() {
        return pk_meal_rule;
    }

    public void setPk_meal_rule(String pk_meal_rule) {
        this.pk_meal_rule = pk_meal_rule;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public Double getMoney_sum() {
        return money_sum;
    }

    public void setMoney_sum(Double money_sum) {
        this.money_sum = money_sum;
    }

    public String getMeal_rule_name() {
        return meal_rule_name;
    }

    public void setMeal_rule_name(String meal_rule_name) {
        this.meal_rule_name = meal_rule_name;
    }

    @Override
    public String getTableName() {
        return "card_card";
    }
}
