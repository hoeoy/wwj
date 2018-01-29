package com.iandtop.model;

import com.iandtop.model.pub.SuperModel;

/**
 * Created by lz on 2017/5/12.
 */
public class DeptModelVO extends SuperModel {

    private String pk_department;
    private String department_code;
    private String department_name;
    private String company_code;
    private String parent_code;
    private Integer type;
    private String haschild;

    public String getPk_department() {
        return pk_department;
    }

    public void setPk_department(String pk_department) {
        this.pk_department = pk_department;
    }

    public String getDepartment_code() {
        return department_code;
    }

    public void setDepartment_code(String department_code) {
        this.department_code = department_code;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getCompany_code() {
        return company_code;
    }

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }

    public String getParent_code() {
        return parent_code;
    }

    public void setParent_code(String parent_code) {
        this.parent_code = parent_code;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getHaschild() {
        return haschild;
    }

    public void setHaschild(String haschild) {
        this.haschild = haschild;
    }

    @Override
    public String getTableName() {
        return "db_department";
    }
}
