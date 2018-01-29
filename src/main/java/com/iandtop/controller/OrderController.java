package com.iandtop.controller;
import com.github.pagehelper.PageInfo;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.service.OrderService;
import com.iandtop.model.OrderModel;
import com.iandtop.utils.APIRestResponse;
import com.iandtop.utils.ResponseUtils;
import com.iandtop.utils.RestOperateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Administrator on 2017/5/12.
 */
@Controller
@RequestMapping("/Order")
public class OrderController {
    @Autowired
    private OrderService service;
//同类型商品查询
    @ResponseBody
    @RequestMapping("/Findtypegoods")
    public APIRestResponse findgoods(int goodstype, Integer pageNo,HttpServletResponse response)throws ParseException {
        PageInfo<OrderModel> resultList = service.queryByPage(goodstype,pageNo, 8);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (resultList.getSize() > 0)
            return ResponseUtils.getSuccess(true, "查询成功", RestOperateCode.GET_DATA, resultList);
        else
            return ResponseUtils.getFailed("", "查询失败", RestOperateCode.GET_DATA);
    }
    //商品按名字查找
    @ResponseBody
    @RequestMapping("/Findgood")
    public APIRestResponse findgood(OrderModel ordermodel, Integer pageNo,HttpServletResponse response)throws ParseException {
        PageInfo<OrderModel> resultList = service.findgoodByPage(ordermodel,pageNo, 8);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (resultList.getSize() > 0)
            return ResponseUtils.getSuccess(true, "查询成功", RestOperateCode.GET_DATA, resultList);
        else
            return ResponseUtils.getFailed("", "查询失败", RestOperateCode.GET_DATA);
    }
    //商品列表点击进入商品详情
    @ResponseBody
    @RequestMapping("/Gooddetails")
    public APIRestResponse  gooddetails(int id,HttpServletResponse response)throws ParseException {
        List<OrderModel> resultList = service.gooddetails(id);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (resultList.size()!=0)
            return ResponseUtils.getSuccess(true, "查询成功", RestOperateCode.GET_DATA, resultList);
        else
            return ResponseUtils.getFailed("", "查询失败", RestOperateCode.GET_DATA);
    }
    //查询所有商品
    @ResponseBody
    @RequestMapping("/Find")
    public APIRestResponse find(Integer pageNo,HttpServletResponse response)throws ParseException {
        PageInfo<OrderModel> resultList = service.findByPage(pageNo, 8);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (resultList.getSize() > 0)
            return ResponseUtils.getSuccess(true, "查询成功", RestOperateCode.GET_DATA, resultList);
        else
            return ResponseUtils.getFailed("", "查询失败", RestOperateCode.GET_DATA);
    }
//大类查询商品
    @ResponseBody
    @RequestMapping("/findstylegoods")
    public APIRestResponse findstylegoods(int goodstype, Integer pageNo,HttpServletResponse response)throws ParseException {
        PageInfo<OrderModel> resultList = service.findstylegoodsByPage(goodstype,pageNo, 8);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (resultList.getSize() > 0)
            return ResponseUtils.getSuccess(true, "查询成功", RestOperateCode.GET_DATA, resultList);
        else
            return ResponseUtils.getFailed("", "查询失败", RestOperateCode.GET_DATA);
    }
    @ResponseBody
    @RequestMapping("/delgood")
    public APIRestResponse delgood(OrderModel ordermodel,HttpServletResponse response) {
        int result = service.delgood(ordermodel);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (result!=0)
            return ResponseUtils.getSuccess(true,"true", RestOperateCode.DELETE_DATA);
        else
            return ResponseUtils.getSuccess(false,"false", RestOperateCode.DELETE_DATA);
    }
    @ResponseBody
    @RequestMapping("/updategood")
    public APIRestResponse updategood(OrderModel ordermodel,HttpServletResponse response) {
        int result = service.updategood(ordermodel);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if(result == StatusCodeConstants.Success){
            return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.UPDATE_DATA);
        }
        return ResponseUtils.getSuccessAPI(false,"false", RestOperateCode.UPDATE_DATA);
    }



}
