package com.iandtop.model.meal;

import com.iandtop.model.pub.SuperModel;

/**
 * Created by lz on 2017/5/20.
 */
public class MealRuleModel extends SuperModel{

    private String pk_meal_rule;
    private String meal_rule_code;
    private String meal_rule_name;
    private Integer frequency_day;
    private Integer money_day;
    private Integer frequency_time;
    private Integer money_time;

    public String getPk_meal_rule() {
        return pk_meal_rule;
    }

    public void setPk_meal_rule(String pk_meal_rule) {
        this.pk_meal_rule = pk_meal_rule;
    }

    public String getMeal_rule_code() {
        return meal_rule_code;
    }

    public void setMeal_rule_code(String meal_rule_code) {
        this.meal_rule_code = meal_rule_code;
    }

    public String getMeal_rule_name() {
        return meal_rule_name;
    }

    public void setMeal_rule_name(String meal_rule_name) {
        this.meal_rule_name = meal_rule_name;
    }

    public Integer getFrequency_day() {
        return frequency_day;
    }

    public void setFrequency_day(Integer frequency_day) {
        this.frequency_day = frequency_day;
    }

    public Integer getMoney_day() {
        return money_day;
    }

    public void setMoney_day(Integer money_day) {
        this.money_day = money_day;
    }

    public Integer getFrequency_time() {
        return frequency_time;
    }

    public void setFrequency_time(Integer frequency_time) {
        this.frequency_time = frequency_time;
    }

    public Integer getMoney_time() {
        return money_time;
    }

    public void setMoney_time(Integer money_time) {
        this.money_time = money_time;
    }

    @Override
    public String getTableName() {
        return "meal_rule";
    }
}
