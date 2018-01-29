package com.iandtop.model.system;

import com.iandtop.model.pub.SuperModel;

/**
 * Created by lz on 2017/5/18.
 */
public class JurisdictionModel extends SuperModel {

    private String pk_jurisdiction;
    private String pk_navigation;
    private String pk_role;

    public String getPk_jurisdiction() {
        return pk_jurisdiction;
    }

    public void setPk_jurisdiction(String pk_jurisdiction) {
        this.pk_jurisdiction = pk_jurisdiction;
    }

    public String getPk_navigation() {
        return pk_navigation;
    }

    public void setPk_navigation(String pk_navigation) {
        this.pk_navigation = pk_navigation;
    }

    public String getPk_role() {
        return pk_role;
    }

    public void setPk_role(String pk_role) {
        this.pk_role = pk_role;
    }

    @Override
    public String getTableName() {
        return "sm_jurisdiction";
    }
}
