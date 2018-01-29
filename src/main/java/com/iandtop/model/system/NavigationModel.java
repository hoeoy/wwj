package com.iandtop.model.system;

import com.iandtop.model.pub.SuperModel;

/**
 * Created by lz on 2017/5/18.
 */
public class NavigationModel extends SuperModel {

    private String pk_navigation;
    private String pk_father_navigation;
    private String navigation_code;
    private String navigation_name;
    private String navigation_path;
    private String icon_path;

    public String getPk_navigation() {
        return pk_navigation;
    }

    public void setPk_navigation(String pk_navigation) {
        this.pk_navigation = pk_navigation;
    }

    public String getPk_father_navigation() {
        return pk_father_navigation;
    }

    public void setPk_father_navigation(String pk_father_navigation) {
        this.pk_father_navigation = pk_father_navigation;
    }

    public String getNavigation_code() {
        return navigation_code;
    }

    public void setNavigation_code(String navigation_code) {
        this.navigation_code = navigation_code;
    }

    public String getNavigation_name() {
        return navigation_name;
    }

    public void setNavigation_name(String navigation_name) {
        this.navigation_name = navigation_name;
    }

    public String getNavigation_path() {
        return navigation_path;
    }

    public void setNavigation_path(String navigation_path) {
        this.navigation_path = navigation_path;
    }

    public String getIcon_path() {
        return icon_path;
    }

    public void setIcon_path(String icon_path) {
        this.icon_path = icon_path;
    }

    @Override
    public String getTableName() {
        return "sm_navigation";
    }
}
