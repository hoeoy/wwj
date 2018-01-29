package com.iandtop.controller;

import com.iandtop.model.device.DeviceModel;
import com.iandtop.model.form.MerchantPOSMondel;
import com.iandtop.model.form.MerchantPos;
import com.iandtop.model.meal.MerchantModel;
import com.iandtop.service.TenantPosService;
import com.iandtop.utils.APIRestResponse;
import com.iandtop.utils.DateUtils;
import com.iandtop.utils.ReqUtils;
import com.iandtop.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by xss on 2017-05-24.
 */
@Controller
@RequestMapping("/tenantPos")
public class TenantPosController {

    @Autowired
    private TenantPosService tenantPosService;

    @ResponseBody
    @RequestMapping(value="/queryMerchant")
    public APIRestResponse queryMerchant(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin","*");
        List<MerchantModel> result = tenantPosService.queryMerchant();
        if(result!=null && result.size()>0){
            return ResponseUtils.getSuccessAPI(true,"true", "",result);
        }else{
            return ResponseUtils.getFailed("未找到要查询的数据。","false", "",null);
        }
    }

    @ResponseBody
    @RequestMapping(value="/queryDevice")
    public APIRestResponse queryDevice(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin","*");
        String pk_merchant = request.getParameter("pk_merchant");
        List<DeviceModel> result = tenantPosService.queryDevice(pk_merchant);
        if(result!=null && result.size()>0){
            return ResponseUtils.getSuccessAPI(true,"true", "",result);
        }else{
            return ResponseUtils.getFailed("未找到要查询的数据。","false", "",null);
        }
    }

    @ResponseBody
    @RequestMapping(value="/queryMerchantDay")
    public APIRestResponse queryMerchantDay(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin","*");
        String pk_device = request.getParameter("pk_device");
        String start_ts = request.getParameter("start_ts");
        String end_ts = request.getParameter("end_ts");
        String pk_merchant = request.getParameter("pk_merchant");

        if(start_ts==null || start_ts.length()<1){
            start_ts = DateUtils.formatDatetime(DateUtils.firstDayOfMonth());
        }
        if(end_ts==null || end_ts.length()<1){
            end_ts = DateUtils.currentDatetime();
        }
        List<MerchantPOSMondel> result = tenantPosService.queryMerchantDay(pk_merchant,pk_device,start_ts,end_ts);
        if(result!=null && result.size()>0){
            return ResponseUtils.getSuccessAPI(true,"true", "",result);
        }else{
            return ResponseUtils.getSuccessAPI(false,"false", "",null);
        }
    }

    @ResponseBody
    @RequestMapping(value="/queryMerchantSum")
    public APIRestResponse queryMerchantSum(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin","*");
        String pk_device = request.getParameter("pk_device");
        String start_ts = request.getParameter("start_ts");
        String end_ts = request.getParameter("end_ts");
        String pk_merchant = request.getParameter("pk_merchant");
        if(start_ts==null || start_ts.length()<1){
            start_ts = DateUtils.formatDatetime(DateUtils.firstDayOfMonth());
        }
        if(end_ts==null || end_ts.length()<1){
            end_ts = DateUtils.currentDatetime();
        }
        List<MerchantPOSMondel> result = tenantPosService.queryMerchantSum(pk_merchant,pk_device,start_ts,end_ts);
        if(result!=null && result.size()>0){
            return ResponseUtils.getSuccessAPI(true,"true", "",result);
        }else{
            return ResponseUtils.getSuccessAPI(false,"false", "",null);
        }
    }


    @ResponseBody
    @RequestMapping(value="/findSumbyMerchant")
    public APIRestResponse findSumbyMerchant(HttpServletRequest request, HttpServletResponse response) {
        try{
            response.setHeader("Access-Control-Allow-Origin","*");

            String strType = request.getParameter("type");
            String start_ts = request.getParameter("start_ts");
            String end_ts = request.getParameter("end_ts");
            String strPkMerchant = ReqUtils.getString(request,"pk_merchant");
            Long pk_merchant = new Long(strPkMerchant);

            if(start_ts==null || start_ts.length()<1){
                start_ts = DateUtils.formatDatetime(DateUtils.firstDayOfMonth());
            }
            if(end_ts==null || end_ts.length()<1){
                end_ts = DateUtils.currentDatetime();
            }
            if(strType == null || pk_merchant == null || strType.equals("")){
                return ResponseUtils.getSuccessAPI(false,"false", "数据格式不正确",null);
            }
            if(strType.equals("1")){// 普通消费
                List<MerchantPos> result = tenantPosService.findSumbyMerchant(pk_merchant,start_ts,end_ts);
                if(result!=null && result.size()>0){
                    return ResponseUtils.getSuccessAPI(true,"true", "",result);
                }else{
                    return ResponseUtils.getSuccessAPI(false,"false", "没有数据",null);
                }
            } else if(strType.equals("2")) {// 通用消费
                List<MerchantPos> result = tenantPosService.findSumbyMerchantExtend(pk_merchant,start_ts,end_ts);
                if(result!=null && result.size()>0){
                    return ResponseUtils.getSuccessAPI(true,"true", "",result);
                }else{
                    return ResponseUtils.getSuccessAPI(false,"false", "没有数据",null);
                }
            }else {
                return ResponseUtils.getSuccessAPI(false,"false", "不支持此类型",null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtils.getSuccessAPI(false,"false", "出错啦",null);
        }
    }




}
