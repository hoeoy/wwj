package com.iandtop.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iandtop.model.CompanyModel;
import com.iandtop.model.DeptModelVO;
import com.iandtop.model.StaffModel;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.model.system.UserModel;
import com.iandtop.service.UserService;
import com.iandtop.utils.APIRestResponse;
import com.iandtop.utils.IandtopHandlerInterceptor;
import com.iandtop.utils.ResponseUtils;
import com.iandtop.utils.RestOperateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Controller
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * table数据展示
     * @param vo
     * @return
     */
    @RequestMapping("/retrieve")
    @ResponseBody
    public String listService(UserModel vo){
        List<UserModel> list= userService.retrieveAllWithPage(vo);
        List<UserModel> all= userService.retrieveAll();
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
        int result = userService.deleteByPks(ids);

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
    public APIRestResponse save(@RequestBody UserModel vo) {
        Integer num;
        if(vo !=null){

            if(vo.getPk_user()!= null && vo.getPk_user().trim().length() > 0){//如果前端传递过来pk,则判断为更新操作
                num = userService.updateByPk(vo);
            }else{
                num = userService.insertByMo(vo);
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
    @RequestMapping("/retrievenus")
    public APIRestResponse retrievenus(String department_code) {
        List<StaffModel> result = userService.retrievenus(department_code);
        return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.GET_DATA,result);
    }

    @ResponseBody
    @RequestMapping("/userself")
    public APIRestResponse userself(@RequestBody List<UserModel> vos) {
        Integer result = userService.userself(vos);

        if(StatusCodeConstants.Success == result){
            return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.SAVE_DATA);
        }
        return ResponseUtils.getSuccessAPI(false,"false", RestOperateCode.SAVE_DATA);
    }

    @ResponseBody
    @RequestMapping("/login")
    public APIRestResponse login(UserModel model, HttpServletRequest request) {
        UserModel result = userService.checkByCodeAndPwd(model);

        if(result != null){

            HttpSession session = request.getSession();
            session.setAttribute(IandtopHandlerInterceptor.SESSION_USER,result);

            return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.GET_DATA,result);
        }
        return ResponseUtils.getSuccessAPI(false,"false", RestOperateCode.GET_DATA);
    }
    @ResponseBody
    @RequestMapping("/userlogin")
    public APIRestResponse userlogin(UserModel model,HttpServletResponse response,HttpServletRequest request) {
        List<UserModel> result = userService.loginByCodeAndPwd(model);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if(result.size()==1){
            HttpSession session = request.getSession();
            session.setAttribute(IandtopHandlerInterceptor.SESSION_USER,result.get(0));
            return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.GET_DATA,result);
        }
        return ResponseUtils.getSuccessAPI(false,"false", RestOperateCode.GET_DATA);
    }
    @ResponseBody
    @RequestMapping("/updatePwd")
    public APIRestResponse updatePwd(UserModel model,HttpServletResponse response) {
        List<UserModel> result = userService.oldPwd(model);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if(result.size()!=0){
            int line = userService.updatePwd(model);
            if(line == StatusCodeConstants.Success){
                return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.UPDATE_DATA);
            }
            return ResponseUtils.getSuccessAPI(false,"false", RestOperateCode.UPDATE_DATA);
        }
        return ResponseUtils.getSuccessAPI(false,"旧密码错误，更改密码失败", RestOperateCode.UPDATE_DATA);
    }
}



