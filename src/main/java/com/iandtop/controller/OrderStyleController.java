package com.iandtop.controller;

import com.iandtop.model.OrderModelVo;
import com.iandtop.model.OrderStyleModel;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.service.OrderStyleService;
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
 * Created by Administrator on 2017/5/15.
 */
@Controller
@RequestMapping("/OrderStyle")
public class OrderStyleController {
    @Autowired
    private OrderStyleService service;

    @ResponseBody
    @RequestMapping("/findStyle")
    public APIRestResponse findStyle(HttpServletResponse response)throws ParseException {
        long start = System.currentTimeMillis();
        List<OrderStyleModel> resultList = service.findStyle();
        System.out.println("大类time:"+(System.currentTimeMillis()-start));
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (resultList.size()!=0)
            return ResponseUtils.getSuccess(true, "查询成功", RestOperateCode.GET_DATA, resultList);
        else
            return ResponseUtils.getFailed("", "查询失败", RestOperateCode.GET_DATA);
    }

    @ResponseBody
    @RequestMapping("/findtype")
    public APIRestResponse  findtype(int pk_parentid,HttpServletResponse response)throws ParseException {
        long start = System.currentTimeMillis();
        List<OrderModelVo> resultList = service.findtype(pk_parentid);
        System.out.println("小类time:"+(System.currentTimeMillis()-start));
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (resultList.size()!=0)
            return ResponseUtils.getSuccess(true, "查询成功", RestOperateCode.GET_DATA, resultList);
        else
            return ResponseUtils.getFailed("", "查询失败", RestOperateCode.GET_DATA);
    }
    @ResponseBody
    @RequestMapping("/addOrderStyle")
    public APIRestResponse  addOrderStyle(OrderStyleModel orderStyleModel,HttpServletResponse response)throws ParseException {
        List<OrderStyleModel> line = service.findStyleforcondition(orderStyleModel);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if(line.size()>0){
            return ResponseUtils.getFailed("", "名字重复", RestOperateCode.GET_DATA);
        }else{
            int count = service.addOrderStyle(orderStyleModel);
            if (count!=0)
                return ResponseUtils.getSuccess(true, "新增成功", RestOperateCode.GET_DATA, orderStyleModel);
            else
                return ResponseUtils.getFailed("", "新增失败", RestOperateCode.GET_DATA);
        }

    }
    @ResponseBody
    @RequestMapping("/addOrderType")
    public APIRestResponse  addOrderType(OrderModelVo orderModelVo,HttpServletResponse response)throws ParseException {
        List<OrderModelVo> line=service.findtypeforcondition(orderModelVo);
        response.setHeader("Access-Control-Allow-Origin", "*");
        boolean isExtis = false;
        if(line.size()>0){
            for(OrderModelVo oVo : line){
                if(orderModelVo.getPk_parentid().equals(oVo.getPk_parentid())){
                    isExtis = true;
                    break;
                }
            }
        }
        if(isExtis){
            return ResponseUtils.getFailed("", "名字重复", RestOperateCode.GET_DATA);
        }else{
            int count = service.addOrderType(orderModelVo);
            if (count!=0)
                return ResponseUtils.getSuccess(true, "新增成功", RestOperateCode.GET_DATA, count);
            else
                return ResponseUtils.getFailed("", "新增失败", RestOperateCode.GET_DATA);
        }
    }
    @ResponseBody
    @RequestMapping("/delOrderStyle")
    public APIRestResponse delOrderStyle(OrderStyleModel orderStyleModel,HttpServletResponse response) {
        int result = service.delOrderStyle(orderStyleModel);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (result!=0)
            return ResponseUtils.getSuccess(true,"true", RestOperateCode.DELETE_DATA);
        else
            return ResponseUtils.getSuccess(false,"false", RestOperateCode.DELETE_DATA);
    }
    @ResponseBody
    @RequestMapping("/delOrderStype")
    public APIRestResponse delOrderType(OrderModelVo orderModelVo ,HttpServletResponse response) {
        int result = service.delOrderType(orderModelVo);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (result!=0)
            return ResponseUtils.getSuccess(true,"true", RestOperateCode.DELETE_DATA);
        else
            return ResponseUtils.getSuccess(false,"false", RestOperateCode.DELETE_DATA);
    }
    @ResponseBody
    @RequestMapping("/updateOrderStyle")
    public APIRestResponse updateOrderStyle(OrderStyleModel orderStyleModel,HttpServletResponse response) {
        int result = service.updateOrderStyle(orderStyleModel);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if(result == StatusCodeConstants.Success){
            return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.UPDATE_DATA);
        }
        return ResponseUtils.getSuccessAPI(false,"false", RestOperateCode.UPDATE_DATA);
    }
    @ResponseBody
    @RequestMapping("/updateOrderType")
    public APIRestResponse updateOrderType(OrderModelVo orderModelVo,HttpServletResponse response) {
        int result = service.updateOrderType(orderModelVo);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if(result == StatusCodeConstants.Success){
            return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.UPDATE_DATA);
        }
        return ResponseUtils.getSuccessAPI(false,"false", RestOperateCode.UPDATE_DATA);
    }
    /**
    @ResponseBody
    @RequestMapping("/findStyleforcondition")
    public APIRestResponse findStyleforcondition(OrderStyleModel orderStyleModel,HttpServletResponse response)throws ParseException {
        List<OrderStyleModel> resultList = service.findStyleforcondition(orderStyleModel);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (resultList.size()!=0)
            return ResponseUtils.getSuccess(true, "查询成功", RestOperateCode.GET_DATA, resultList);
        else
            return ResponseUtils.getFailed("", "查询失败", RestOperateCode.GET_DATA);
    }
    **/
}
