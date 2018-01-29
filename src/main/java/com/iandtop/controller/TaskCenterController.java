package com.iandtop.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iandtop.model.StaffModel;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.model.system.RoleModel;
import com.iandtop.model.task.TaskConfigModel;
import com.iandtop.model.task.TaskLogModel;
import com.iandtop.service.TaskConfigService;
import com.iandtop.service.TaskLogService;
import com.iandtop.utils.APIRestResponse;
import com.iandtop.utils.ResponseUtils;
import com.iandtop.utils.RestOperateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by lz on 2017/7/19.
 */
@Controller
@RequestMapping("/task")
public class TaskCenterController {

    @Autowired
    private TaskLogService taskLogService;

    @Autowired
    private TaskConfigService taskConfigService;

    @ResponseBody
    @RequestMapping(value="/retrievelogs")
    public String retrieveLogs(TaskLogModel vo, HttpServletRequest request) {

        List<TaskLogModel> result = taskLogService.retrieveAllWithPage(vo);

        List<TaskLogModel> resultAll = taskLogService.retrieveAll();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total",resultAll.size());
        jsonObject.put("rows", JSONArray.toJSON(result));

        return JSONArray.toJSON(jsonObject).toString();
    }

    @ResponseBody
    @RequestMapping("/syncnow ")
    public APIRestResponse syncnow () throws Exception {

        return taskConfigService.SyncNow();
    }

    @ResponseBody
    @RequestMapping("/save")
    public APIRestResponse save(@RequestBody TaskConfigModel vo) {
        Integer num;
        if(vo !=null){

            if(vo.getPk_task_config()!= null && vo.getPk_task_config().trim().length() > 0){//如果前端传递过来pk,则判断为更新操作
                num = taskConfigService.updateByPk(vo);
            }else{
                num = taskConfigService.insertByMo(vo);
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
    @RequestMapping("/delete")
    public APIRestResponse deleteBatch(@RequestBody List<String> ids) {
        int result = taskConfigService.deleteByPks(ids);

        if (StatusCodeConstants.Success == result)
            return ResponseUtils.getSuccess(true,"true", RestOperateCode.DELETE_DATA);
        else
            return ResponseUtils.getSuccess(false,"false", RestOperateCode.DELETE_DATA);
    }

    @ResponseBody
    @RequestMapping("/deletelog")
    public APIRestResponse deletelog(@RequestBody List<String> ids) {
        int result = taskLogService.deleteByPks(ids);

        if (StatusCodeConstants.Success == result)
            return ResponseUtils.getSuccess(true,"true", RestOperateCode.DELETE_DATA);
        else
            return ResponseUtils.getSuccess(false,"false", RestOperateCode.DELETE_DATA);
    }

    @ResponseBody
    @RequestMapping("/retrieveconfigs")
    public String retrieveConfigs() {
        List<TaskConfigModel> result = taskConfigService.retrieveAll();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total",result.size());
        jsonObject.put("rows", JSONArray.toJSON(result));

        return JSONArray.toJSON(jsonObject).toString();

    }

}
