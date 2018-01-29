package com.iandtop.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iandtop.model.device.DeviceModel;
import com.iandtop.model.meal.MerchantModel;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.service.DeviceService;
import com.iandtop.utils.APIRestResponse;
import com.iandtop.utils.ResponseUtils;
import com.iandtop.utils.RestOperateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by lz on 2017/5/4.
 */

@Controller
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @ResponseBody
    @RequestMapping(value="/retrieve")
    public String retrieve(DeviceModel vo, HttpServletRequest request) {

        List<DeviceModel> result = deviceService.retrieveAllWithPage(vo);

        List<DeviceModel> resultAll = deviceService.retrieveAll();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total",resultAll.size());
        jsonObject.put("rows",JSONArray.toJSON(result));

        return JSONArray.toJSON(jsonObject).toString();
    }


    @ResponseBody
    @RequestMapping("/save")
    public APIRestResponse save(@RequestBody DeviceModel model) {

        Integer num = 0;
        if(model !=null){

            if(model.getPk_device()!= null && model.getPk_device().trim() != ""){//如果前端传递过来pk,则判断为更新操作
                return deviceService.updateByPk(model);
            }else{
                return deviceService.insertByMo(model);
            }
        }else{
            return ResponseUtils.getSuccessAPI(false,"false", RestOperateCode.UPDATE_DATA,"参数不可为null");
        }
    }

    @ResponseBody
    @RequestMapping("/delete")
    public APIRestResponse delete(@RequestBody List<String> pks) {
        Integer num = deviceService.deleteByPks(pks);
        if(num > 0){
            return ResponseUtils.getSuccessAPI(true,"删除成功", RestOperateCode.DELETE_DATA);
        }else{
            return ResponseUtils.getSuccessAPI(false,"删除失败", RestOperateCode.DELETE_DATA);
        }

    }

    @ResponseBody
    @RequestMapping("/retrieveTree")
    public APIRestResponse retrieveTree() {
        List<MerchantModel> result = deviceService.retrieveTree();

        return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.GET_DATA,result);
    }

    @ResponseBody
    @RequestMapping("/findAllretrieve")
    public APIRestResponse findAllretrieve() {

        List<DeviceModel> result = deviceService.findAllretrieve();

        if(result.size()>0){
            return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.GET_DATA,result);
        }
        return ResponseUtils.getSuccessAPI(false,"false", RestOperateCode.GET_DATA);
    }

}
