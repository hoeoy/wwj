package com.iandtop.model.system;

import com.iandtop.model.pub.SuperModel;

/**
 * Created by lz on 2017/5/18.
 */
public class RoleModel extends SuperModel {

    public static final String Super_Admin = "admin";

    private String pk_role;
    private String role_code;
    private String role_name;

    public String getPk_role() {
        return pk_role;
    }

    public void setPk_role(String pk_role) {
        this.pk_role = pk_role;
    }

    public String getRole_code() {
        return role_code;
    }

    public void setRole_code(String role_code) {
        this.role_code = role_code;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    @Override
    public String getTableName() {
        return "sm_role";
    }
}
