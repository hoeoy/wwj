package com.iandtop.utils.excel;

/**
 * Created by Administrator on 2017/6/2.
 */
public class StaffExcel {
    public StaffExcel(){
    }
    @Override
    public String toString() {
        return "MyTest{" +"name='" + staff_code + '\'' +", staff_name='" + staff_name + '\'' +
                ", department_code='" + department_code + '\'' +
                ", staff_type='" + staff_type + '\'' +
                ", sex='" + sex + '\'' +
                ", id_card='" + id_card + '\'' +
                ", job_code='" + job_code + '\'' +
                ", edu_code='" + edu_code + '\'' +
                ", nation_code='" + nation_code + '\'' +
                ", birth_date='" + birth_date + '\'' +
                ", hire_date='" + hire_date + '\'' +
                ", leave_date='" + leave_date + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

        @Excel(name = "员工编码")
        private String staff_code;

        @Excel(name = "姓名")
        private String staff_name;

        @Excel(name = "关联部门")
        private String department_code;

        @Excel(name = "员工状态")
        private String staff_type;

        @Excel(name = "性别")
        private String sex;

        @Excel(name = "身份证号")
        private String id_card;

        @Excel(name = "职务编码")
        private String job_code;

        @Excel(name = "学历编码")
        private String edu_code;

        @Excel(name = "民族编码")
        private String nation_code;

        @Excel(name = "生日")//
        private String birth_date;

        @Excel(name = "入职日期")
        private String hire_date;

        @Excel(name = "离职日期")
        private String leave_date;

        @Excel(name = "电子邮箱")
        private String email;




        @Excel(name = "联系电话")
        private String phone;

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

    public String getDepartment_code() {
        return department_code;
    }

    public void setDepartment_code(String department_code) {
        this.department_code = department_code;
    }

    public String getStaff_type() {
        return staff_type;
    }

    public void setStaff_type(String staff_type) {
        this.staff_type = staff_type;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getJob_code() {
        return job_code;
    }

    public void setJob_code(String job_code) {
        this.job_code = job_code;
    }

    public String getEdu_code() {
        return edu_code;
    }

    public void setEdu_code(String edu_code) {
        this.edu_code = edu_code;
    }

    public String getNation_code() {
        return nation_code;
    }

    public void setNation_code(String nation_code) {
        this.nation_code = nation_code;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getHire_date() {
        return hire_date;
    }

    public void setHire_date(String hire_date) {
        this.hire_date = hire_date;
    }

    public String getLeave_date() {
        return leave_date;
    }

    public void setLeave_date(String leave_date) {
        this.leave_date = leave_date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
