package com.iandtop.service.impl;

import com.iandtop.dao.MealRuleDAO;
import com.iandtop.dao.PublicDAO;
import com.iandtop.model.meal.MealRuleModel;
import com.iandtop.model.meal.MerchantModel;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.service.MealRuleService;
import com.iandtop.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by lz on 2017/5/19.
 */
@Service
@Transactional
public class MealRuleServiceImpl implements MealRuleService {

    @Autowired
    private MealRuleDAO mealRuleDAO;

    @Autowired
    private PublicDAO publicDAO;

    @Override
    public List<MealRuleModel> retrieveAll() {
        return mealRuleDAO.retrieveAll();
    }

    @Override
    public List<MealRuleModel> retrieveAllWithPage(MealRuleModel vo) {
        return mealRuleDAO.retrieveAllWithPage(vo);
    }

    @Override
    public int insertByMo(MealRuleModel model) {

        List<MealRuleModel> checkResult = mealRuleDAO.retrieveByCode(model.getMeal_rule_code());
        if(checkResult != null && checkResult.size() > 0){
            return StatusCodeConstants.Fail;
        }

        Integer result = mealRuleDAO.insertByMo(model);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }

    @Override
    public int updateByPk(MealRuleModel model) {
        Integer result = mealRuleDAO.updateByPk(model);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }

    @Override
    public int deleteByPks(List<String> pks) {
        Integer result = mealRuleDAO.deleteByPks(pks);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }

    @Override
    public List<MealRuleModel> retrieveByCode(String merchant_code) {
        return mealRuleDAO.retrieveByCode(merchant_code);
    }

    @Override
    public List<MealRuleModel> retrieveByPk(String pk) {
        return mealRuleDAO.retrieveByPk(pk);
    }

    @Override
    public List<String> createMealRecordForm() {
        String sql = "";

        List<String> result = new ArrayList<String>();

        //10年的消费表
        int size = 240;
        List<String> tableNames = getTableNames(size);
        for(int i=0;i<tableNames.size();i++){
            sql = getSql(tableNames.get(i));
            publicDAO.createTable(sql);
        }
        return result;
    }

    @Override
    public int deleteMealRecordForms() {


        return 0;
    }

    private List<String> getTableNames(int size){
        List<String> tableNames = new ArrayList<String>();
        Calendar calendar=Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);//
        int currentMonth=calendar.get(Calendar.MONTH)+1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        Boolean leftMonth = (currentDay<15);
        String day = "";

        for(int i=0;i<size;i++){
            day = leftMonth?"01":"15" ;
            String tableName = "meal_record_"+currentYear+"_"+((currentMonth<10)?("0"+currentMonth):currentMonth)+"_"+day;
            leftMonth = !leftMonth;
            if(day.equals("15")){
                currentMonth++;
            }
            if(currentMonth>=13){
                currentMonth = 1;
                currentYear++;
            }
            tableNames.add(tableName);
        }
        return tableNames;
    }

    private String getSql(String tableName){
        String sql ="create table "+tableName+"("+
                "`pk_meal_record` bigint(20) NOT NULL AUTO_INCREMENT, " +
                "  `pk_staff` bigint(20) DEFAULT NULL COMMENT '人员pk', " +
                "  `pk_card` bigint(20) DEFAULT NULL COMMENT '卡pk', " +
                "  `pk_device` bigint(20) DEFAULT NULL COMMENT '设备pk'," +
                "  `card_code` varchar(20) DEFAULT NULL COMMENT '卡号', " +
                "  `staff_code` varchar(64) DEFAULT NULL COMMENT '人员编码', " +
                "  `device_code` varchar(64) DEFAULT NULL COMMENT '设备编码', " +
                "  `meal_money` double DEFAULT NULL COMMENT '消费金额', " +
                "  `meal_cash_money` double DEFAULT NULL COMMENT '现金钱包消费金额', " +
                "  `meal_allowance` double DEFAULT NULL COMMENT '补贴钱包消费金额', " +
                "  `cash_retain` double DEFAULT NULL COMMENT '现金钱包余额', " +
                "  `allowance_retain` double DEFAULT NULL COMMENT '补贴钱包余额', " +
                "  `money_retain` double DEFAULT NULL COMMENT '总余额', " +
                "  `meal_type` char(2) DEFAULT NULL COMMENT '0:正常消费,1:计次,2:冲正,3:更正收款失误,4:消费撤销,5:记账交易', " +
                "  `meal_way` char(1) DEFAULT NULL COMMENT '0:线下  1:线上', " +
                "  `device_meal_type` varchar(8) DEFAULT NULL COMMENT '消费机的消费规则：0仅现金，10仅补贴，20先现金后补贴，40先补贴后现金', " +
                "  `meal_ts` char(19) DEFAULT NULL, " +
                "  `dining_name` varchar(64) DEFAULT NULL COMMENT '餐次餐别名称', " +
                "  `dining_code` varchar(64) DEFAULT NULL COMMENT '餐次餐别编码', " +
                "  `serial` bigint(20) DEFAULT NULL, " +
                "  `memo` varchar(255) DEFAULT NULL, " +
                "  `def1` varchar(255) DEFAULT NULL, " +
                "  `def2` varchar(255) DEFAULT NULL, " +
                "  `def3` varchar(255) DEFAULT NULL, " +
                "  `def4` varchar(255) DEFAULT NULL, " +
                "  `def5` varchar(255) DEFAULT NULL, " +
                "  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, " +
                "  PRIMARY KEY (`pk_meal_record`))";
        return sql;
    }

}
