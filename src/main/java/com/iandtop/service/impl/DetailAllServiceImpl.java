package com.iandtop.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iandtop.dao.DetailAllDAO;
import com.iandtop.dao.PublicDAO;
import com.iandtop.model.DeptModelVO;
import com.iandtop.model.card.CardChangeRecordModel;
import com.iandtop.model.card.CardChargeRecordModel;
import com.iandtop.model.card.CardModel;
import com.iandtop.model.card.CardRefundRecordModel;
import com.iandtop.model.meal.MealRecordModel;
import com.iandtop.model.system.UserModel;
import com.iandtop.service.DetailAllService;
import com.iandtop.utils.BaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xss on 2017/5/22.
 */
@Service
@Transactional
public class DetailAllServiceImpl implements DetailAllService
{
    @Autowired
    private DetailAllDAO detailAllDAO;
    @Autowired
    private PublicDAO publicDAO;

    @Override
    public List<UserModel> queryUserAll() {
//        String queryUser = "select pk_user,user_code,user_name from sm_user where pk_staff is NULL";
//        List<UserModel> userModels =
//                BaseUtils.mapToBean(UserModel.class,publicDAO.retrieveBySql(queryUser));
        return detailAllDAO.queryUserAll();
    }

    @Override
    public List<DeptModelVO> queryDeptAll(){
        return detailAllDAO.queryDeptAll();
    }

    @Override
    public PageInfo<CardChargeRecordModel> queryChargeDetail(
            String staff_code, String staff_name,
            String user_name, String card_code,
            String charge_type, String start_ts,
            String end_ts,String dept_code, Integer pageNo, Integer pageSize) {
            pageNo = pageNo == null?1:pageNo;
            pageSize = pageSize == null?10:pageSize;
            PageHelper.startPage(pageNo, pageSize);

            List<CardChargeRecordModel> annlist = detailAllDAO.queryChargeDetail(staff_code, staff_name, user_name, card_code, charge_type, start_ts, end_ts,dept_code);
            List<CardChargeRecordModel> list = annlist;
            PageInfo<CardChargeRecordModel> page = new PageInfo<CardChargeRecordModel>(list);
            return page;

        //return detailAllDAO.queryChargeDetail(staff_code, staff_name, user_name, card_code, charge_type, start_ts, end_ts,dept_code);
    }

    @Override
    public PageInfo<MealRecordModel> queryConsumeDetail(
            String staff_code, String staff_name, String card_code,String device_code,String dept_code,String start_ts, String end_ts, Integer pageNo, Integer pageSize) throws ParseException {
        List<String> tableNames = gainTableName(start_ts,end_ts);//所有需要查的半月表
        String queryMeal = "select " +
                "c.card_code,c.serial,c.meal_ts,c.meal_money/100 meal_money,c.meal_type," +
                "c.meal_allowance/100 meal_allowance,c.meal_cash_money/100 meal_cash_money,c.cash_retain/100 cash_retain," +
                "c.allowance_retain/100 allowance_retain,c.money_retain/100 money_retain," +
                "s.staff_code,s.staff_name,d.department_name,m.device_name from (";
        for(int i = 0;i<tableNames.size();i++){
            queryMeal += "select pk_device,pk_staff,meal_type,card_code,serial,meal_ts,meal_money," +
                    "meal_allowance,meal_cash_money,cash_retain,allowance_retain,money_retain from "+tableNames.get(i);
            if(i<tableNames.size()-1){
                queryMeal+=" UNION ";
            }
        }
        queryMeal+=") c left join db_staff s on c.pk_staff = s.pk_staff " +
                "left join db_department d on s.def1 = d.department_code " +
                "left join meal_device m on c.pk_device = m.pk_device " +
                "where 1=1 ";
        if(staff_code!=null && staff_code.length()>0){
            queryMeal+="and s.staff_code = '"+staff_code+"' ";
        }
        if(staff_name!=null && staff_name.length()>0){
            queryMeal+="and s.staff_name = '"+staff_name+"' ";
        }
        if(card_code!=null && card_code.length()>0){
            queryMeal+="and c.card_code = '"+card_code+"' ";
        }
        if(device_code!=null && device_code.length()>0){
            queryMeal+="and m.device_code = '"+device_code+"' ";
        }
        if(dept_code!=null && dept_code.length()>0){
            queryMeal+="and d.department_code like '"+dept_code+"%' ";
        }
        if(start_ts!=null && start_ts.length()>0){
            queryMeal+="and c.meal_ts >= '"+start_ts+"' ";
        }
        if(end_ts!=null && end_ts.length()>0){
            queryMeal+="and c.meal_ts <= '"+end_ts+"' ";
        }
        queryMeal+= "order by c.meal_ts desc";

        String strSQL = "select count(0) from (";
        for(int i = 0;i<tableNames.size();i++){
            strSQL += "select pk_device,pk_staff,meal_type,card_code,serial,meal_ts,meal_money," +
                    "meal_allowance,meal_cash_money,cash_retain,allowance_retain,money_retain from "+tableNames.get(i) ;
            if(i<tableNames.size()-1){
                strSQL+=" UNION ";
            }
        }
        strSQL+=") c left join db_staff s on c.pk_staff = s.pk_staff " +
                "left join db_department d on s.def1 = d.department_code " +
                "left join meal_device m on c.pk_device = m.pk_device " +
                "where 1=1 ";
        if(staff_code!=null && staff_code.length()>0){
            strSQL+="and s.staff_code = '"+staff_code+"' ";
        }
        if(staff_name!=null && staff_name.length()>0){
            strSQL+="and s.staff_name = '"+staff_name+"' ";
        }
        if(card_code!=null && card_code.length()>0){
            strSQL+="and c.card_code = '"+card_code+"' ";
        }
        if(device_code!=null && device_code.length()>0){
            strSQL+="and m.device_code = '"+device_code+"' ";
        }
        if(dept_code!=null && dept_code.length()>0){
            strSQL+="and d.department_code like '"+dept_code+"%' ";
        }
        if(start_ts!=null && start_ts.length()>0){
            strSQL+=" and c.meal_ts >= '"+start_ts+"' ";
        }
        if(end_ts!=null && end_ts.length()>0){
            strSQL+=" and c.meal_ts <= '"+end_ts+"' ";
        }

        int listTotal = publicDAO.intfindBysql(strSQL);
        pageNo = pageNo == null?1:pageNo;
        pageSize = pageSize == null?10:pageSize;
        PageHelper.startPage(pageNo, pageSize);
        List<MealRecordModel> list = BaseUtils.mapToBean(MealRecordModel.class,publicDAO.retrieveBySql(queryMeal));

        PageInfo<MealRecordModel> pages =new PageInfo<MealRecordModel>();
        pages.setTotal(listTotal);
        pages.setPageNum(pageNo);
        pages.setPageSize(pageSize);
        pages.setList(list);
        pages.setSize(list.size());

        return pages;
       // return mealRecordModels;
    }

    @Override
    public List<CardRefundRecordModel> queryRefundDetail(
            String staff_code, String staff_name, String user_name, String card_code, String charge_type, String start_ts, String end_ts, String dept_code) {
        return detailAllDAO.queryRefundDetail(staff_code, staff_name, user_name, card_code, charge_type, start_ts, end_ts,dept_code);
    }

    @Override
    public List<CardChangeRecordModel> queryReturnCardDetail(
            String staff_code, String staff_name, String user_name, String card_code, String charge_type, String start_ts, String end_ts, String dept_code) {
        return detailAllDAO.queryReturnCardDetail(staff_code, staff_name, user_name, card_code, charge_type, start_ts, end_ts,dept_code);
    }

    @Override
    public List<CardChangeRecordModel> queryPatchCardDetail(
            String staff_code, String staff_name, String user_name, String card_code, String charge_type, String start_ts, String end_ts,String dept_code) {
        return detailAllDAO.queryPatchCardDetail(staff_code, staff_name, user_name, card_code, charge_type, start_ts, end_ts,dept_code);
    }

    @Override
    public PageInfo<CardModel> queryPsnBalance(String staff_code, String staff_name, String card_code, String dept_code, Integer pageNo, Integer pageSize) {
        String queryPsnBalance = "select c.card_code,s.staff_code,s.staff_name,d.department_name," +
                "c.money_cash/100 money_cash,c.money_allowance/100 money_allowance,c.card_state,(c.money_allowance+c.money_cash)/100 money_sum " +
                "from card_card c " +
                "left join db_staff s on c.pk_staff = s.pk_staff " +
                "left join db_department d on s.department_code = d.department_code "+
                "where 1=1 and c.card_state!=40 ";
        if(staff_code!=null && staff_code.length()>0){
            queryPsnBalance+="and s.staff_code = '"+staff_code+"' ";
        }
        if(staff_name!=null && staff_name.length()>0){
            queryPsnBalance+="and s.staff_name = '"+staff_name+"' ";
        }
        if(card_code!=null && card_code.length()>0){
            queryPsnBalance+="and c.card_code = '"+card_code+"' ";
        }
        if(dept_code!=null && dept_code.length()>0){
            queryPsnBalance+="and s.department_code like '"+dept_code+"%' ";
        }
        //order by a.staff_code asc
        queryPsnBalance += " order by s.staff_code asc ";

        //查询总行数sql
        String sql =
                "select count(0) from card_card c " +
                "left join db_staff s on c.pk_staff = s.pk_staff " +
                "left join db_department d on s.department_code = d.department_code "+
                "where 1=1 and c.card_state!=40 ";
        if(staff_code!=null && staff_code.length()>0){
            sql+="and s.staff_code = '"+staff_code+"' ";
        }
        if(staff_name!=null && staff_name.length()>0){
            sql+="and s.staff_name = '"+staff_name+"' ";
        }
        if(card_code!=null && card_code.length()>0){
            sql+="and c.card_code = '"+card_code+"' ";
        }
        if(dept_code!=null && dept_code.length()>0){
            sql+="and s.department_code like '"+dept_code+"%' ";
        }
            sql += " order by s.staff_code asc ";
        int listTotal = publicDAO.intfindBysql(sql);
        pageNo = pageNo == null?1:pageNo;
        pageSize = pageSize == null?10:pageSize;
        PageHelper.startPage(pageNo, pageSize);
        List<CardModel> cardModelList =
                BaseUtils.mapToBean(CardModel.class,publicDAO.retrieveBySql(queryPsnBalance));


        PageInfo<CardModel> pages =new PageInfo<CardModel>();
        pages.setTotal(listTotal);
        pages.setPageNum(pageNo);
        pages.setPageSize(pageSize);
        pages.setList(cardModelList);
        pages.setSize(cardModelList.size());

        return pages;
       // return cardModelList;
    }

    public List<String> gainTableName(String start_ts, String end_ts){

        List<String> tableNames = new ArrayList<String>();
        String start_year = start_ts.substring(0,4);
        String start_month = start_ts.substring(5,7);
        String start_day = start_ts.substring(8,10);
        String end_year = end_ts.substring(0,4);
        String end_month = end_ts.substring(5,7);
        String end_day = end_ts.substring(8,10);
        start_day = Integer.parseInt(start_day) < 15 ? "01" : "15";
        end_day = Integer.parseInt(end_day) < 15 ? "01" : "15";

        String start_table = "meal_record_" + start_year + "_" + start_month + "_" + start_day;
        String end_table = "meal_record_" + end_year + "_" + end_month + "_" + end_day;
        tableNames.add(start_table);
        String table_name = "";
        while(!table_name.equals(end_table) && !start_table.equals(end_table)){
            start_year = change(start_year,start_month,start_day).get(0);
            start_month = patch(change(start_year,start_month,start_day).get(1));
            start_day = change(start_year,start_month,start_day).get(2);
            table_name = "meal_record_" + start_year + "_" + start_month + "_" + start_day;
            String name = new String();
            name = table_name;
            tableNames.add(name);
        }
        return tableNames;
    }

    private static List<String> change(String year, String month, String day){
        List<String> md = new ArrayList<String>();
        if("15".equals(day)){
            day = "01";
            if("12".equals(month)){
                year = String.valueOf(Integer.valueOf(year)+1);
                month = "1";
            }else{
                month = String.valueOf(Integer.valueOf(month)+1);
            }
        }else {
            day = "15";
        }
        md.add(year);
        md.add(month);
        md.add(day);
        return md;
    }
    private static String patch(String month){
        if(month.length()==1){
            month = "0"+month;
        }
        return month;
    }
}











