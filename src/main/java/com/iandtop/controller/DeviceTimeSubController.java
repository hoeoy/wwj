package com.iandtop.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iandtop.model.device.DeviceTimeModel;
import com.iandtop.model.device.DeviceTimeSubModel;
import com.iandtop.service.DeviceTimeService;
import com.iandtop.service.DeviceTimeSubService;
import com.iandtop.utils.ResponseUtils;
import com.iandtop.utils.RestOperateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/deviceSub")
public class DeviceTimeSubController {

    @Autowired
    private DeviceTimeSubService service;

    @Autowired
    private DeviceTimeService timeService;

    @ResponseBody
    @RequestMapping(value = "/save")
    public Object saveTime(@RequestBody List<DeviceTimeSubModel> model, @PathVariable(value = "time_name") String time_name) throws ParseException {
        DeviceTimeModel deviceTimeModel = new DeviceTimeModel();
        if (time_name != null) {
            deviceTimeModel.setTime_name(time_name);
           String pkTime= String.valueOf(timeService.saveTime(deviceTimeModel));
            for (DeviceTimeSubModel tmp : model) {
                tmp.setPk_device_time(pkTime);
                if (tmp.getStart_time() != null && tmp.getEnd_time() != null && tmp.getPk_device_time() != null) {
                    int status = service.saveTime(tmp);
                    if (status > 0)
                        return ResponseUtils.getSuccess(true, "保存成功", RestOperateCode.INSERT_DATA);
                    else
                        return ResponseUtils.getSuccess(false, "失败", RestOperateCode.INSERT_DATA);
                }
            }
        }
        return ResponseUtils.getSuccess(false, "失败噢", RestOperateCode.INSERT_DATA);
    }


    @ResponseBody
    @RequestMapping(value = "/find")
    public String findTime(String pk_device_time) {
        List<DeviceTimeSubModel> listModel = service.findTime(pk_device_time);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", listModel.size());
        jsonObject.put("rows", JSONArray.toJSON(listModel));
        return JSONArray.toJSON(jsonObject).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/update")
    public Object updateTime(@RequestBody DeviceTimeSubModel model) {
        int status = service.updateTime(model);

        return ResponseUtils.getSuccess(status > 0 ? true : false, "OK", RestOperateCode.UPDATE_DATA);
    }

    @ResponseBody
    @RequestMapping(value = "/del")
    public Object delTime(String pk_device_time_sub) {
        int status = service.delTime(pk_device_time_sub);
        return ResponseUtils.getSuccess(status > 0 ? true : false, "OK", RestOperateCode.DELETE_DATA);
    }
}
