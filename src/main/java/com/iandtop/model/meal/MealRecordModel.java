package com.iandtop.model.meal;

import com.iandtop.model.pub.SuperModel;
import com.iandtop.utils.MealRecordUtils;

import java.io.Serializable;

/**
 * Created by lz on 2017/5/24.
 */
public class MealRecordModel  extends SuperModel{

    //清零补贴
    public static String Charge_Type_Clear = "3";
    //累加补贴
    public static String Charge_Type_Add = "4";

    public static final String Meal_Type_Normal = "0";
    public static final String Meal_Type_Count = "1";
    public static final String Meal_Type_Correct = "2";
    public static final String Meal_Type_Corrections = "3";
    public static final String Meal_Type_Cancel = "4";
    public static final String Meal_Way_Line = "0";
    public static final String Meal_Way_OnLine = "1";

    private String pk_meal_record;
    private String pk_staff;
    private String pk_card;
    private String pk_device;
    private String card_code;
    private String staff_code;
    private String device_code;
    private Double meal_money;
    private Double meal_cash_money;
    private Double meal_allowance;
    private Double cash_retain;
    private Double allowance_retain;
    private Double money_retain;
    private String meal_type; //0:正常消费,1:计次,2:冲正,3:更正收款失误,4:消费撤销,5:补录
    private String meal_way;
    private String meal_ts;
    private String dining_name;
    private String device_meal_type;

    public String getDevice_meal_type() {
        return device_meal_type;
    }

    public void setDevice_meal_type(String device_meal_type) {
        this.device_meal_type = device_meal_type;
    }

    //冗余字段
    private String table_name;
    private String staff_name;
    private String department_name;
    private String device_name;
    private String serial;
    private String dining_code;// 餐次编码

    public String getDining_code() {
        return dining_code;
    }

    public void setDining_code(String dining_code) {
        this.dining_code = dining_code;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getPk_meal_record() {
        return pk_meal_record;
    }

    public void setPk_meal_record(String pk_meal_record) {
        this.pk_meal_record = pk_meal_record;
    }

    public String getPk_staff() {
        return pk_staff;
    }

    public void setPk_staff(String pk_staff) {
        this.pk_staff = pk_staff;
    }

    public String getPk_card() {
        return pk_card;
    }

    public void setPk_card(String pk_card) {
        this.pk_card = pk_card;
    }

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

    public String getStaff_code() {
        return staff_code;
    }

    public void setStaff_code(String staff_code) {
        this.staff_code = staff_code;
    }

    public String getDevice_code() {
        return device_code;
    }

    public void setDevice_code(String device_code) {
        this.device_code = device_code;
    }

    public Double getMeal_money() {
        return meal_money;
    }

    public void setMeal_money(Double meal_money) {
        this.meal_money = meal_money;
    }

    public Double getMeal_cash_money() {
        return meal_cash_money;
    }

    public void setMeal_cash_money(Double meal_cash_money) {
        this.meal_cash_money = meal_cash_money;
    }

    public Double getMeal_allowance() {
        return meal_allowance;
    }

    public void setMeal_allowance(Double meal_allowance) {
        this.meal_allowance = meal_allowance;
    }

    public Double getCash_retain() {
        return cash_retain;
    }

    public void setCash_retain(Double cash_retain) {
        this.cash_retain = cash_retain;
    }

    public Double getAllowance_retain() {
        return allowance_retain;
    }

    public void setAllowance_retain(Double allowance_retain) {
        this.allowance_retain = allowance_retain;
    }

    public Double getMoney_retain() {
        return money_retain;
    }

    public void setMoney_retain(Double money_retain) {
        this.money_retain = money_retain;
    }

    public String getMeal_type() {
        return meal_type;
    }

    public void setMeal_type(String meal_type) {
        this.meal_type = meal_type;
    }

    public String getMeal_way() {
        return meal_way;
    }

    public void setMeal_way(String meal_way) {
        this.meal_way = meal_way;
    }

    public String getMeal_ts() {
        return meal_ts;
    }

    public void setMeal_ts(String meal_ts) {
        this.meal_ts = meal_ts;
    }

    public String getDining_name() {
        return dining_name;
    }

    public void setDining_name(String dining_name) {
        this.dining_name = dining_name;
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

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    @Override
    public String getTableName() {
        return MealRecordUtils.getMealRecordName(System.currentTimeMillis());
    }
}
