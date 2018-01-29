package com.iandtop.model;

/**
 * Created by Administrator on 2017/5/12.
 */
public class OrderModel {
    private Integer      id;
    private Integer      goodstype;    //商品小类
    private String       goodsname ;   //商品名称
    private String       norm;		  //规格
    private String       unit;	      //单位
    private Double      price ;       //价格
    private Integer      state ;       //状态
    private Integer      barcode ;     //条码
    private String       addedtime ;   // 上架时间
    private String       image  ;      //图片
    private Integer     styleid;   //商品大类id
    private String typename;

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStyleid() {
        return styleid;
    }

    public void setStyleid(Integer styleid) {
        this.styleid = styleid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodstype() {
        return goodstype;
    }

    public void setGoodstype(Integer goodstype) {
        this.goodstype = goodstype;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getNorm() {
        return norm;
    }

    public void setNorm(String norm) {
        this.norm = norm;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }



    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getBarcode() {
        return barcode;
    }

    public void setBarcode(Integer barcode) {
        this.barcode = barcode;
    }

    public String getAddedtime() {
        return addedtime;
    }

    public void setAddedtime(String addedtime) {
        this.addedtime = addedtime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}