package com.iandtop.model.card;

import com.iandtop.model.pub.SuperModel;

/**
 * Created by lz on 2017/5/12.
 */
public class CardParamModel extends SuperModel{

    private String pk_card_param;
    private String pk_company;
    private String name;
    private Double costing;
    private Double deposit;
    private Integer effective_months;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPk_card_param() {
        return pk_card_param;
    }

    public void setPk_card_param(String pk_card_param) {
        this.pk_card_param = pk_card_param;
    }

    public String getPk_company() {
        return pk_company;
    }

    public void setPk_company(String pk_company) {
        this.pk_company = pk_company;
    }

    public Double getCosting() {
        return costing;
    }

    public void setCosting(Double costing) {
        this.costing = costing;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Integer getEffective_months() {
        return effective_months;
    }

    public void setEffective_months(Integer effective_months) {
        this.effective_months = effective_months;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getTableName() {
        return "card_param";
    }
}
