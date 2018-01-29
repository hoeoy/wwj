package com.iandtop.model;

import com.iandtop.model.pub.SuperModel;

/**
 * @author LY
 * @description 员工实体
 * @create 2017/5/12
 */

public class StaffModel extends SuperModel {

    public static final String Male = "1";
    public static final String FaMale = "0";

    public static final String Staff_Type_OnJob = "1";
    public static final String Staff_Type_LeaveJob = "0";

    /**
     * 唯一ID
     */
    private String pk_staff;
    /**
     * 编码
     */
    private String staff_code;
    /**
     * 名称
     */
    private String staff_name;
    /**
     * 关联企业
     */
    private String company_code;
    /**
     * 关联部门
     */
    private String department_code;
    /**
     * 员工状态
     */
    private String staff_type;
    /**
     * 性别
     */
    private String sex;
    /**
     * 身份证
     */
    private String id_card;

    /**
     * 关联职务
     */
    private String job_code;
    /**
     * 关联学历
     */
    private String edu_code;

    /**
     * 关联民族
     */
    private String nation_code;
    /**
     * 生日
     */
    private String birth_date;

    /**
     * 入职日期
     */
    private String hire_date;

    /**
     * 离职日期
     */
    private String leave_date;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 工号
     */
    private String company_card;
    /**
     * 修改时间
     */
    private String update_date;
    /**
     * 联系电话
     */
    private String phone;

    /**
     * 外文局id
     */
    private String wwjid;

    /**
     *商户id
     */
    private String pk_merchant;

    //冗余字段
    private String department_name;

    public String getPk_merchant() {
        return pk_merchant;
    }

    public void setPk_merchant(String pk_merchant) {
        this.pk_merchant = pk_merchant;
    }

    public String getWwjid() {
        return wwjid;
    }

    public void setWwjid(String wwjid) {
        this.wwjid = wwjid;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPk_staff() {
        return pk_staff;
    }

    public void setPk_staff(String pk_staff) {
        this.pk_staff = pk_staff;
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

    public String getCompany_code() {
        return company_code;
    }

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
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

    public String getCompany_card() {
        return company_card;
    }

    public void setCompany_card(String company_card) {
        this.company_card = company_card;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    @Override
    public String getTableName() {
        return "db_staff";
    }
}
