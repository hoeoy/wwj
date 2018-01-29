package com.iandtop.model.card;

import com.iandtop.model.pub.SuperModel;

/**
 * Created by lz on 2017/5/13.
 */
public class CardLostRecordModel extends SuperModel {

    public static final String Type_Lost = "lost";
    public static final String Type_UnLost = "unlost";

    private String pk_lost_record;
    private String pk_card;
    private String pk_staff;
    private String card_code;
    private String staff_code;
    private String type;
    private String operator;
    private String lost_ts;

    public String getPk_lost_record() {
        return pk_lost_record;
    }

    public void setPk_lost_record(String pk_lost_record) {
        this.pk_lost_record = pk_lost_record;
    }

    public String getPk_card() {
        return pk_card;
    }

    public void setPk_card(String pk_card) {
        this.pk_card = pk_card;
    }

    public String getPk_staff() {
        return pk_staff;
    }

    public void setPk_staff(String pk_staff) {
        this.pk_staff = pk_staff;
    }

    public String getCard_code() {
        return card_code;
    }

    public void setCard_code(String card_code) {
        this.card_code = card_code;
    }

    public String getStaff_code() {
        return staff_code;
    }

    public void setStaff_code(String staff_code) {
        this.staff_code = staff_code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getLost_ts() {
        return lost_ts;
    }

    public void setLost_ts(String lost_ts) {
        this.lost_ts = lost_ts;
    }

    @Override
    public String getTableName() {
        return "card_lost_record";
    }
}
