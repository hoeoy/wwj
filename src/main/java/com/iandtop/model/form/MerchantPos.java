package com.iandtop.model.form;

import com.iandtop.model.pub.SuperModel;

public class MerchantPos extends SuperModel {

    private Long pkMerchant;

    private String merchantCode;

    private String merchantName;

    private Long cofferAmount;

    private Double cofferPrice;

    private Long marketAmout;

    private Double marketPrice;

    private Long breakfastAmount;

    private Double breakfastPrice;

    private Long lunchAmount;

    private Double lunchPrice;

    private Long oderAmount;

    private Double oderPrice;

    private Long dinnerAmount;

    private Double dinnerPrice;

    private Long totalAmount;

    private Double totalPrice;

    public Long getPkMerchant() {
        return pkMerchant;
    }

    public void setPkMerchant(Long pkMerchant) {
        this.pkMerchant = pkMerchant;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode == null ? null : merchantCode.trim();
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
    }

    public Long getCofferAmount() {
        return cofferAmount;
    }

    public void setCofferAmount(Long cofferAmount) {
        this.cofferAmount = cofferAmount;
    }

    public Double getCofferPrice() {
        return cofferPrice;
    }

    public void setCofferPrice(Double cofferPrice) {
        this.cofferPrice = cofferPrice;
    }

    public Long getMarketAmout() {
        return marketAmout;
    }

    public void setMarketAmout(Long marketAmout) {
        this.marketAmout = marketAmout;
    }

    public Double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Long getBreakfastAmount() {
        return breakfastAmount;
    }

    public void setBreakfastAmount(Long breakfastAmount) {
        this.breakfastAmount = breakfastAmount;
    }

    public Double getBreakfastPrice() {
        return breakfastPrice;
    }

    public void setBreakfastPrice(Double breakfastPrice) {
        this.breakfastPrice = breakfastPrice;
    }

    public Long getLunchAmount() {
        return lunchAmount;
    }

    public void setLunchAmount(Long lunchAmount) {
        this.lunchAmount = lunchAmount;
    }

    public Double getLunchPrice() {
        return lunchPrice;
    }

    public void setLunchPrice(Double lunchPrice) {
        this.lunchPrice = lunchPrice;
    }

    public Long getOderAmount() {
        return oderAmount;
    }

    public void setOderAmount(Long oderAmount) {
        this.oderAmount = oderAmount;
    }

    public Double getOderPrice() {
        return oderPrice;
    }

    public void setOderPrice(Double oderPrice) {
        this.oderPrice = oderPrice;
    }

    public Long getDinnerAmount() {
        return dinnerAmount;
    }

    public void setDinnerAmount(Long dinnerAmount) {
        this.dinnerAmount = dinnerAmount;
    }

    public Double getDinnerPrice() {
        return dinnerPrice;
    }

    public void setDinnerPrice(Double dinnerPrice) {
        this.dinnerPrice = dinnerPrice;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String getTableName() {
        return null;
    }
}