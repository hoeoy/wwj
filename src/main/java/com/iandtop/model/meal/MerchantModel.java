package com.iandtop.model.meal;

import com.iandtop.model.pub.SuperModel;

/**
 * Created by lz on 2017/5/19.
 */
public class MerchantModel extends SuperModel {

    private String pk_merchant;
    private String merchant_code;
    private String merchant_name;
    private String merchant_phone;
    private String merchant_psn;

    public String getPk_merchant() {
        return pk_merchant;
    }

    public void setPk_merchant(String pk_merchant) {
        this.pk_merchant = pk_merchant;
    }

    public String getMerchant_code() {
        return merchant_code;
    }

    public void setMerchant_code(String merchant_code) {
        this.merchant_code = merchant_code;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getMerchant_phone() {
        return merchant_phone;
    }

    public void setMerchant_phone(String merchant_phone) {
        this.merchant_phone = merchant_phone;
    }

    public String getMerchant_psn() {
        return merchant_psn;
    }

    public void setMerchant_psn(String merchant_psn) {
        this.merchant_psn = merchant_psn;
    }

    @Override
    public String getTableName() {
        return "meal_merchant";
    }
}
