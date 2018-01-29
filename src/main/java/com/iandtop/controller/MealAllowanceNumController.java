package com.iandtop.controller;/*
package com.iandtop.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iandtop.model.meal.MealAllowanceNumModel;
import com.iandtop.service.MealAllowanceNumService;
import com.iandtop.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

*/
/**
 * @author Klin
 * @date 2017/5/24 0024
 * @parm
 * @result
 * @description 补贴
 *//*

@Controller
@RequestMapping("/AllowanceNum")
public class MealAllowanceNumController {
    @Autowired
    private MealAllowanceNumService numService;


    */
/**
     * @author Klin
     * @date 2017/5/24 0024
     * @parm MealAllowanceNumModel
     * @result
     * @description 状态已发送的不允许删除
     *//*

    @ResponseBody
    @RequestMapping("retrieve")
    public Object retrieve(MealAllowanceNumModel model) {

        List<MealAllowanceNumModel> result = numService.retrieve(model);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", result.size());
        jsonObject.put("rows", JSONArray.toJSON(result));

//        return JSONArray.toJSON(jsonObject).toString();
        return ResponseUtils.getSuccessAPI(true,"","",result);
    }

    */
/**
     * @author Klin
     * @date 2017/5/24 0024
     * @parm MealAllowanceNumModel
     * @result
     * @description
     *//*

    @ResponseBody
    @RequestMapping("save")
    public Object save(MealAllowanceNumModel model) {
        int status = numService.save(model);
        return ResponseUtils.getSuccessAPI(status > 0 ? true : false, "", "", status);

    }

    */
/**
     * @author Klin
     * @date 2017/5/24 0024
     * @parm MealAllowanceNumModel
     * @result
     * @description 状态已发送的不允许删除
     *//*

    @ResponseBody
    @RequestMapping("del")
    public Object delData(@PathVariable(value = "pkcenum") String pkallowancenum) {
        int status = numService.delData(pkallowancenum);
        return null;
    }


}
*/
