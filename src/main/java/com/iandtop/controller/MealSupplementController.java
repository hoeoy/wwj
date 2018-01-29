package com.iandtop.controller;


import com.github.pagehelper.PageInfo;
import com.iandtop.model.card.CardModel;
import com.iandtop.model.form.ChargeForSModel;
import com.iandtop.model.meal.MealRecordModel;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.service.MealSupplementService;
import com.iandtop.utils.APIRestResponse;
import com.iandtop.utils.ResponseUtils;
import com.iandtop.utils.RestOperateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;

@Controller
@RequestMapping("/MealSupplement")
public class MealSupplementController {

    @Autowired
    private MealSupplementService service;

    @ResponseBody
    @RequestMapping(value="/findCardByPage",method = RequestMethod.GET)
    public APIRestResponse ChargeDepartment(@RequestParam(value = "staff_code", required = false) String staff_code,
                                            @RequestParam(value = "staff_name", required = false) String staff_name,
                                            @RequestParam(value = "card_code", required = false) String card_code,
                                            @RequestParam(value = "department_code", required = false) String department_code,
                                            @RequestParam(value = "pageNo") Integer pageNo,
                                            @RequestParam(value = "pageSize") Integer pageSize,
                                            HttpServletResponse response)throws ParseException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        CardModel model = new CardModel();
        model.setStaff_code(staff_code);
        model.setStaff_name(staff_name);
        model.setCard_code(card_code);
        model.setDepartment_code(department_code);
        PageInfo<CardModel> resultList =  service.findCardByPage(model,pageNo,pageSize);
        if (resultList.getSize() > 0)
            return ResponseUtils.getSuccess(true, "查询成功", RestOperateCode.GET_DATA, resultList);
        else
            return ResponseUtils.getFailed("", "查询失败", RestOperateCode.GET_DATA);
    }

    @ResponseBody
    @RequestMapping("/supplement")
    public APIRestResponse supplement(MealRecordModel model)throws ParseException {
        int result = service.supplement(model);
        if (result==StatusCodeConstants.Sys_Error) {
            return ResponseUtils.getSuccessAPI(false, "系统内部错误", RestOperateCode.INSERT_DATA);
        }
        if (result==StatusCodeConstants.Param_Error) {
            return ResponseUtils.getSuccessAPI(false, "参数错误", RestOperateCode.INSERT_DATA);
        }
        if (result==StatusCodeConstants.Card_Not_Normal_State) {
            return ResponseUtils.getSuccessAPI(false, "卡片不是正常状态", RestOperateCode.INSERT_DATA);
        }
        if (result==StatusCodeConstants.Money_Not_Enough) {
            return ResponseUtils.getSuccessAPI(false, "余额不足", RestOperateCode.INSERT_DATA);
        }
        return ResponseUtils.getSuccessAPI(true, "操作成功", RestOperateCode.INSERT_DATA);
    }

}
