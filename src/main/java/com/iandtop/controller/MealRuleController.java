package com.iandtop.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iandtop.model.meal.MealRuleModel;
import com.iandtop.model.meal.MerchantModel;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.service.MealRuleService;
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
@RequestMapping("/mealrule")
public class MealRuleController {

    @Autowired
    private MealRuleService mealRuleService;

    /**
     * table数据展示
     * @param vo
     * @return
     */
    @RequestMapping("/retrieve")
    @ResponseBody
    public String listService(MealRuleModel vo){
        List<MealRuleModel> list= mealRuleService.retrieveAllWithPage(vo);
        List<MealRuleModel> all= mealRuleService.retrieveAll();
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
        int result = mealRuleService.deleteByPks(ids);

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
    public APIRestResponse save(@RequestBody MealRuleModel vo) {
        Integer num;
        if(vo !=null){

            if(vo.getPk_meal_rule()!= null && vo.getPk_meal_rule().trim().length() > 0){//如果前端传递过来pk,则判断为更新操作
                num = mealRuleService.updateByPk(vo);
            }else{
                num = mealRuleService.insertByMo(vo);
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
        List<MealRuleModel> roles = mealRuleService.retrieveAll();

        return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.GET_DATA,roles);

    }

    @RequestMapping("createTables")
    public List<String> createTables(){
        return mealRuleService.createMealRecordForm();
    }

}



