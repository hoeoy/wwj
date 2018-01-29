package com.iandtop.dao;

import com.iandtop.model.DeptModelVO;
import com.iandtop.model.ZbqModel;
import com.iandtop.model.card.CardChangeRecordModel;
import com.iandtop.model.card.CardChargeRecordModel;
import com.iandtop.model.card.CardRefundRecordModel;
import com.iandtop.model.system.UserModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xss on 2017-05-22.
 */
public interface DetailAllDAO {

    List<UserModel> queryUserAll();

    List<DeptModelVO> queryDeptAll();

    List<CardChargeRecordModel> queryChargeDetail(
            @Param("staff_code") String staff_code, @Param("staff_name") String staff_name, @Param("user_name") String user_name, @Param("card_code") String card_code,
            @Param("charge_type") String charge_type, @Param("start_ts") String start_ts, @Param("end_ts") String end_ts, @Param("dept_code") String dept_code);

    List<CardRefundRecordModel> queryRefundDetail(
            @Param("staff_code") String staff_code, @Param("staff_name") String staff_name, @Param("user_name") String user_name, @Param("card_code") String card_code,
            @Param("charge_type") String charge_type, @Param("start_ts") String start_ts, @Param("end_ts") String end_ts, @Param("dept_code") String dept_code);

    List<CardChangeRecordModel> queryReturnCardDetail(
            @Param("staff_code") String staff_code, @Param("staff_name") String staff_name, @Param("user_name") String user_name, @Param("card_code") String card_code,
            @Param("charge_type") String charge_type, @Param("start_ts") String start_ts, @Param("end_ts") String end_ts, @Param("dept_code") String dept_code);

    List<CardChangeRecordModel> queryPatchCardDetail(
            @Param("staff_code") String staff_code, @Param("staff_name") String staff_name, @Param("user_name") String user_name, @Param("card_code") String card_code,
            @Param("charge_type") String charge_type, @Param("start_ts") String start_ts, @Param("end_ts") String end_ts, @Param("dept_code") String dept_code);

    List<ZbqModel> queryOrderDetail(
            @Param("stypeid") String stypeid,@Param("styleid") String styleid,@Param("typename") String typename, @Param("foodname") String foodname, @Param("start_ts") String start_ts, @Param("end_ts") String end_ts);
}
