package com.iandtop.controller;

import com.iandtop.model.OrderDetailModel;
import com.iandtop.service.OrderDetailService;
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
 * Created by Administrator on 2017/5/19.
 */
@Controller
@RequestMapping("/OrderDetail")
public class OrderDetailController {
    @Autowired
    private OrderDetailService service;
    @ResponseBody
    @RequestMapping("/findOrderDetail")
    public APIRestResponse findOrderDetail(OrderDetailModel Detail, HttpServletResponse response)throws ParseException {
        List<OrderDetailModel> resultList = service.findOrderDetail(Detail);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (resultList.size() > 0)
            return ResponseUtils.getSuccess(true, "查询成功", RestOperateCode.GET_DATA, resultList);
        else
            return ResponseUtils.getFailed("", "查询失败", RestOperateCode.GET_DATA);
    }
}
