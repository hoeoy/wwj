package com.iandtop.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */
public class OrderStyleModel {


    private int id;
    private String stylename;
    private List<OrderModelVo> orderModelVos = new LinkedList<OrderModelVo>();//所有小类的集合

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStylename() {
        return stylename;
    }

    public void setStylename(String stylename) {
        this.stylename = stylename;
    }

    public List<OrderModelVo> getOrderModelVos() {
        return orderModelVos;
    }

    public void setOrderModelVos(List<OrderModelVo> orderModelVos) {
        this.orderModelVos = orderModelVos;
    }
}
