package com.iandtop.service;

import com.github.pagehelper.PageInfo;
import com.iandtop.model.DeptModelVO;
import com.iandtop.model.StaffModel;
import com.iandtop.model.card.CardChangeRecordModel;
import com.iandtop.model.card.CardChargeRecordModel;
import com.iandtop.model.card.CardModel;
import com.iandtop.model.card.CardRefundRecordModel;
import com.iandtop.model.meal.MealRecordModel;
import com.iandtop.model.system.UserModel;

import java.text.ParseException;
import java.util.List;

/**
 * Created by xss on 2017-05-22.
 */
public interface DetailAllService {

    List<UserModel> queryUserAll();

    List<DeptModelVO> queryDeptAll();

    PageInfo<CardChargeRecordModel> queryChargeDetail(
            String staff_code, String staff_name, String user_name, String card_code, String charge_type, String start_ts, String end_ts, String dept_code, Integer pageNo, Integer pageSize);

    PageInfo<MealRecordModel> queryConsumeDetail(
            String staff_code, String staff_name, String card_code, String device_code, String dept_code, String start_ts, String end_ts, Integer pageNo, Integer pageSize) throws ParseException;

    List<CardRefundRecordModel> queryRefundDetail(
            String staff_code, String staff_name, String user_name, String card_code, String charge_type, String start_ts, String end_ts, String dept_code);

    List<CardChangeRecordModel> queryReturnCardDetail(
            String staff_code, String staff_name, String user_name, String card_code, String charge_type, String start_ts, String end_ts, String dept_code);

    List<CardChangeRecordModel> queryPatchCardDetail(
            String staff_code, String staff_name, String user_name, String card_code, String charge_type, String start_ts, String end_ts, String dept_code);

    PageInfo<CardModel> queryPsnBalance(String staff_code, String staff_name, String card_code, String dept_code, Integer pageNo, Integer pageSize);

    List<String> gainTableName(String start_ts, String end_ts);
}
