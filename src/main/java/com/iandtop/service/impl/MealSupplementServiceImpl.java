package com.iandtop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iandtop.dao.*;
import com.iandtop.model.card.CardModel;
import com.iandtop.model.meal.MealRecordModel;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.service.MealSupplementService;
import com.iandtop.utils.BaseUtils;
import com.iandtop.utils.BigDecimalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@Transactional
public class MealSupplementServiceImpl implements MealSupplementService {
    @Autowired
    MealSupplementDao dao;
    @Autowired
    PublicDAO publicDAO;
    @Autowired
    private CardDAO cardDAO;
    @Autowired
    private MealDAO mealDAO;

    @Override
    public PageInfo<CardModel> findCardByPage(CardModel model, Integer pageNo, Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? 10 : pageSize;
        PageHelper.startPage(pageNo, pageSize);

        List<CardModel> annlist = dao.findCardByPage(model);
        List<CardModel> list = annlist;
        PageInfo<CardModel> page = new PageInfo<CardModel>(list);
        return page;
    }

    @Override
    public int supplement(MealRecordModel model) {
        //检验余额是否充足(先扣补贴后扣现金)
        double meal_money = model.getMeal_money();
        meal_money = meal_money*100;
        System.out.println(meal_money);

        //现金：money_cash
        double money_cash = model.getCash_retain();
        money_cash = money_cash*100;
        System.out.println(money_cash);

        //补贴：money_allowance
        double money_allowance = model.getAllowance_retain();
        money_allowance = money_allowance*100;
        System.out.println(money_allowance);
        //money_cash+money_allowance;
        double sum =  BigDecimalUtil.add(money_cash,money_allowance);
        double jieguo = BigDecimalUtil.sub(sum,meal_money);
        if (jieguo < 0) {
            return 13;
        }

        //保存消费记录
        MealRecordModel mealRecordModel = new MealRecordModel();
        mealRecordModel.setTable_name(gainTableName(model.getMeal_ts()));
        mealRecordModel.setPk_staff(model.getPk_staff());
        mealRecordModel.setPk_card(model.getPk_card());
        mealRecordModel.setCard_code(model.getCard_code());
        mealRecordModel.setStaff_code(model.getStaff_code());
        mealRecordModel.setMeal_money(meal_money);
        mealRecordModel.setDevice_meal_type("40");
        mealRecordModel.setDevice_code(model.getDevice_code());
        mealRecordModel.setPk_device(model.getPk_device());
        mealRecordModel.setMeal_type("5");
        mealRecordModel.setMeal_way("0");
        mealRecordModel.setMeal_ts(model.getMeal_ts());
        mealRecordModel.setDining_code(model.getDining_code());
        //先补贴后现金
        if (meal_money > money_allowance) {
            //补贴不足
            //现金钱包消费赋值=要消费的数-补贴钱包余额
            // sum1 = meal_money - money_allowance;
            double sum1 = BigDecimalUtil.sub(meal_money,money_allowance);

            mealRecordModel.setMeal_cash_money(sum1);
            //补贴钱包消费赋值=补贴钱包余额
            mealRecordModel.setMeal_allowance(money_allowance);
            //补贴钱包赋值为0
            money_allowance = 0.0;
            //现金钱包余额=现金钱包余额-现金消费
            //money_cash = money_cash + money_allowance - sum1;
            double money_cash1 =  BigDecimalUtil.add(money_cash,money_allowance);
             money_cash = BigDecimalUtil.sub(money_cash1,sum1);
            //现金钱包余额赋值
            mealRecordModel.setCash_retain(money_cash);
            //补贴钱包赋值
            mealRecordModel.setAllowance_retain(money_allowance);
            //double money_retain = BigDecimalUtil.add(money_cash,money_allowance);
            mealRecordModel.setMoney_retain(money_cash1);


        } else {
            //补贴充足
            //money_allowance = money_allowance - meal_money;
            money_allowance = BigDecimalUtil.sub(money_allowance,meal_money);

            //现金消费赋值
            mealRecordModel.setMeal_cash_money(0.0);
            //补贴钱包赋值
            mealRecordModel.setMeal_allowance(meal_money);
            //现金钱包余额赋值
            mealRecordModel.setCash_retain(money_cash);
            //补贴钱包余额赋值
            mealRecordModel.setAllowance_retain(money_allowance);
            double money_retain = BigDecimalUtil.add(money_cash,money_allowance);
            mealRecordModel.setMoney_retain(money_retain);
        }
        Integer result;
        result = mealDAO.insertByVO(mealRecordModel);
        if (result < 1) {
            return StatusCodeConstants.Sys_Error;
            //return ResponseUtils.getSuccessAPI(false, "系统内部错误", RestOperateCode.UPDATE_DATA);
        }
        //校验卡信息
        String pk_staff = model.getPk_staff();
        String queryCardSql = "select * from card_card where pk_staff='" + pk_staff + "' and card_state in('" + CardModel.State_Normal + "','" + CardModel.State_Lost + "')";

        List<CardModel> cardModels = BaseUtils.mapToBean(CardModel.class, publicDAO.retrieveBySql(queryCardSql));
        if (cardModels == null || cardModels.size() == 0) {
            return 5;
            //return ResponseUtils.getSuccessAPI(false, "未找到卡片信息", RestOperateCode.UPDATE_DATA);
        }

        if (cardModels.size() > 1) {
            return 10;
            //return ResponseUtils.getSuccessAPI(false, "此人在系统中有多张", RestOperateCode.UPDATE_DATA);
        }

        //更新卡表数据
        CardModel cardModel = new CardModel();
        cardModel.setMoney_cash(money_cash);
        cardModel.setMoney_allowance(money_allowance);
        cardModel.setSerial(cardModels.get(0).getSerial() + 1);
        cardModel.setPk_card(cardModels.get(0).getPk_card());


        result = cardDAO.updateByVO(cardModel);
        if (result < 1) {
            return 0;
            // return ResponseUtils.getSuccessAPI(false, "系统内部错误", RestOperateCode.UPDATE_DATA);
        }
        return 200;

    }

    public String gainTableName(String start_ts) {
        String tableName = "";
        String start_year = start_ts.substring(0, 4);
        String start_month = start_ts.substring(5, 7);
        String start_day = start_ts.substring(8, 10);
        start_day = Integer.parseInt(start_day) < 15 ? "01" : "15";
        String start_table = "meal_record_" + start_year + "_" + start_month + "_" + start_day;
        tableName = start_table;
        return tableName;
    }
}
