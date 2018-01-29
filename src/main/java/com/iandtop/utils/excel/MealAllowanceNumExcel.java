package com.iandtop.utils.excel;

public class MealAllowanceNumExcel {
	 public MealAllowanceNumExcel() { 
	    }

	    @Excel(name = "pkAllowanceNum", width = 30)
	    private String pkAllowanceNum;//员工卡号ID

	    @Excel(name = "allowanceNumCode")
	    private String allowanceNumCode;//补贴金额

	    @Excel(name = "state")
	    private String state;//类型
	    
	    @Excel(name = "ts")
	    private String ts;//类型
	    
	    @Excel(name = "memo")
	    private String memo;//类型
	    
	    @Excel(name = "pkStaff")
	    private String pkStaff;//类型
	    
	    @Excel(name = "def1")
	    private String def1;//类型
	    
	    @Excel(name = "def2")
	    private String def2;//类型
	    
	    @Excel(name = "def3")
	    private String def3;//类型
	    
	    @Excel(name = "def4")
	    private String def4;//类型
	    
	    @Excel(name = "def5")
	    private String def5;//类型

		public String getPkAllowanceNum() {
			return pkAllowanceNum;
		}

		public void setPkAllowanceNum(String pkAllowanceNum) {
			this.pkAllowanceNum = pkAllowanceNum;
		}

		public String getAllowanceNumCode() {
			return allowanceNumCode;
		}

		public void setAllowanceNumCode(String allowanceNumCode) {
			this.allowanceNumCode = allowanceNumCode;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getTs() {
			return ts;
		}

		public void setTs(String ts) {
			this.ts = ts;
		}

		public String getMemo() {
			return memo;
		}

		public void setMemo(String memo) {
			this.memo = memo;
		}

		public String getPkStaff() {
			return pkStaff;
		}

		public void setPkStaff(String pkStaff) {
			this.pkStaff = pkStaff;
		}

		public String getDef1() {
			return def1;
		}

		public void setDef1(String def1) {
			this.def1 = def1;
		}

		public String getDef2() {
			return def2;
		}

		public void setDef2(String def2) {
			this.def2 = def2;
		}

		public String getDef3() {
			return def3;
		}

		public void setDef3(String def3) {
			this.def3 = def3;
		}

		public String getDef4() {
			return def4;
		}

		public void setDef4(String def4) {
			this.def4 = def4;
		}

		public String getDef5() {
			return def5;
		}

		public void setDef5(String def5) {
			this.def5 = def5;
		}
	    
	    

}
