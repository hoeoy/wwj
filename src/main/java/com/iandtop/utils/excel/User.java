package com.iandtop.utils.excel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lz on 2017/4/12.
 */
public class User {

    public User(){

    }

    @Excel(name = "姓名", width = 30)
    private String name;

    @Excel(name = "年龄", width = 60)
    private String age;

    @Excel(skip = true)
    private String password;

    @Excel(name = "xx")
    private Double xx;

    @Excel(name = "yy")
    private Date yy;

    @Excel(name = "锁定")
    private Boolean locked;

    @Excel(name = "金额")
    private BigDecimal db;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getXx() {
        return xx;
    }

    public void setXx(Double xx) {
        this.xx = xx;
    }

    public Date getYy() {
        return yy;
    }

    public void setYy(Date yy) {
        this.yy = yy;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public BigDecimal getDb() {
        return db;
    }

    public void setDb(BigDecimal db) {
        this.db = db;
    }
}
