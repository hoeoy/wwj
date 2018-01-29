package com.iandtop.utils.excel;

/**
 * @author Klin
 * @description 描述
 * @create 2017/5/25 0025
 * @Version 1.0
 */
public class MealAllowanceExcel {
    public MealAllowanceExcel() {
    }

    @Excel(name = "工号", width = 30)
    private String pkCard;//员工工号

    @Excel(name = "补贴金额")
    private String moneyAllowance;//补贴金额

    @Excel(name = "补贴类型")
    private String allowanceType;//类型

    public String getPkCard() {
        return pkCard;
    }

    public void setPkCard(String pkCard) {
        this.pkCard = pkCard;
    }

    public String getMoneyAllowance() {
        return moneyAllowance;
    }

    public void setMoneyAllowance(String moneyAllowance) {
        this.moneyAllowance = moneyAllowance;
    }

    public String getAllowanceType() {
        return allowanceType;
    }

    public void setAllowanceType(String allowanceType) {
        this.allowanceType = allowanceType;
    }
}
