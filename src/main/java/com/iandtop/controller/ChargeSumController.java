package com.iandtop.controller;

import com.iandtop.model.form.ChargeSumModel;
import com.iandtop.service.ChargeSumService;
import com.iandtop.utils.APIRestResponse;
import com.iandtop.utils.DateUtils;
import com.iandtop.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by xss on 2017/5/23.
 */

@Controller
@RequestMapping("/chargeSum")
public class ChargeSumController {

    @Autowired
    private ChargeSumService chargeSumService;

    @ResponseBody
    @RequestMapping(value="/query")
    public APIRestResponse queryChargeSum(HttpServletRequest request,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin","*");
        String pk_user = request.getParameter("pk_user");
        String start_ts = request.getParameter("start_ts");
        String end_ts = request.getParameter("end_ts");
        String if_sum = request.getParameter("if_sum");
        String if_user_sum = request.getParameter("if_user_sum");

        List<ChargeSumModel> result = null;
        if(end_ts!=null && end_ts.length()>0){
            end_ts = DateUtils.getLaterDate(end_ts);
        }
        if("Y".equals(if_sum)){
            //汇总成一条数据
            result = chargeSumService.queryChargeSumOne(pk_user,start_ts,end_ts,if_sum);
        }else if("Y".equals(if_user_sum)){
             //操作员汇总
            result = chargeSumService.queryChargeSum(pk_user,start_ts,end_ts,if_user_sum);
        }else{
            //按日期汇总
            result = chargeSumService.queryChargeSum(pk_user,start_ts,end_ts);
        }

        if(result!=null && result.size()>0){
            return ResponseUtils.getSuccessAPI(true,"true", "",result);
        }else{
            return ResponseUtils.getSuccessAPI(false,"false", "",null);
        }
    }
    @ResponseBody
    @RequestMapping(value="/queryMealCharge")
    public APIRestResponse queryMeal(HttpServletRequest request,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin","*");
        String start_ts = request.getParameter("start_ts");
        String end_ts = request.getParameter("end_ts");
        if(start_ts==null || start_ts.length()<1){
            start_ts = DateUtils.formatDatetime(DateUtils.firstDayOfMonth());
        }
        if(end_ts==null || end_ts.length()<1){
            end_ts = DateUtils.currentDatetime();
        }
        List<ChargeSumModel> result = chargeSumService.queryMealCharge(start_ts,end_ts);
        if(result!=null && result.size()>0){
            return ResponseUtils.getSuccessAPI(true,"true", "",result);
        }else{
            return ResponseUtils.getSuccessAPI(false,"false", "",null);
        }
    }
    @ResponseBody
    @RequestMapping(value="/updrecordsum")
    public APIRestResponse updrecordsum(HttpServletRequest request,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin","*");
        String start_ts = request.getParameter("start_ts");
        String end_ts = request.getParameter("end_ts");
        if(start_ts==null || start_ts.length()<1){
            start_ts = DateUtils.formatDatetime(DateUtils.firstDayOfMonth());
        }
        if(end_ts==null || end_ts.length()<1){
            end_ts = DateUtils.currentDatetime();
        }
        int line = chargeSumService.updrecordsum(start_ts,end_ts);
        if(line>0){
            return ResponseUtils.getSuccessAPI(true,"同步成功", "");
        }else{
            return ResponseUtils.getSuccessAPI(false,"同步失败，请重试", "",null);
        }
    }

}








