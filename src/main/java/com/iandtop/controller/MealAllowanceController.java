package com.iandtop.controller;/*
package com.iandtop.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iandtop.model.meal.MealAllowanceModel;
import com.iandtop.model.meal.MealAllowanceNumModel;
import com.iandtop.service.MealAllowanceNumService;
import com.iandtop.service.MealAllowanceService;
import com.iandtop.utils.MD5;
import com.iandtop.utils.ResponseUtils;
import com.iandtop.utils.excel.ExcelDataFormatter;
import com.iandtop.utils.excel.ExcelUtils;
import com.iandtop.utils.excel.MealAllowanceExcel;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

*/
/**
 * @author Klin
 * @description 描述
 * @create 2017/5/25 0025
 * @Version 1.0
 *//*

@Controller
@RequestMapping("/MealAllowance")
public class MealAllowanceController {

    @Autowired
    private MealAllowanceService service;

    @Autowired
    private MealAllowanceNumService numService;

    @ResponseBody
    @RequestMapping("save")
    public Object save(@RequestBody List<MealAllowanceModel> modelVO) {
        int status = 0;
        for (MealAllowanceModel tmp : modelVO) {
            status += service.save(tmp);
        }
//        status = service.save(models);

        return ResponseUtils.getSuccessAPI(status > 0 ? true : false, "", "");
    }

    @ResponseBody
    @RequestMapping("find")
    public Object findData(MealAllowanceModel model) {
        List<MealAllowanceModel> ListDate = service.findData(model);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", ListDate.size());
        jsonObject.put("rows", JSONArray.toJSON(ListDate));
//        return JSONArray.toJSON(jsonObject).toString();
        return ResponseUtils.getSuccess(ListDate.size() > 0 ? true : false, "", "", ListDate);
    }

    @ResponseBody
    @RequestMapping("isok")
    public Object isok(@Param(value = "pkallowancenum") String pkallowancenum) {
        int status=service.isok(pkallowancenum);

        return ResponseUtils.getSuccess(status > 0 ? true : false, "", "", status);

    }


    @ResponseBody
    @RequestMapping("ULE")
    public Object ule(@RequestParam MultipartFile file, HttpServletRequest request) {
        int status = 0;
        ExcelDataFormatter edf = new ExcelDataFormatter();
        File newFile = null;
        try {
            String name = file.getOriginalFilename();
            String rootPath = request.getContextPath();

            //获取项目路径，创建临时文件
            //windows下
            if ("\\".equals(File.separator)) {
                newFile = new File(rootPath + "\\" + name);
            }
            //linux下
            if ("/".equals(File.separator)) {
                newFile = new File(rootPath + "/" + name);
            }
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newFile));
            stream.write(file.getBytes());
            stream.close();

            //读取Excel文件并解析
            List<MealAllowanceExcel> vos = new ExcelUtils<MealAllowanceExcel>(new MealAllowanceExcel()).readFromFile(edf, newFile);
            MealAllowanceNumModel numModel = new MealAllowanceNumModel();
            String pkCode = String.valueOf(numService.save(numModel));
            for (MealAllowanceExcel tmp : vos) {
                MealAllowanceModel model = new MealAllowanceModel();
//                pkCard;//员工卡号ID moneyAllowance  allowanceType
                model.setPk_allowance(MD5.MD5UUID());
                model.setPk_allowance_num(pkCode);
                model.setAllowance_type(tmp.getAllowanceType().indexOf("累加") > 0 ? 1 : 0);
                model.setMoney_allowance(Double.parseDouble(tmp.getMoneyAllowance()));
                model.setPk_card(tmp.getPkCard());
                status += service.save(model);
            }

        } catch (Exception e) {
            e.printStackTrace();

            //删除临时文件
            if (newFile != null) {
                newFile.delete();
            }

            if (newFile != null) {
                newFile.delete();
            }
        }
        return ResponseUtils.getSuccessAPI(status > 0 ? true : false, "", "");
    }

}*/
