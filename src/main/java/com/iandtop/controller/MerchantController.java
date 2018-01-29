package com.iandtop.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iandtop.model.meal.MerchantModel;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.model.system.RoleModel;
import com.iandtop.service.MerchantService;
import com.iandtop.utils.APIRestResponse;
import com.iandtop.utils.ResponseUtils;
import com.iandtop.utils.RestOperateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Controller
 */
@Controller
@RequestMapping("/merchant")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    /**
     * table数据展示
     * @param vo
     * @return
     */
    @RequestMapping("/retrieve")
    @ResponseBody
    public String listService(MerchantModel vo){
        List<MerchantModel> list= merchantService.retrieveAllWithPage(vo);
        List<MerchantModel> all= merchantService.retrieveAll();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total",all.size());
        jsonObject.put("rows", JSONArray.toJSON(list));

        return JSONArray.toJSON(jsonObject).toString();
    }

    /**
     * 批量删除
     * @param ids
     * @return true OR false
     */
    @ResponseBody
    @RequestMapping("/delete")
    public APIRestResponse deleteBatch(@RequestBody List<String> ids) {
        int result = merchantService.deleteByPks(ids);

        if (StatusCodeConstants.Success == result)
            return ResponseUtils.getSuccess(true,"true", RestOperateCode.DELETE_DATA);
        else
            return ResponseUtils.getSuccess(false,"false", RestOperateCode.DELETE_DATA);
    }


    /**
     * 新增
     *
     * @param vo
     * @return true OR false
     */
    @ResponseBody
    @RequestMapping("/save")
    public APIRestResponse save(@RequestBody MerchantModel vo) {
        Integer num;
        if(vo !=null){

            if(vo.getPk_merchant()!= null && vo.getPk_merchant().trim().length() > 0){//如果前端传递过来pk,则判断为更新操作
                num = merchantService.updateByPk(vo);
            }else{
                num = merchantService.insertByMo(vo);
            }

            if(StatusCodeConstants.Success == num){
                return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.UPDATE_DATA);
            }else{
                return ResponseUtils.getSuccessAPI(false,"false", RestOperateCode.UPDATE_DATA);
            }
        }else{
            return ResponseUtils.getSuccessAPI(false,"false", RestOperateCode.UPDATE_DATA,"参数不可为null");
        }
    }

    @ResponseBody
    @RequestMapping("/retrieveAll")
    public APIRestResponse retrieveAll() {
        List<MerchantModel> roles = merchantService.retrieveAll();

        return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.GET_DATA,roles);

    }

}



