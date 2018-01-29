package com.iandtop.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iandtop.model.device.DeviceModel;
import com.iandtop.model.device.DeviceTimeModel;
import com.iandtop.model.meal.MerchantModel;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.service.DeviceTimeService;
import com.iandtop.utils.APIRestResponse;
import com.iandtop.utils.ResponseUtils;
import com.iandtop.utils.RestOperateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.List;

/**
 * @author Klin
 * @description 描述
 * @create 2017/5/23 0023
 * @Version 1.0
 */

@Controller
@RequestMapping("/deviceTime")
public class DeviceTimeController {

    @Autowired
    private DeviceTimeService service;


    @ResponseBody
    @RequestMapping(value = "/saveTime")
    public Object saveTime(@RequestBody DeviceTimeModel model) throws ParseException {

        Integer result;

        if(model.getPk_device_time() != null && model.getPk_device_time().toString().trim().length() > 0){
            //更新操作
            result = service.updateTime(model);
        }else{
            //保存操作
            result = service.saveTime(model);
        }
        if (result == StatusCodeConstants.Success)
            return ResponseUtils.getSuccess(true, "保存成功", RestOperateCode.INSERT_DATA);
        else
            return ResponseUtils.getSuccess(false, "失败", RestOperateCode.INSERT_DATA);
    }


    @ResponseBody
    @RequestMapping(value = "/findTime")
    public String findTime(String pk_device_time) {
        List<DeviceTimeModel> listModel = service.findTime(pk_device_time);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", listModel.size());
        jsonObject.put("rows", JSONArray.toJSON(listModel));
        return JSONArray.toJSON(jsonObject).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/updateTime")
    public Object updateTime(DeviceTimeModel model) {
        int status = service.updateTime(model);

        return ResponseUtils.getSuccess(status > 0 ? true : false, "OK", RestOperateCode.UPDATE_DATA);
    }

    @ResponseBody
    @RequestMapping(value = "/delTime")
    public Object delTime(String pk_device_time) {
        int status = service.delTime(pk_device_time);
        return ResponseUtils.getSuccess(status > 0 ? true : false, "OK", RestOperateCode.DELETE_DATA);
    }

    @ResponseBody
    @RequestMapping("/retrieveTree")
    public APIRestResponse retrieveTree() {
        List<DeviceTimeModel> result = service.retrieveTree();

        return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.GET_DATA,result);
    }

}
