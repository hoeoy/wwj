package com.iandtop.model.system;

import com.iandtop.model.pub.SuperModel;

/**
 * Created by lz on 2017/5/18.
 */
public class UserModel extends SuperModel {

    private String pk_user;
    private String pk_role;
    private String pk_staff;
    private String user_code;
    private String user_name;
    private String password;
    private String oldpwd;
    private String pk_device;
    //冗余字段
    private String role_name;





    public String getPk_device() {
        return pk_device;
    }

    public void setPk_device(String pk_device) {
        this.pk_device = pk_device;
    }

    public String getOldpwd() {
        return oldpwd;
    }

    public void setOldpwd(String oldpwd) {
        this.oldpwd = oldpwd;
    }

    public String getPk_staff() {
        return pk_staff;
    }

    public void setPk_staff(String pk_staff) {
        this.pk_staff = pk_staff;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getPk_user() {
        return pk_user;
    }

    public void setPk_user(String pk_user) {
        this.pk_user = pk_user;
    }

    public String getPk_role() {
        return pk_role;
    }

    public void setPk_role(String pk_role) {
        this.pk_role = pk_role;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getTableName() {
        return "sm_user";
    }
}
