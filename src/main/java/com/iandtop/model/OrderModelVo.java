package com.iandtop.model;

/**
 * Created by Administrator on 2017/5/12.
 */
public class OrderModelVo extends OrderModel{

    private Integer pk_only ; 	        //类型id
    private Integer pk_parentid;       //父类id
    private String  typename  ;   // 小类名称

    public Integer getPk_only() {
        return pk_only;
    }

    public void setPk_only(Integer pk_only) {
        this.pk_only = pk_only;
    }

    public Integer getPk_parentid() {
        return pk_parentid;
    }

    public void setPk_parentid(Integer pk_parentid) {
        this.pk_parentid = pk_parentid;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }
}
