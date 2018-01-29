package com.iandtop.controller;

import com.iandtop.model.meal.MealRecordModel;
import com.iandtop.service.MealrecordUpService;
import com.iandtop.utils.APIRestResponse;
import com.iandtop.utils.ResponseUtils;
import com.iandtop.utils.RestOperateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;

/**
 * 更正收款失误
 * Created by zww on 2017/6/12.
 */
@Controller
@RequestMapping("/MealrecordUp")
public class MealrecordUpController {
    @Autowired
    private MealrecordUpService service;

    //
    @RequestMapping("/updateproceeds")
    @ResponseBody
    public APIRestResponse updateMealrecord(MealRecordModel mealRecordModel,Double money){
        int num=service.updateMealrecord(mealRecordModel,money);
        if(num==2){
            return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.UPDATE_DATA);
        }else{
            return ResponseUtils.getSuccessAPI(false,"false", RestOperateCode.UPDATE_DATA);
        }
    }

    //查询食堂订单
    @RequestMapping("/queryorder")
    @ResponseBody
    public APIRestResponse queryorder(MealRecordModel mealRecordModel, String start_ts, String end_ts, HttpServletResponse response)throws ParseException {
        List<MealRecordModel> resultList = service.queryorder(mealRecordModel,start_ts,end_ts);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (resultList.size() > 0)
            return ResponseUtils.getSuccess(true, "查询成功", RestOperateCode.GET_DATA,resultList);
        else
            return ResponseUtils.getFailed("", "查询失败", RestOperateCode.GET_DATA);
    }


}
