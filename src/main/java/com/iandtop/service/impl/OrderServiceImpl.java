package com.iandtop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iandtop.dao.*;
import com.iandtop.model.OrderModel;
import com.iandtop.model.StaffModel;
import com.iandtop.model.card.CardModel;
import com.iandtop.model.meal.MealRecordModel;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.service.OrderService;
import com.iandtop.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/5/12.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao OrderDao;

    @Autowired
    private StaffDAO staffDAO;

    @Autowired
    private PublicDAO publicDAO;

    @Autowired
    private CardDAO cardDAO;

    @Autowired
    private MealDAO mealDAO;

    @Override
    public List<OrderModel> findgoods(int goodstype) {
        return OrderDao.findgoods(goodstype);
    }

    @Override
    public PageInfo<OrderModel> queryByPage(int goodstype, Integer pageNo, Integer pageSize) {
        pageNo = pageNo == null?1:pageNo;
        pageSize = pageSize == null?10:pageSize;
        PageHelper.startPage(pageNo, pageSize);

        List<OrderModel> annlist = OrderDao.findgoods(goodstype);
        List<OrderModel> list = annlist;
        PageInfo<OrderModel> page = new PageInfo<OrderModel>(list);
        return page;
    }

    @Override
    public List<OrderModel> findgood(OrderModel ordermodel) {
        return OrderDao.findgood(ordermodel);
    }

    @Override
    public PageInfo<OrderModel> findgoodByPage(OrderModel ordermodel, Integer pageNo, Integer pageSize) {
        pageNo = pageNo == null?1:pageNo;
        pageSize = pageSize == null?10:pageSize;
        PageHelper.startPage(pageNo, pageSize);

        List<OrderModel> annlist = OrderDao.findgood(ordermodel);
        List<OrderModel> list = annlist;
        PageInfo<OrderModel> page = new PageInfo<OrderModel>(list);
        return page;
    }

    @Override
    public List<OrderModel> gooddetails(int id) {
        List<OrderModel> find = OrderDao.gooddetails(id);
        return find;
    }

    @Override
    public List<OrderModel> find() {
        return OrderDao.find();
    }

    @Override
    public PageInfo<OrderModel> findByPage(Integer pageNo, Integer pageSize) {
        pageNo = pageNo == null?1:pageNo;
        pageSize = pageSize == null?10:pageSize;
        PageHelper.startPage(pageNo, pageSize);

        List<OrderModel> annlist = OrderDao.find();
        List<OrderModel> list = annlist;
        PageInfo<OrderModel> page = new PageInfo<OrderModel>(list);
        return page;
    }

    @Override
    public List<OrderModel> findstylegoods(int goodstype) {
        return OrderDao.findstylegoods(goodstype);
    }

    @Override
    public PageInfo<OrderModel> findstylegoodsByPage(int goodstype, Integer pageNo, Integer pageSize) {
        pageNo = pageNo == null?1:pageNo;
        pageSize = pageSize == null?10:pageSize;
        PageHelper.startPage(pageNo, pageSize);

        List<OrderModel> annlist = OrderDao.findstylegoods(goodstype);
        List<OrderModel> list = annlist;
        PageInfo<OrderModel> page = new PageInfo<OrderModel>(list);
        return page;
    }
    @Override
    public int delgood(OrderModel ordermodel) {
        return OrderDao.delgood(ordermodel);
    }


    @Override
    public int updategood(OrderModel ordermodel) {
        return OrderDao.updategood(ordermodel);
    }

    @Override
    public List<OrderModel> typefindgoods(OrderModel ordermodel) {
        return OrderDao.typefindgoods(ordermodel);
    }

    @Override
    public int payOrderOnLine(String staff_code, String passwrod, Double meal_money) {

        if(staff_code == null || staff_code.trim().length() == 0 || meal_money == null){
            return StatusCodeConstants.Param_Error;
            // return ResponseUtils.getSuccessAPI(false, "参数错误", RestOperateCode.UPDATE_DATA);
        }

        boolean isLine = passwrod == null || passwrod.trim().length() == 0 ? false : true;

        meal_money = meal_money * 100;

        //校验人员信息
        List<StaffModel> staffModels = staffDAO.retrieveByPkstaff(staff_code);
        if(staffModels.size() == 0 || staffModels == null){
            return StatusCodeConstants.Not_Found_Psn_Info;
            //return ResponseUtils.getSuccessAPI(false, "未找到人员信息", RestOperateCode.UPDATE_DATA);
        }

        if(staffModels.size() > 1){
            return StatusCodeConstants.Code_Repeat;
            // return ResponseUtils.getSuccessAPI(false, "编码重复", RestOperateCode.UPDATE_DATA);
        }

        //校验卡信息
        String pk_staff = staffModels.get(0).getPk_staff();
        String queryCardSql = "select * from card_card where pk_staff='"+pk_staff+"' and card_state in('"+CardModel.State_Normal+"','"+CardModel.State_Lost+"')";

        List<CardModel> cardModels = BaseUtils.mapToBean(CardModel.class,publicDAO.retrieveBySql(queryCardSql));
        if(cardModels == null || cardModels.size() == 0){
            return StatusCodeConstants.Not_Found_Card_Info;
            //return ResponseUtils.getSuccessAPI(false, "未找到卡片信息", RestOperateCode.UPDATE_DATA);
        }

        if(cardModels.size() > 1){
            return StatusCodeConstants.Psn_Have_Multiple_Card;
            //return ResponseUtils.getSuccessAPI(false, "此人在系统中有多张", RestOperateCode.UPDATE_DATA);
        }


        CardModel cardModel = cardModels.get(0);

        //校验卡状态
        if(!cardModel.getCard_state().equals(CardModel.State_Normal)){
            return StatusCodeConstants.Card_Not_Normal_State;
            // return ResponseUtils.getSuccessAPI(false, "卡片不是正常状态", RestOperateCode.UPDATE_DATA);
        }

        if(isLine){
            //校验支付密码
            if(!passwrod.equals(cardModel.getPassword())){
                return StatusCodeConstants.Pwd_Error;
                //return ResponseUtils.getSuccessAPI(false, "密码错误", RestOperateCode.UPDATE_DATA);
            }
        }

        //检验余额是否充足(先扣补贴后扣现金)
        //现金：money_cash
        Double money_cash = cardModel.getMoney_cash();
        //补贴：money_allowance
        Double money_allowance = cardModel.getMoney_allowance();
        if(meal_money > (money_cash + money_allowance)){
           return StatusCodeConstants.Money_Not_Enough;
            // return ResponseUtils.getSuccessAPI(false, "余额不足", RestOperateCode.UPDATE_DATA);
        }

        //保存消费记录
        MealRecordModel mealRecordModel = new MealRecordModel();
        mealRecordModel.setTable_name(mealRecordModel.getTableName());
        mealRecordModel.setPk_staff(staffModels.get(0).getPk_staff());
        mealRecordModel.setPk_card(cardModel.getPk_card());
        mealRecordModel.setCard_code(cardModel.getCard_code());
        mealRecordModel.setStaff_code(staff_code);
        mealRecordModel.setMeal_money(meal_money);

        String device_code="00";
        MealRecordModel Model=OrderDao.mealRecordModel(device_code);
        String type = Model.getDevice_meal_type();
        mealRecordModel.setDevice_meal_type(type);
        mealRecordModel.setDevice_code(Model.getDevice_code());
        mealRecordModel.setPk_device(Model.getPk_device());
        //先补贴后现金
        if(Model.getDevice_meal_type().equals("40")){
        if(meal_money > money_allowance){
            //补贴不足
            //现金钱包消费赋值=要消费的数-补贴钱包余额
            double sum1=meal_money - money_allowance;
            mealRecordModel.setMeal_cash_money(sum1);
            //补贴钱包消费赋值=补贴钱包余额
            mealRecordModel.setMeal_allowance(money_allowance);
            //补贴钱包赋值为0
            money_allowance = 0.0;
            //现金钱包余额=现金钱包余额-现金消费
            money_cash = money_cash + money_allowance - sum1;
            //现金钱包余额赋值
            mealRecordModel.setCash_retain(money_cash);
            //补贴钱包赋值
            mealRecordModel.setAllowance_retain(money_allowance);

        }else{
            //补贴充足
            money_allowance = money_allowance - meal_money;
            //现金消费赋值
            mealRecordModel.setMeal_cash_money(0.0);
            //补贴钱包赋值
            mealRecordModel.setMeal_allowance(meal_money);
            //现金钱包余额赋值
            mealRecordModel.setCash_retain(money_cash);
            //补贴钱包余额赋值
            mealRecordModel.setAllowance_retain(money_allowance);
        }
        }
        //先现金后补贴
        if(Model.getDevice_meal_type().equals("20")){
        if(meal_money > money_cash){
            //现金不足
            //补贴钱包消费赋值=要消费的数-现金钱包余额
            double sum1=meal_money - money_cash;
            //补贴钱包消费赋值
            mealRecordModel.setMeal_allowance(sum1);
            //现金钱包消费赋值=现金钱包余额
            mealRecordModel.setMeal_cash_money(money_cash);
            //现金钱包赋值为0
            money_cash = 0.0;
            //补贴钱包余额=补贴钱包余额-补贴消费
            money_allowance = money_allowance - sum1;
            //现金钱包余额赋值
            mealRecordModel.setCash_retain(money_cash);
            //补贴钱包赋值
            mealRecordModel.setAllowance_retain(money_allowance);

        }else{
            //现金充足1
            money_cash = money_cash - meal_money;
            //补贴消费赋值
            mealRecordModel.setMeal_allowance(0.0);
            //现金消费赋值
            mealRecordModel.setMeal_cash_money(meal_money);
            //现金钱包余额赋值
            mealRecordModel.setCash_retain(money_cash);
            //补贴钱包余额赋值
            mealRecordModel.setAllowance_retain(money_allowance);
        }
        }
        //仅现金
        if(Model.getDevice_meal_type().equals("0")){

        if(meal_money < money_cash){
            //现金充足2
            money_cash = money_cash - meal_money;
            //补贴消费赋值
            mealRecordModel.setMeal_allowance(0.0);
            //现金消费赋值
            mealRecordModel.setMeal_cash_money(meal_money);
            //现金钱包余额赋值
            mealRecordModel.setCash_retain(money_cash);
            //补贴钱包余额赋值
            mealRecordModel.setAllowance_retain(money_allowance);
        }else {
            return StatusCodeConstants.Money_Not_Enough;
        }
        }
        //仅补贴
        if(Model.getDevice_meal_type().equals("10")){
        if(meal_money < money_allowance){
            //补贴充足3
            money_allowance = money_allowance - meal_money;
            //现金消费赋值
            mealRecordModel.setMeal_cash_money(0.0);
            //补贴钱包赋值
            mealRecordModel.setMeal_allowance(meal_money);
            //现金钱包余额赋值
            mealRecordModel.setCash_retain(money_cash);
            //补贴钱包余额赋值
            mealRecordModel.setAllowance_retain(money_allowance);
        }else {
            return StatusCodeConstants.Money_Not_Enough;
        }
        }
        mealRecordModel.setMoney_retain(money_cash + money_allowance);
        mealRecordModel.setMeal_type(MealRecordModel.Meal_Type_Normal);

        if(!isLine){
            mealRecordModel.setMeal_way(MealRecordModel.Meal_Way_Line);
        }else{
            mealRecordModel.setMeal_way(MealRecordModel.Meal_Way_OnLine);
        }


        mealRecordModel.setMeal_ts(DateUtils.currentDatetime());

        Integer result;
        result = mealDAO.insertByVO(mealRecordModel);
        if(result < 1){
            return StatusCodeConstants.Sys_Error;
            //return ResponseUtils.getSuccessAPI(false, "系统内部错误", RestOperateCode.UPDATE_DATA);
        }

        //更新卡表数据
        cardModel.setMoney_cash(money_cash);
        cardModel.setMoney_allowance(money_allowance);
        cardModel.setSerial(cardModel.getSerial()+1);


        result = cardDAO.updateByVO(cardModel);
        if(result < 1){
            return StatusCodeConstants.Sys_Error;
            // return ResponseUtils.getSuccessAPI(false, "系统内部错误", RestOperateCode.UPDATE_DATA);
        }

        //返回100 即为成功
        return 100;
    }

    @Override
    public int payOrderOffline(String staff_code, Double meal_money,String pk_device) {
        if(staff_code == null || staff_code.trim().length() == 0 || meal_money == null){
            return StatusCodeConstants.Param_Error;
            // return ResponseUtils.getSuccessAPI(false, "参数错误", RestOperateCode.UPDATE_DATA);
        }


        meal_money = meal_money * 100;

        //校验人员信息
        List<StaffModel> staffModels = staffDAO.retrieveByPkstaff(staff_code);


//        //校验卡信息(用于校验卡余额是否足够)
        String pk_staff = staffModels.get(0).getPk_staff();
        String queryCardSql = "select * from card_card where pk_staff='"+pk_staff+"' and card_state in('"+CardModel.State_Normal+"','"+CardModel.State_Lost+"')";

        List<CardModel> cardModels = BaseUtils.mapToBean(CardModel.class,publicDAO.retrieveBySql(queryCardSql));
        CardModel cardModel = cardModels.get(0);


        //检验余额是否充足(先扣补贴后扣现金)
        //现金：money_cash
        Double money_cash = cardModel.getMoney_cash();
        //补贴：money_allowance
        Double money_allowance = cardModel.getMoney_allowance();
        if(meal_money > (money_cash + money_allowance)){
            return StatusCodeConstants.Money_Not_Enough;
            // return ResponseUtils.getSuccessAPI(false, "余额不足", RestOperateCode.UPDATE_DATA);
        }

        //保存消费记录
        MealRecordModel mealRecordModel = new MealRecordModel();
        mealRecordModel.setTable_name(mealRecordModel.getTableName());
        mealRecordModel.setPk_staff(staffModels.get(0).getPk_staff());
        mealRecordModel.setPk_card(cardModel.getPk_card());
        mealRecordModel.setCard_code(cardModel.getCard_code());
        mealRecordModel.setStaff_code(staff_code);
        mealRecordModel.setMeal_money(meal_money);

        //String device_code="00";
        MealRecordModel Model=OrderDao.findDeviceCode(pk_device);
        String type = Model.getDevice_meal_type();
        mealRecordModel.setDevice_meal_type(type);
        mealRecordModel.setDevice_code(Model.getDevice_code());
        mealRecordModel.setPk_device(Model.getPk_device());
        //先补贴后现金
        if(Model.getDevice_meal_type().equals("40")){
            if(meal_money > money_allowance){
                //补贴不足
                //现金钱包消费赋值=要消费的数-补贴钱包余额
                double sum1=meal_money - money_allowance;
                mealRecordModel.setMeal_cash_money(sum1);
                //补贴钱包消费赋值=补贴钱包余额
                mealRecordModel.setMeal_allowance(money_allowance);
                //补贴钱包赋值为0
                money_allowance = 0.0;
                //现金钱包余额=现金钱包余额-现金消费
                money_cash = money_cash + money_allowance - sum1;
                //现金钱包余额赋值
                mealRecordModel.setCash_retain(money_cash);
                //补贴钱包赋值
                mealRecordModel.setAllowance_retain(money_allowance);

            }else{
                //补贴充足
                money_allowance = money_allowance - meal_money;
                //现金消费赋值
                mealRecordModel.setMeal_cash_money(0.0);
                //补贴钱包赋值
                mealRecordModel.setMeal_allowance(meal_money);
                //现金钱包余额赋值
                mealRecordModel.setCash_retain(money_cash);
                //补贴钱包余额赋值
                mealRecordModel.setAllowance_retain(money_allowance);
            }
        }
        //先现金后补贴
        if(Model.getDevice_meal_type().equals("20")){
            if(meal_money > money_cash){
                //现金不足
                //补贴钱包消费赋值=要消费的数-现金钱包余额
                double sum1=meal_money - money_cash;
                //补贴钱包消费赋值
                mealRecordModel.setMeal_allowance(sum1);
                //现金钱包消费赋值=现金钱包余额
                mealRecordModel.setMeal_cash_money(money_cash);
                //现金钱包赋值为0
                money_cash = 0.0;
                //补贴钱包余额=补贴钱包余额-补贴消费
                money_allowance = money_allowance - sum1;
                //现金钱包余额赋值
                mealRecordModel.setCash_retain(money_cash);
                //补贴钱包赋值
                mealRecordModel.setAllowance_retain(money_allowance);

            }else{
                //现金充足1
                money_cash = money_cash - meal_money;
                //补贴消费赋值
                mealRecordModel.setMeal_allowance(0.0);
                //现金消费赋值
                mealRecordModel.setMeal_cash_money(meal_money);
                //现金钱包余额赋值
                mealRecordModel.setCash_retain(money_cash);
                //补贴钱包余额赋值
                mealRecordModel.setAllowance_retain(money_allowance);
            }
        }
        //仅现金
        if(Model.getDevice_meal_type().equals("0")){

            if(meal_money < money_cash){
                //现金充足2
                money_cash = money_cash - meal_money;
                //补贴消费赋值
                mealRecordModel.setMeal_allowance(0.0);
                //现金消费赋值
                mealRecordModel.setMeal_cash_money(meal_money);
                //现金钱包余额赋值
                mealRecordModel.setCash_retain(money_cash);
                //补贴钱包余额赋值
                mealRecordModel.setAllowance_retain(money_allowance);
            }else {
                return StatusCodeConstants.Money_Not_Enough;
            }
        }
        //仅补贴
        if(Model.getDevice_meal_type().equals("10")){
            if(meal_money < money_allowance){
                //补贴充足3
                money_allowance = money_allowance - meal_money;
                //现金消费赋值
                mealRecordModel.setMeal_cash_money(0.0);
                //补贴钱包赋值
                mealRecordModel.setMeal_allowance(meal_money);
                //现金钱包余额赋值
                mealRecordModel.setCash_retain(money_cash);
                //补贴钱包余额赋值
                mealRecordModel.setAllowance_retain(money_allowance);
            }else {
                return StatusCodeConstants.Money_Not_Enough;
            }
        }
        mealRecordModel.setMoney_retain(money_cash + money_allowance);
        mealRecordModel.setMeal_type(MealRecordModel.Meal_Type_Normal);
        mealRecordModel.setMeal_way(MealRecordModel.Meal_Way_Line);

        mealRecordModel.setMeal_ts(DateUtils.currentDatetime());

        Integer result;
        result = mealDAO.insertByVO(mealRecordModel);
        if(result < 1){
            return StatusCodeConstants.Sys_Error;
            //return ResponseUtils.getSuccessAPI(false, "系统内部错误", RestOperateCode.UPDATE_DATA);
        }

        //更新卡表数据
        cardModel.setMoney_cash(money_cash);
        cardModel.setMoney_allowance(money_allowance);
        cardModel.setSerial(cardModel.getSerial()+1);


        result = cardDAO.updateByVO(cardModel);
        if(result < 1){
            return StatusCodeConstants.Sys_Error;
            // return ResponseUtils.getSuccessAPI(false, "系统内部错误", RestOperateCode.UPDATE_DATA);
        }

        //返回100 即为成功
        return 100;
    }


}
