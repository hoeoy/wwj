package com.iandtop.model.meal;

import com.iandtop.model.pub.SuperModel;

/**
 * @author Klin
 * @description 描述
 * @create 2017/5/24 0024
 * @Version 1.0
 */
public class MealAllowanceNumModel extends SuperModel {

	//未发放
	public static final Integer Allowance_State_No_Grant = 0;
	//已发放
	public static final Integer Allowance_State_Already_Grant = 2;

	private long pk_allowance_num;  //批次ID
	private long allowance_num_code; //订单号
	private Integer state;
	private String stateName;
	private long pk_staff;//当期操作人
	private String staff_name;
	private String start_time;
	private String end_time;
	private String ids;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getStaff_name() {
		return staff_name;
	}

	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}


	public long getPk_allowance_num() {
		return pk_allowance_num;
	}

	public void setPk_allowance_num(long pk_allowance_num) {
		this.pk_allowance_num = pk_allowance_num;
	}

	public long getAllowance_num_code() {
		return allowance_num_code;
	}

	public void setAllowance_num_code(long allowance_num_code) {
		this.allowance_num_code = allowance_num_code;
	}

	public Integer getState() {
		return state;
	}

	public long getPk_staff() {
		return pk_staff;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public void setPk_staff(long pk_staff) {
		this.pk_staff = pk_staff;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getTableName() {
		return "meal_allowance_num";
	}
}
