package com.iandtop.controller;

import com.github.pagehelper.PageInfo;
import com.iandtop.model.DeptModelVO;
import com.iandtop.model.ZbqModel;
import com.iandtop.model.card.CardChangeRecordModel;
import com.iandtop.model.card.CardChargeRecordModel;
import com.iandtop.model.card.CardModel;
import com.iandtop.model.card.CardRefundRecordModel;
import com.iandtop.model.meal.MealRecordModel;
import com.iandtop.model.system.UserModel;
import com.iandtop.service.DetailAllService;
import com.iandtop.service.ZbqService;
import com.iandtop.utils.APIRestResponse;
import com.iandtop.utils.DateUtils;
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
 * Created by xss on 2017/5/22.
 */

@Controller
@RequestMapping("/detailAll")
public class DetailAllController {

    @Autowired
    private DetailAllService detailAllService;

    @Autowired
    private ZbqService zbqService;

    @ResponseBody
    @RequestMapping(value="/queryUserAll")
    public APIRestResponse queryUserAll( HttpServletRequest request,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin","*");
        List<UserModel> result = detailAllService.queryUserAll();
        if(result!=null && result.size()>0){
            return ResponseUtils.getSuccessAPI(true,"true", "",result);
        }else{
            return ResponseUtils.getFailed("未找到要查询的数据。","false", "",null);
        }
    }

    @ResponseBody
    @RequestMapping(value="/queryDeptAll")
    public APIRestResponse queryDeptAll(HttpServletRequest request,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin","*");
        List<DeptModelVO> result = detailAllService.queryDeptAll();
        if(result!=null && result.size()>0){
            return ResponseUtils.getSuccessAPI(true,"true", "",result);
        }else{
            return ResponseUtils.getFailed("未找到要查询的数据。","false", "",null);
        }
    }

    @ResponseBody
    @RequestMapping("/queryCharge")
    public APIRestResponse queryChargeDetail(HttpServletRequest request,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin","*");
        String staff_code = request.getParameter("staff_code");
        String staff_name = request.getParameter("staff_name");
        String user_name = request.getParameter("user_name");
        String card_code = request.getParameter("card_code");
        String charge_type = request.getParameter("charge_type");
        String start_ts = request.getParameter("start_ts");
        String end_ts = request.getParameter("end_ts");
        String dept_code = request.getParameter("dept_code");
        String pageNo1 = request.getParameter("pageNo");
        if(pageNo1==null || pageNo1.length()<1){
            pageNo1 ="1";
        }
        Integer pageNo = Integer.valueOf(pageNo1);
        String pageSize1 = request.getParameter("pageSize");
        if(pageSize1==null || pageSize1.length()<1){
            pageSize1 ="20";
        }
        Integer pageSize = Integer.valueOf(pageSize1);

        PageInfo<CardChargeRecordModel> result = detailAllService.queryChargeDetail(staff_code, staff_name, user_name, card_code, charge_type, start_ts, end_ts,dept_code,pageNo,pageSize);
        if(result.getSize() > 0){
            return ResponseUtils.getSuccessAPI(true, "查询成功", RestOperateCode.GET_DATA, result);
        }else{
            return ResponseUtils.getFailed("", "查询失败", RestOperateCode.GET_DATA);
        }

    }

    @ResponseBody
    @RequestMapping("/queryConsume")
    public APIRestResponse queryConsume(HttpServletRequest request,HttpServletResponse response) throws ParseException {
        response.setHeader("Access-Control-Allow-Origin","*");
        String staff_code = request.getParameter("staff_code");
        String staff_name = request.getParameter("staff_name");
        String card_code = request.getParameter("card_code");
        String device_code = request.getParameter("device_code");
        String dept_code = request.getParameter("dept_code");
        String start_ts = request.getParameter("start_ts");
        String end_ts = request.getParameter("end_ts");
        if(start_ts==null || start_ts.length()<1){
            start_ts = DateUtils.formatDatetime(DateUtils.firstDayOfMonth());
        }
        if(end_ts==null || end_ts.length()<1){
            end_ts = DateUtils.currentDatetime();
        }
        //Integer pageNo, Integer pageSize
        String pageNo1 = request.getParameter("pageNo");
        if(pageNo1==null || pageNo1.length()<1){
            pageNo1 ="1";
        }
        Integer pageNo = Integer.valueOf(pageNo1);
        String pageSize1 = request.getParameter("pageSize");
        if(pageSize1==null || pageSize1.length()<1){
            pageSize1 ="20";
        }
        Integer pageSize = Integer.valueOf(pageSize1);

        PageInfo<MealRecordModel> result = detailAllService.queryConsumeDetail(staff_code,staff_name,card_code,device_code,dept_code,start_ts,end_ts,pageNo,pageSize);
        if(result.getSize() > 0){
            return ResponseUtils.getSuccessAPI(true, "查询成功", RestOperateCode.GET_DATA, result);
        }else{
            return ResponseUtils.getFailed("", "查询失败", RestOperateCode.GET_DATA);
        }

    }

    @ResponseBody
    @RequestMapping("/queryRefund")
    public APIRestResponse queryRefund(HttpServletRequest request,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin","*");
        String staff_code = request.getParameter("staff_code");
        String staff_name = request.getParameter("staff_name");
        String user_name = request.getParameter("user_name");
        String card_code = request.getParameter("card_code");
        String charge_type = request.getParameter("charge_type");
        String start_ts = request.getParameter("start_ts");
        String end_ts = request.getParameter("end_ts");
        String dept_code = request.getParameter("dept_code");

        List<CardRefundRecordModel> result = detailAllService.queryRefundDetail(staff_code, staff_name, user_name, card_code, charge_type, start_ts, end_ts,dept_code);
        if(result!=null && result.size()>0){
            return ResponseUtils.getSuccessAPI(true,"true", "",result);
        }else{
            return ResponseUtils.getFailed("未找到要查询的数据。","false", "",null);
        }

    }

    @ResponseBody
    @RequestMapping("/queryReturnCard")
    public APIRestResponse queryReturnCard(HttpServletRequest request,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin","*");
        String staff_code = request.getParameter("staff_code");
        String staff_name = request.getParameter("staff_name");
        String user_name = request.getParameter("user_name");
        String card_code = request.getParameter("card_code");
        String charge_type = request.getParameter("charge_type");
        String start_ts = request.getParameter("start_ts");
        String end_ts = request.getParameter("end_ts");
        String dept_code = request.getParameter("dept_code");

        List<CardChangeRecordModel> result = detailAllService.queryReturnCardDetail(staff_code, staff_name, user_name, card_code, charge_type, start_ts, end_ts,dept_code);
        if(result!=null && result.size()>0){
            return ResponseUtils.getSuccessAPI(true,"true", "",result);
        }else{
            return ResponseUtils.getFailed("未找到要查询的数据。","false", "",null);
        }
    }

    @ResponseBody
    @RequestMapping("/queryPatchCard")
    public APIRestResponse queryPatchCard(HttpServletRequest request,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin","*");
        String staff_code = request.getParameter("staff_code");
        String staff_name = request.getParameter("staff_name");
        String user_name = request.getParameter("user_name");
        String card_code = request.getParameter("card_code");
        String charge_type = request.getParameter("charge_type");
        String start_ts = request.getParameter("start_ts");
        String end_ts = request.getParameter("end_ts");
        String dept_code = request.getParameter("dept_code");

        List<CardChangeRecordModel> result = detailAllService.queryPatchCardDetail(staff_code, staff_name, user_name, card_code, charge_type, start_ts, end_ts,dept_code);
        if(result!=null && result.size()>0){
            return ResponseUtils.getSuccessAPI(true,"true", "",result);
        }else{
            return ResponseUtils.getFailed("未找到要查询的数据。","false", "",null);
        }
    }

    @ResponseBody
    @RequestMapping("/queryPsnBalance")
    public APIRestResponse queryPsnBalance(HttpServletRequest request,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin","*");
        String staff_code = request.getParameter("staff_code");
        String staff_name = request.getParameter("staff_name");
        String card_code = request.getParameter("card_code");
        String dept_code = request.getParameter("dept_code");
        String pageNo1 = request.getParameter("pageNo");
        if(pageNo1==null || pageNo1.length()<1){
            pageNo1 ="1";
        }
        Integer pageNo = Integer.valueOf(pageNo1);
        String pageSize1 = request.getParameter("pageSize");
        if(pageSize1==null || pageSize1.length()<1){
            pageSize1 ="20";
        }
        Integer pageSize = Integer.valueOf(pageSize1);
        PageInfo<CardModel> result = detailAllService.queryPsnBalance(staff_code, staff_name, card_code,dept_code,pageNo,pageSize);
        if(result.getSize() > 0){
            return ResponseUtils.getSuccessAPI(true, "查询成功", RestOperateCode.GET_DATA, result);
        }else{
            return ResponseUtils.getFailed("", "查询失败", RestOperateCode.GET_DATA);
        }
    }

    @ResponseBody
    @RequestMapping("/queryOrder")
    public APIRestResponse queryOrderDetail(HttpServletRequest request,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin","*");
        String stypeid = request.getParameter("stypeid");
        String styleid = request.getParameter("styleid");
        String typename = request.getParameter("typename");
        String foodname = request.getParameter("foodname");
        String start_ts = request.getParameter("start_ts");
        String end_ts = request.getParameter("end_ts");
        String pageNo1 = request.getParameter("pageNo");
        if(pageNo1==null || pageNo1.length()<1){
            pageNo1 ="1";
        }
        Integer pageNo = Integer.valueOf(pageNo1);
        String pageSize1 = request.getParameter("pageSize");
        if(pageSize1==null || pageSize1.length()<1){
            pageSize1 ="20";
        }
        Integer pageSize = Integer.valueOf(pageSize1);

        PageInfo<ZbqModel> result = zbqService.queryOrderDetail(stypeid,styleid, typename, foodname,start_ts, end_ts,pageNo,pageSize);
        if(result.getSize() > 0){
            return ResponseUtils.getSuccessAPI(true, "查询成功", RestOperateCode.GET_DATA, result);
        }else{
            return ResponseUtils.getFailed("", "查询失败", RestOperateCode.GET_DATA);
        }
    }
}
