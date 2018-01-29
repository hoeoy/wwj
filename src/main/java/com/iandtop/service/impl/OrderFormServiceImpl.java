package com.iandtop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iandtop.model.OrderDetailModel;
import com.iandtop.model.OrderDetailModelVO;
import com.iandtop.model.OrderFormModel;
import com.iandtop.service.OrderFormService;
import com.iandtop.dao.OrderFormDao;
import com.iandtop.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/18.
 */
@Service
@Transactional
public class OrderFormServiceImpl implements OrderFormService {
    @Autowired
    private OrderFormDao OrderFormDao;
    @Override
    public List<OrderFormModel> findformforname(OrderFormModel orderform) {
        return OrderFormDao.findformforname(orderform);
    }
    @Override
    public PageInfo<OrderFormModel> findformfornameByPage(OrderFormModel orderform, Integer pageNo, Integer pageSize) {
        pageNo = pageNo == null?1:pageNo;
        pageSize = pageSize == null?10:pageSize;
        PageHelper.startPage(pageNo, pageSize);

        List<OrderFormModel> annlist = OrderFormDao.findformforname(orderform);
        List<OrderFormModel> list = annlist;
        PageInfo<OrderFormModel> page = new PageInfo<OrderFormModel>(list);
        return page;
    }

//    @Override
//    public int insertForm(OrderDetailModelVO model) {
//        model.setFk_formcode(Integer.parseInt(MD5.MD5UUID()));
////        return OrderFormDao.insertForm(formpeople,staff_code,pk_formcode,meal_money);
//        if (OrderFormDao.insertForm(model)>0){
//            return model.getFk_formcode();
//        }
//        return 0;
//    }
//
//    @Override
//    public int insertdetail(List<OrderDetailModel> OrderDetailModel) {
//        return OrderFormDao.insertdetail(OrderDetailModel);
//    }

    @Override
    public int insertdetail(Map<String,String> Parameters) {
        return OrderFormDao.insertdetail(Parameters);
    }

    @Override
    public int insertForm(Map<String, String> parameter) {
        return OrderFormDao.insertForm(parameter);
    }

    @Override
    public int insertdetail2(Map<String, String> Parameters) {
        return OrderFormDao.insertdetail2(Parameters);
    }

    @Override
    public int insertForm2(Map<String, String> parameter) {
        return OrderFormDao.insertForm2(parameter);
    }

    @Override
    public int updateForm(OrderFormModel orderFormModel) {
        return OrderFormDao.updateForm(orderFormModel);
    }

    @Override
    public int updateForm2(OrderFormModel orderFormModel) {
        return OrderFormDao.updateForm2(orderFormModel);
    }
}
