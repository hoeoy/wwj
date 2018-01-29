package com.iandtop.controller;

import com.iandtop.model.OrderModel;
import com.iandtop.service.GoodsService;
import com.iandtop.utils.APIRestResponse;
import com.iandtop.utils.ResponseUtils;
import com.iandtop.utils.RestOperateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * Controller
 */
@Controller
@RequestMapping("/Goods")
public class GoodsController {

    @Autowired
    private GoodsService service;

    @ResponseBody
    @RequestMapping("/insertGoods")
    public APIRestResponse insertGoods(HttpServletResponse response, OrderModel model){
        Integer ststus=service.insertGoods(model);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (ststus!=0)
            return ResponseUtils.getSuccess(true,"", RestOperateCode.UPDATE_DATA);
        else
            return ResponseUtils.getFailed("", "新增失败", RestOperateCode.GET_DATA);

    }

    @ResponseBody
    @RequestMapping("/uploadImg")
    public Object insertGoods(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request,HttpServletResponse response){
        System.out.println("开始");
        response.setHeader("Access-Control-Allow-Origin", "*");
        String path = request.getSession().getServletContext().getRealPath("upload");
        String houzui = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = System.currentTimeMillis() + houzui;
        System.out.println(path);
        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        //保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return ResponseUtils.getSuccess(true,request.getContextPath()+"/upload/"+fileName, RestOperateCode.UPDATE_DATA);
        String image=request.getContextPath()+"/upload/"+fileName;
        //return ResponseUtils.getSuccess(true, "查询成功", RestOperateCode.GET_DATA, resultList);
        return ResponseUtils.getSuccess(true, "上传成功", RestOperateCode.GET_DATA, image);
    }


}



