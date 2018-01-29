package com.iandtop.controller;

import com.iandtop.model.system.NavigationModel;
import com.iandtop.model.system.UserModel;
import com.iandtop.service.IndexService;
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
 * Created by lz on 2017/5/18.
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private IndexService indexService;

    @ResponseBody
    @RequestMapping("/loadRoleNavigation")
    public APIRestResponse loadRoleNavigation(@RequestBody UserModel model){
        List<NavigationModel> result = indexService.retrieveWithUserRole(model);
        return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.GET_DATA,result);
    }

    @ResponseBody
    @RequestMapping("/loadAllNavigation")
    public APIRestResponse retrieveAllNavigation(@RequestBody UserModel model){
        List<NavigationModel> result = indexService.retrieveAllNavigation();
        return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.GET_DATA,result);
    }

    @ResponseBody
    @RequestMapping("/loadNavigation")
    public APIRestResponse loadNavigation(@RequestBody UserModel model){
        List<NavigationModel> result = indexService.retrieveWithUser(model);
        return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.GET_DATA,result);
    }

}
