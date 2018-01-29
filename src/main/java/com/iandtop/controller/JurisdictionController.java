package com.iandtop.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iandtop.model.DeptModelVO;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.model.system.JurisdictionModel;
import com.iandtop.service.JurisdictionService;
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
@RequestMapping("/jurisdiction")
public class JurisdictionController {

    @Autowired
    private JurisdictionService jurisdictionService;

    @ResponseBody
    @RequestMapping("/save")
    public APIRestResponse save(@RequestBody List<JurisdictionModel> models) {

        Integer result = jurisdictionService.insertByVOs(models);
        if(StatusCodeConstants.Success == result){
            return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.INSERT_DATA);
        }else{
            return ResponseUtils.getSuccessAPI(false,"false", RestOperateCode.INSERT_DATA);
        }
    }

    @ResponseBody
    @RequestMapping("/delete")
    public APIRestResponse delete(@RequestBody List<String> pks) {
        Integer num = jurisdictionService.deleteByPks(pks);
        if(StatusCodeConstants.Success == num){
            return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.DELETE_DATA);
        }else{
            return ResponseUtils.getSuccessAPI(false,"false", RestOperateCode.DELETE_DATA);
        }
    }

}
