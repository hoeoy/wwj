package com.iandtop.model.form;

import com.alibaba.fastjson.serializer.DoubleArraySerializer;
import com.iandtop.model.pub.SuperModel;

/**
 * Created by xss on 2017-05-24.
 */
public class ChargeSumModel extends SuperModel {

    private String pk_charge_record;
    private String user_name;//操作员姓名
    private String operator;//操作员PK
    private String charge_ts;//充值时间
    private Double subsidy;//补贴
    private Double subsidy_empty;//清零补贴
    private Double pledge_income;//卡押金收入
    private Double pledge_refund;//卡押金支出
    private Double charge_money;//充值金额
    private Double card_cost;//卡成本收入
    private Double refund_money;//退款金额
    private Double practical_money;//实收现金，除补贴以外
    private Double subsidy_refund;//补贴退款
    private String sum_date;//日期
    private String meal_money;//消费金额

    private String memo;

    public Double getSubsidy_refund() {
        return subsidy_refund;
    }

    public void setSubsidy_refund(Double subsidy_refund) {
        this.subsidy_refund = subsidy_refund;
    }

    public String getPk_charge_record() {
        return pk_charge_record;
    }

    public void setPk_charge_record(String pk_charge_record) {
        this.pk_charge_record = pk_charge_record;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getCharge_ts() {
        return charge_ts;
    }

    public void setCharge_ts(String charge_ts) {
        this.charge_ts = charge_ts;
    }

    public Double getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(Double subsidy) {
        this.subsidy = subsidy;
    }

    public Double getSubsidy_empty() {
        return subsidy_empty;
    }

    public void setSubsidy_empty(Double subsidy_empty) {
        this.subsidy_empty = subsidy_empty;
    }

    public Double getPledge_income() {
        return pledge_income;
    }

    public void setPledge_income(Double pledge_income) {
        this.pledge_income = pledge_income;
    }

    public Double getPledge_refund() {
        return pledge_refund;
    }

    public void setPledge_refund(Double pledge_refund) {
        this.pledge_refund = pledge_refund;
    }

    public Double getCharge_money() {
        return charge_money;
    }

    public void setCharge_money(Double charge_money) {
        this.charge_money = charge_money;
    }

    public Double getCard_cost() {
        return card_cost;
    }

    public void setCard_cost(Double card_cost) {
        this.card_cost = card_cost;
    }

    public Double getRefund_money() {
        return refund_money;
    }

    public void setRefund_money(Double refund_money) {
        this.refund_money = refund_money;
    }

    public Double getPractical_money() {
        return practical_money;
    }

    public void setPractical_money(Double practical_money) {
        this.practical_money = practical_money;
    }

    public String getSum_date() {
        return sum_date;
    }

    public void setSum_date(String sum_date) {
        this.sum_date = sum_date;
    }

    public String getMeal_money() {
        return meal_money;
    }

    public void setMeal_money(String meal_money) {
        this.meal_money = meal_money;
    }

    @Override
    public String getMemo() {
        return memo;
    }

    @Override
    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String getTableName() {
        return null;
    }
}
