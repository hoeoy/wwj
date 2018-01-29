package com.iandtop.service.impl;/*package com.iandtop.service.impl;

import com.iandtop.dao.MealAllowanceNumDAO;
import com.iandtop.model.meal.MealAllowanceNumModel;
import com.iandtop.service.MealAllowanceNumService;
import com.iandtop.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

*//**
 * @author Klin
 * @description 描述
 * @create 2017/5/24 0024
 * @Version 1.0
 *//*
@Service
public class MealAllowanceNumServiceImpl implements MealAllowanceNumService {


    @Autowired
    protected MealAllowanceNumDAO numDAO;

    *//**
     * @author Klin
     * @date 2017/5/24 0024
     * @parm
     * @result
     * @description 查询
     *//*
    public List<MealAllowanceNumModel> retrieve(MealAllowanceNumModel model) {
        return numDAO.retrieve(model);
    }

    *//**
     * @author Klin
     * @date 2017/5/24 0024
     * @parm
     * @result
     * @description 新增
     *//*
    public int save(MealAllowanceNumModel model) {
        if (model.getPkallowancenum() == 0L) {
            model.setPkallowancenum(Long.parseLong(MD5.MD5UUID()));
        }
        if (model.getAllowancenumcode() == 0L) {
            model.setAllowancenumcode(Long.parseLong(MD5.MD5UUID()));
        }
        model.setState(0);
        model.setTs(String.valueOf(System.currentTimeMillis()));
        if (numDAO.save(model)>0)
            return (int) model.getPkallowancenum();
        return 0;
    }

    *//**
     * @author Klin
     * @date 2017/5/24 0024
     * @parm
     * @result
     * @description 删除
     *//*
    public int delData(String pkallowancenum) {
        return numDAO.delData(pkallowancenum);
    }
}*/
