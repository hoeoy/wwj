package com.iandtop.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iandtop.model.CompanyModel;
import com.iandtop.model.DeptModelVO;
import com.iandtop.service.DeptService;
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
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;
    
    @ResponseBody
    @RequestMapping(value="/retrieve")
    public String retrieve(DeptModelVO vo, HttpServletRequest request) {

        List<DeptModelVO> result = deptService.retrieveAllWithPage(vo);

        List<DeptModelVO> resultAll = deptService.retrieveAll();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total",resultAll.size());
        jsonObject.put("rows",JSONArray.toJSON(result));

        return JSONArray.toJSON(jsonObject).toString();
    }


    @ResponseBody
    @RequestMapping("/save")
    public APIRestResponse save(@RequestBody DeptModelVO dept) {

        Integer num = 0;
        if(dept !=null){

            if(dept.getPk_department()!= null && dept.getPk_department().trim() != ""){//如果前端传递过来pk,则判断为更新操作
                num = deptService.updateByPk(dept);
            }else{
                num = deptService.insertByMo(dept);
            }

            if(num>=1){
                return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.UPDATE_DATA);
            }else{
                return ResponseUtils.getSuccessAPI(false,"false", RestOperateCode.UPDATE_DATA);
            }
        }else{
            return ResponseUtils.getSuccessAPI(false,"false", RestOperateCode.UPDATE_DATA,"参数不可为null");
        }
    }

    @ResponseBody
    @RequestMapping("/delete")
    public APIRestResponse delete(@RequestBody List<String> pks) {
        Integer num = deptService.deleteByPks(pks);
        if(num>=1){
            return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.DELETE_DATA);
        }else{
            return ResponseUtils.getSuccessAPI(false,"false", RestOperateCode.DELETE_DATA);
        }

    }

    @ResponseBody
    @RequestMapping(value="/retrieveTree")
    public APIRestResponse retrieveTree() {

        List<CompanyModel> result = deptService.retrieveDeptTree();

        return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.GET_DATA,result);
    }

    @ResponseBody
    @RequestMapping(value="/retrievedept")
    public APIRestResponse retrievedept(@RequestBody DeptModelVO deptModel) {

        DeptModelVO deptModelVO = deptService.retrieveByCode(deptModel);

        return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.GET_DATA,deptModelVO);

    }


}
