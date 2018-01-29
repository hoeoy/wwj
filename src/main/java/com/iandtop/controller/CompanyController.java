package com.iandtop.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iandtop.model.CompanyModel;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.service.CompanyService;
import com.iandtop.utils.APIRestResponse;
import com.iandtop.utils.ResponseUtils;
import com.iandtop.utils.RestOperateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller
 */
@Controller
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    /**
     * table数据展示
     * @param vo
     * @return
     */
    @RequestMapping("/retrieve")
    @ResponseBody
    public String listService(CompanyModel vo){
        List<CompanyModel> list=companyService.retrieveAllWithPage(vo);
        List<CompanyModel> all=companyService.retrieveAll();
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
        int status = companyService.deleteByPks(ids);

        if (status > 0)
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
    public APIRestResponse save(@RequestBody CompanyModel vo) {
        Integer num;
        if(vo !=null){

            if(vo.getPk_company()!= null){//如果前端传递过来pk,则判断为更新操作
                num = companyService.updateByPk(vo);
            }else{
                num = companyService.insertByMo(vo);
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


}



