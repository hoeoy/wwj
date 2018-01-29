package com.iandtop.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.iandtop.model.OrderModel;
import com.iandtop.model.card.CardParamModel;
import com.iandtop.model.form.ChargeForSModel;
import com.iandtop.service.ChargeForSService;
import com.iandtop.utils.APIRestResponse;
import com.iandtop.utils.ResponseUtils;
import com.iandtop.utils.RestOperateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Seven on 2017/9/27.
 */
@Controller
@RequestMapping("/ChargeForS")
public class ChargeForSController {
    @Autowired
    private ChargeForSService service;

    @ResponseBody
    @RequestMapping("/ChargeRecord")
    public APIRestResponse ChargeRecord(ChargeForSModel model, Integer pageNo,Integer pageSize, HttpServletResponse response)throws ParseException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        PageInfo<ChargeForSModel>resultList =  service.ChargeRecordByPage(model,pageNo,pageSize);
        if (resultList.getSize() > 0)
            return ResponseUtils.getSuccess(true, "查询成功", RestOperateCode.GET_DATA, resultList);
        else
            return ResponseUtils.getFailed("", "查询失败", RestOperateCode.GET_DATA);
    }

    @ResponseBody
    @RequestMapping("/ChargeStaff")
    public APIRestResponse ChargeStaff(ChargeForSModel model, Integer pageNo,Integer pageSize, HttpServletResponse response)throws ParseException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        PageInfo<ChargeForSModel>resultList =  service.ChargeStaffByPage(model,pageNo,pageSize);
        if (resultList.getSize() > 0)
            return ResponseUtils.getSuccess(true, "查询成功", RestOperateCode.GET_DATA, resultList);
        else
            return ResponseUtils.getFailed("", "查询失败", RestOperateCode.GET_DATA);
    }

    @ResponseBody
    @RequestMapping("/ChargeDepartment")
    public APIRestResponse ChargeDepartment(ChargeForSModel model, Integer pageNo,Integer pageSize, HttpServletResponse response)throws ParseException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        PageInfo<ChargeForSModel>resultList =  service.ChargeDepartment(model,pageNo,pageSize);
        if (resultList.getSize() > 0)
            return ResponseUtils.getSuccess(true, "查询成功", RestOperateCode.GET_DATA, resultList);
        else
            return ResponseUtils.getFailed("", "查询失败", RestOperateCode.GET_DATA);
    }

    @ResponseBody
    @RequestMapping("/ChargeMerchant")
    public APIRestResponse ChargeMerchant(ChargeForSModel model, Integer pageNo,Integer pageSize, HttpServletResponse response)throws ParseException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        PageInfo<ChargeForSModel>resultList =  service.ChargeMerchant(model,pageNo,pageSize);
        if (resultList.getSize() > 0)
            return ResponseUtils.getSuccess(true, "查询成功", RestOperateCode.GET_DATA, resultList);
        else
            return ResponseUtils.getFailed("", "查询失败", RestOperateCode.GET_DATA);
    }
}
