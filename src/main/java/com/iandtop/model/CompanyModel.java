package com.iandtop.model;

import com.iandtop.model.pub.SuperModel;

/**
 * @author LZ
 * @description 企业实体
 * @create 2017/5/12
 */

public class CompanyModel extends SuperModel {

    private Long pk_company;
    private String company_code;
    private String company_name;
    private Integer company_type;
    private String person_in_charge;
    private String address;
    private String telephone;

    public Long getPk_company() {
        return pk_company;
    }

    public void setPk_company(Long pk_company) {
        this.pk_company = pk_company;
    }

    public String getCompany_code() {
        return company_code;
    }

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public Integer getCompany_type() {
        return company_type;
    }

    public void setCompany_type(Integer company_type) {
        this.company_type = company_type;
    }

    public String getPerson_in_charge() {
        return person_in_charge;
    }

    public void setPerson_in_charge(String person_in_charge) {
        this.person_in_charge = person_in_charge;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String getTableName() {
        return "db_company";
    }
}
