package com.iandtop.model.meal;

import com.iandtop.model.pub.SuperModel;

import java.util.List;

/**
 * @author Klin
 * @description 描述
 * @create 2017/5/24 0024
 * @Version 1.0
 */
public class MealAllowanceModel extends SuperModel {

    /**
     * 补贴唯一ID
     */
    private long pk_allowance; //补贴唯一ID

    /**
     * 批次ID
     */
    private long pk_allowance_num;//批次ID
    /**
     * 员工卡号ID
     */
    private String pk_card;//员工卡号ID
    /**
     * 补贴金额
     */
    private double money_allowance;//补贴金额
    /**
     * 补贴类型
     */
    private Integer allowance_type;//

	/**
	 * 补贴明细表补贴类型 0累加
	 */
	public static final Integer MealAllowacne_Type_Add = 0;

	/**
	 *  明细表补贴类型  1清零
	 */
	public static final Integer MealAllowacne_Type_Clear = 1;

    private int pk_staff;//人员ID
    private String allowanceTypeName;//累加标识
    private String staff_code;//员工编号
    private String staff_name;//员工姓名
    private String allowance_num_code;
    private String statename;//发放状态
    private String ids;
    private String card_code;
    private String operator;
	private double money_cash;//现金钱包
	private double wallet_allowance;//补贴钱包
	private String department_name; //单位名称

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

	public double getMoney_cash() {
		return money_cash;
	}

	public void setMoney_cash(double money_cash) {
		this.money_cash = money_cash;
	}

	public double getWallet_allowance() {
		return wallet_allowance;
	}

	public void setWallet_allowance(double wallet_allowance) {
		this.wallet_allowance = wallet_allowance;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public int getPk_staff() {
		return pk_staff;
	}

	public void setPk_staff(int pk_staff) {
		this.pk_staff = pk_staff;
	}


    public long getPk_allowance() {
		return pk_allowance;
	}

	public void setPk_allowance(long pk_allowance) {
		this.pk_allowance = pk_allowance;
	}

	public long getPk_allowance_num() {
		return pk_allowance_num;
	}

	public void setPk_allowance_num(long pk_allowance_num) {
		this.pk_allowance_num = pk_allowance_num;
	}

	public String getPk_card() {
        return pk_card;
    }

    public void setPk_card(String pk_card) {
        this.pk_card = pk_card;
    }

    public double getMoney_allowance() {
        return money_allowance;
    }

    public void setMoney_allowance(double money_allowance) {
        this.money_allowance = money_allowance;
    }

    public String getAllowanceTypeName() {
		return allowanceTypeName;
	}

	public void setAllowanceTypeName(String allowanceTypeName) {
		this.allowanceTypeName = allowanceTypeName;
	}

	public Integer getAllowance_type() {
        return allowance_type;
    }

    public void setAllowance_type(Integer allowance_type) {
        this.allowance_type = allowance_type;
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

	public String getAllowance_num_code() {
		return allowance_num_code;
	}

	public void setAllowance_num_code(String allowance_num_code) {
		this.allowance_num_code = allowance_num_code;
	}

	public String getTableName() {
        return "meal_allowance";
    }

	public String getCard_code() {
		return card_code;
	}

	public void setCard_code(String card_code) {
		this.card_code = card_code;
	}
}
