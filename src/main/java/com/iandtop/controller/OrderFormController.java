package com.iandtop.controller;

import com.github.pagehelper.PageInfo;
import com.iandtop.model.OrderDetailModel;
import com.iandtop.model.OrderDetailModelVO;
import com.iandtop.model.OrderFormModel;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.service.OrderFormService;
import com.iandtop.service.OrderService;
import com.iandtop.utils.APIRestResponse;
import com.iandtop.utils.MD5;
import com.iandtop.utils.ResponseUtils;
import com.iandtop.utils.RestOperateCode;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/18.
 */
@Controller
@RequestMapping("/OrderForm")
public class OrderFormController {
    @Autowired
    private OrderFormService service;
    @Autowired
    private OrderService orderService;
    @ResponseBody
    @RequestMapping("/findformforname")
    public APIRestResponse findformforname(OrderFormModel orderform, Integer pageNo, HttpServletResponse response)throws ParseException {
        PageInfo<OrderFormModel> resultList = service.findformfornameByPage(orderform,pageNo, 10);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (resultList.getSize() > 0)
            return ResponseUtils.getSuccess(true, "查询成功", RestOperateCode.GET_DATA, resultList);
        else
            return ResponseUtils.getFailed("", "查询失败", RestOperateCode.GET_DATA);
    }
    @Transactional
    @ResponseBody
    @RequestMapping(value = "/insert",method = RequestMethod.POST,consumes = "application/json")
    public APIRestResponse insertform(@RequestBody List<OrderDetailModelVO> OrderDetailModel, HttpServletResponse response) {
        String pk_staff = OrderDetailModel.get(0).getStaff_code();
        String passwrod = OrderDetailModel.get(0).getPasswrod();
        double meal_money = OrderDetailModel.get(0).getMeal_money();
        int result = orderService.payOrderOnLine(pk_staff, passwrod, meal_money);
        if (result == StatusCodeConstants.Pwd_Error) {
            return ResponseUtils.getSuccessAPI(false, "密码错误", RestOperateCode.INSERT_DATA);
        }
        if (result == StatusCodeConstants.Sys_Error) {
            return ResponseUtils.getSuccessAPI(false, "系统内部错误", RestOperateCode.INSERT_DATA);
        }
        if (result == StatusCodeConstants.Param_Error) {
            return ResponseUtils.getSuccessAPI(false, "参数错误", RestOperateCode.INSERT_DATA);
        }
        if (result == StatusCodeConstants.Not_Found_Psn_Info) {
            return ResponseUtils.getSuccessAPI(false, "未找到人员信息", RestOperateCode.INSERT_DATA);
        }
        if (result == StatusCodeConstants.Code_Repeat) {
            return ResponseUtils.getSuccessAPI(false, "编码重复", RestOperateCode.INSERT_DATA);
        }
        if (result == StatusCodeConstants.Not_Found_Card_Info_Or_Card_Lost) {
            return ResponseUtils.getSuccessAPI(false, "未找到卡片信息", RestOperateCode.INSERT_DATA);
        }
        if (result == StatusCodeConstants.Psn_Have_Multiple_Card) {
            return ResponseUtils.getSuccessAPI(false, "此人在系统中有多张", RestOperateCode.INSERT_DATA);
        }
        if (result == StatusCodeConstants.Card_Not_Normal_State) {
            return ResponseUtils.getSuccessAPI(false, "卡片不是正常状态", RestOperateCode.INSERT_DATA);
        }

        if (result == StatusCodeConstants.Money_Not_Enough) {
            return ResponseUtils.getSuccessAPI(false, "余额不足", RestOperateCode.INSERT_DATA);
        }
        if (result == 100) {
            int line2 = 0;
            String pk_formcode = MD5.MD5UUID();
            Map<String, String> Parameter = new HashMap<String, String>();
            Parameter.put("formpeople", OrderDetailModel.get(0).getFormpeople());
            Parameter.put("staff_code", OrderDetailModel.get(0).getStaff_code());
            Parameter.put("pk_formcode", pk_formcode);
            Parameter.put("meal_money", String.valueOf(OrderDetailModel.get(0).getMeal_money()));
            int line = service.insertForm(Parameter);
            if (line > 0) {
            for (OrderDetailModelVO tmp : OrderDetailModel) {
                    Map<String, String> Parameters = new HashMap<String, String>();
                    Parameters.put("foodname", tmp.getFoodname());
                    Parameters.put("count", String.valueOf(tmp.getCount()));
                    Parameters.put("pk_formcode", pk_formcode);
                    Parameters.put("price", String.valueOf(tmp.getPrice()));
                    tmp.setFk_formcode(Integer.parseInt(pk_formcode));
                    line2 += service.insertdetail(Parameters);
                }
            }
            return ResponseUtils.getSuccessAPI(line2 > 0 ? true : false, line2 > 0 ? "操作成功" : "操作失败", RestOperateCode.INSERT_DATA);

        } else {
            return ResponseUtils.getSuccessAPI(false, "操作失败", RestOperateCode.INSERT_DATA);
        }
    }
    @ResponseBody
    @RequestMapping(value = "/insert2",method = RequestMethod.POST,consumes = "application/json")
    public APIRestResponse insertform2(@RequestBody List<OrderDetailModelVO> OrderDetailModel, HttpServletResponse response){
        int line3=0;
        Map<String,String> Parameter=new HashMap<String,String>();
        String pk_formcode = MD5.MD5UUID();
        Parameter.put("formpeople",OrderDetailModel.get(0).getFormpeople());
        Parameter.put("staff_code",OrderDetailModel.get(0).getStaff_code());
        Parameter.put("pk_formcode",pk_formcode);
        Parameter.put("meal_money", String.valueOf(OrderDetailModel.get(0).getMeal_money()));
        int line = service.insertForm2(Parameter);
        if(line>0){
        for (OrderDetailModelVO tmp:OrderDetailModel){
                Map<String,String> Parameters=new HashMap<String,String>();
                Parameters.put("foodname",tmp.getFoodname());
                Parameters.put("count", String.valueOf(tmp.getCount()));
                Parameters.put("pk_formcode",pk_formcode);
                Parameters.put("price", String.valueOf(tmp.getPrice()));
                tmp.setFk_formcode(Integer.parseInt(pk_formcode));
                line3 += service.insertdetail2(Parameters);
            }
        }
        return ResponseUtils.getSuccessAPI(line3>0?true:false,line3>0?"下单成功":"下单失败", RestOperateCode.INSERT_DATA);
    }
    @ResponseBody
    @RequestMapping("/updateForm")
    public APIRestResponse updateForm(OrderFormModel orderFormModel,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String staff_code = orderFormModel.getStaff_code();
        Double meal_money = orderFormModel.getMeal_money();
        String pk_device = orderFormModel.getPk_device();
        int result = orderService.payOrderOffline(staff_code,meal_money,pk_device);
        if (result==StatusCodeConstants.Sys_Error) {
            return ResponseUtils.getSuccessAPI(false, "系统内部错误", RestOperateCode.INSERT_DATA);
        }
        if (result==StatusCodeConstants.Param_Error) {
            return ResponseUtils.getSuccessAPI(false, "参数错误", RestOperateCode.INSERT_DATA);
        }
        if (result==StatusCodeConstants.Money_Not_Enough) {
            return ResponseUtils.getSuccessAPI(false, "余额不足", RestOperateCode.INSERT_DATA);
        }
        int line = service.updateForm(orderFormModel);
        if(line == StatusCodeConstants.Success){
            return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.UPDATE_DATA);
        }
        return ResponseUtils.getSuccessAPI(false,"false", RestOperateCode.UPDATE_DATA);
    }
    @ResponseBody
    @RequestMapping("/updateForm2")
    public APIRestResponse updateForm2(OrderFormModel orderFormModel,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String staff_code = orderFormModel.getStaff_code();
        String passwrod = null;
        Double meal_money = orderFormModel.getMeal_money();
        int result = orderService.payOrderOnLine(staff_code, passwrod, meal_money);
        if (result==StatusCodeConstants.Pwd_Error) {
            return ResponseUtils.getSuccessAPI(false, "密码错误", RestOperateCode.INSERT_DATA);
        }
        if (result==StatusCodeConstants.Sys_Error) {
            return ResponseUtils.getSuccessAPI(false, "系统内部错误", RestOperateCode.INSERT_DATA);
        }
        if (result==StatusCodeConstants.Param_Error) {
            return ResponseUtils.getSuccessAPI(false, "参数错误", RestOperateCode.INSERT_DATA);
        }
        if (result==StatusCodeConstants.Not_Found_Psn_Info) {
            return ResponseUtils.getSuccessAPI(false, "未找到人员信息", RestOperateCode.INSERT_DATA);
        }
        if (result==StatusCodeConstants.Code_Repeat) {
            return ResponseUtils.getSuccessAPI(false, "编码重复", RestOperateCode.INSERT_DATA);
        }
        if (result==StatusCodeConstants.Not_Found_Card_Info_Or_Card_Lost) {
            return ResponseUtils.getSuccessAPI(false, "未找到卡片信息", RestOperateCode.INSERT_DATA);
        }
        if (result==StatusCodeConstants.Psn_Have_Multiple_Card) {
            return ResponseUtils.getSuccessAPI(false, "此人在系统中有多张", RestOperateCode.INSERT_DATA);
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
