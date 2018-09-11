package com.iandtop.service.impl;

import com.iandtop.dao.PublicDAO;
import com.iandtop.dao.TenantPosDAO;
import com.iandtop.model.device.DeviceModel;
import com.iandtop.model.form.MerchantPOSMondel;
import com.iandtop.model.form.MerchantPos;
import com.iandtop.model.meal.MerchantModel;
import com.iandtop.service.DetailAllService;
import com.iandtop.service.TenantPosService;
import com.iandtop.utils.BaseUtils;
import com.iandtop.utils.BigDecimalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by xss on 2017-05-24.
 */
@Service
@Transactional
public class TenantPosImpl implements TenantPosService {

    @Autowired
    TenantPosDAO tenantPosDAO;

    @Autowired
    private PublicDAO publicDAO;

    @Autowired
    private DetailAllService detailAllService;

    @Override
    public List<MerchantModel> queryMerchant() {
        return tenantPosDAO.queryMerchant();
    }

    @Override
    public List<DeviceModel> queryDevice(String pk_merchant) {
        return tenantPosDAO.queryDevice(pk_merchant);
    }

    @Override
    public List<MerchantPOSMondel> queryMerchantDay(String pk_merchant, String pk_device, String start_ts, String end_ts) {
        String queryMerchantDay = "select meal_ts_day sum_date,merchant_code,merchant_name,device_code,device_name , " +
                "sum(r.dining_code = 0) breakfast_num, " +
                "sum(CASE r.dining_code WHEN 0 THEN meal_money ELSE 0 END)/100  'breakfast_money', " +
                "sum(r.dining_code = 1) lunch_num, " +
                "sum(CASE r.dining_code WHEN 1 THEN meal_money ELSE 0 END)/100  'lunch_money', " +
                "sum(r.dining_code = 2) dinner_num, " +
                "sum(CASE r.dining_code WHEN 2 THEN meal_money ELSE 0 END)/100  'dinner_money', " +
                "sum(r.dining_code = 3) night_num, " +
                "sum(CASE r.dining_code WHEN 3 THEN meal_money ELSE 0 END)/100  'night_money',  " +
                "sum(r.dining_code = 4) else_num, " +
                "sum(CASE r.dining_code WHEN 4 THEN meal_money ELSE 0 END)/100  'else_money', " +
                "(sum(r.dining_code = 0) + sum(r.dining_code = 1)+ sum(r.dining_code = 2)+ sum(r.dining_code = 3)+ sum(r.dining_code = 4)) sum_num, " +
                "(sum(CASE r.dining_code WHEN 0 THEN meal_money ELSE 0 END)+ " +
                "sum(CASE r.dining_code WHEN 1 THEN meal_money ELSE 0 END)+ " +
                "sum(CASE r.dining_code WHEN 2 THEN meal_money ELSE 0 END)+ " +
                "sum(CASE r.dining_code WHEN 3 THEN meal_money ELSE 0 END)+ " +
                "sum(CASE r.dining_code WHEN 4 THEN meal_money ELSE 0 END))/100 'sum_money' " +
                "from meal_record r where 1=1 ";

        if (pk_device != null && pk_device.length() > 0) {
            queryMerchantDay += "and r.pk_device = '" + pk_device + "' ";
        }
        if (pk_merchant != null && pk_merchant.length() > 0) {
            queryMerchantDay += "and r.pk_merchant = '" + pk_merchant + "' ";
        }
        if (start_ts != null && start_ts.length() > 0) {
            queryMerchantDay += "and meal_ts_day >= '" + start_ts + "' ";
        }
        if (end_ts != null && end_ts.length() > 0) {
            queryMerchantDay += "and meal_ts_day <= '" + end_ts + "' ";
        }
        queryMerchantDay += "group by meal_ts_day";
        List<MerchantPOSMondel> merchantPOSMondels =
                BaseUtils.mapToBean(MerchantPOSMondel.class, publicDAO.retrieveBySql(queryMerchantDay));
        return merchantPOSMondels;
    }

    @Override
    public List<MerchantPOSMondel> queryMerchantSum(String pk_merchant, String pk_device, String start_ts, String end_ts) {
        String queryMerchantDay = "select " +
                // "meal_ts_day sum_date," +
                "merchant_code,merchant_name,device_code,device_name , " +
                "sum(r.dining_code = 0) breakfast_num, " +
                "sum(CASE r.dining_code WHEN 0 THEN meal_money ELSE 0 END)/100  'breakfast_money', " +
                "sum(r.dining_code = 1) lunch_num, " +
                "sum(CASE r.dining_code WHEN 1 THEN meal_money ELSE 0 END)/100  'lunch_money', " +
                "sum(r.dining_code = 2) dinner_num, " +
                "sum(CASE r.dining_code WHEN 2 THEN meal_money ELSE 0 END)/100  'dinner_money', " +
                "sum(r.dining_code = 3) night_num, " +
                "sum(CASE r.dining_code WHEN 3 THEN meal_money ELSE 0 END)/100  'night_money',  " +
                "sum(r.dining_code = 4) else_num, " +
                "sum(CASE r.dining_code WHEN 4 THEN meal_money ELSE 0 END)/100  'else_money', " +
                "(sum(r.dining_code = 0) + sum(r.dining_code = 1)+ sum(r.dining_code = 2)+ sum(r.dining_code = 3)+ sum(r.dining_code = 4)) sum_num, " +
                "(sum(CASE r.dining_code WHEN 0 THEN meal_money ELSE 0 END)+ " +
                "sum(CASE r.dining_code WHEN 1 THEN meal_money ELSE 0 END)+ " +
                "sum(CASE r.dining_code WHEN 2 THEN meal_money ELSE 0 END)+ " +
                "sum(CASE r.dining_code WHEN 3 THEN meal_money ELSE 0 END)+ " +
                "sum(CASE r.dining_code WHEN 4 THEN meal_money ELSE 0 END))/100 'sum_money' " +
                "from meal_record r where 1=1 ";

        if (pk_device != null && pk_device.length() > 0) {
            queryMerchantDay += "and r.pk_device = '" + pk_device + "' ";
        }
        if (pk_merchant != null && pk_merchant.length() > 0) {
            queryMerchantDay += "and r.pk_merchant = '" + pk_merchant + "' ";
        }
        if (start_ts != null && start_ts.length() > 0) {
            queryMerchantDay += "and meal_ts_day >= '" + start_ts + "' ";
        }
        if (end_ts != null && end_ts.length() > 0) {
            queryMerchantDay += "and meal_ts_day <= '" + end_ts + "' ";
        }
//        queryMerchantDay += "group by meal_ts_day,r.device_code";
        queryMerchantDay += "group by device_code";

        List<MerchantPOSMondel> merchantPOSMondels =
                BaseUtils.mapToBean(MerchantPOSMondel.class, publicDAO.retrieveBySql(queryMerchantDay));

        return merchantPOSMondels;
    }

    @Override
    public List<MerchantPos> findSumbyMerchant(Long pk_merchant, String start_ts, String end_ts) {
        String strFields = getFields();
        String selectSql = "SELECT  " +
                "  pk_merchant, " +
                "  merchant_name ," +
                "  merchant_code , " +
                "  count(0) AS amount ,  " +
                "  SUM(meal_money) AS price    " +
                "FROM  " +
                "  meal_record r WHERE  " +
                "  meal_ts_day >= '" + start_ts + "'  " +
                "AND meal_ts_day <= '" + end_ts + "'  ";

        String whereSql = "";
        String groupBySql = " GROUP BY pk_merchant  HAVING pk_merchant = " + pk_merchant;

        String mchSql = selectSql + whereSql + groupBySql;
        whereSql = " and device_type = '2' ";
        String cfSql = selectSql + whereSql + groupBySql;
        whereSql = " and device_type = '3' ";
        String mkSql = selectSql + whereSql + groupBySql;
        whereSql = " and device_type = '1' and dining_code = '0' ";
        String brkSql = selectSql + whereSql + groupBySql;
        whereSql = " and device_type = '1' and dining_code = '1' ";
        String lchSql = selectSql + whereSql + groupBySql;
        whereSql = " and device_type = '1' and dining_code = '2' ";
        String otoSql = selectSql + whereSql + groupBySql;
        whereSql = " and device_type = '1' and dining_code = '3' ";
        String dnrSql = selectSql + whereSql + groupBySql;

        String strSql = strFields + " from " +
                " ( " + cfSql + " ) cf, " +
                " ( " + mkSql + " ) mk, " +
                " ( " + brkSql + " ) brk, " +
                " ( " + lchSql + " ) lch, " +
                " ( " + otoSql + " ) oto, " +
                " ( " + dnrSql + " ) dnr, " +
                " ( " + mchSql + " ) mch ";

        List<Map> mPos = publicDAO.retrieveBySql(strSql);
        List<MerchantPos> merchantPos = BaseUtils.mapToBean(MerchantPos.class, mPos);
        return merchantPos;
    }

    @Override
    public List<MerchantPos> findSumbyMerchantExtend(Long pk_merchant, String start_ts, String end_ts) {
        String strFields = getFields();
        String selectSql = "SELECT  " +
                "  pk_merchant, " +
                "  merchant_name ," +
                "  merchant_code , " +
                "  count(0) AS amount ,  " +
                "  SUM(meal_money) AS price    " +
                "FROM  " +
                "  meal_record r " +
                "WHERE  " +
                "  meal_ts_day >= '" + start_ts + "'  " +
                "AND pk_merchant_staff <> " + pk_merchant + "  " +
                "AND meal_ts_day <= '" + end_ts + "'  ";

        String whereSql = "";
        String groupBySql = " GROUP BY pk_merchant  HAVING pk_merchant = " + pk_merchant;

        String mchSql = selectSql + whereSql + groupBySql;
        whereSql = " and device_type = '2' ";
        String cfSql = selectSql + whereSql + groupBySql;
        whereSql = " and device_type = '3' ";
        String mkSql = selectSql + whereSql + groupBySql;
        whereSql = " and device_type = '1' and dining_code = '0' ";
        String brkSql = selectSql + whereSql + groupBySql;
        whereSql = " and device_type = '1' and dining_code = '1' ";
        String lchSql = selectSql + whereSql + groupBySql;
        whereSql = " and device_type = '1' and dining_code = '2' ";
        String otoSql = selectSql + whereSql + groupBySql;
        whereSql = " and device_type = '1' and dining_code = '3' ";
        String dnrSql = selectSql + whereSql + groupBySql;

        String strSql = strFields + " from " +
                " ( " + cfSql + " ) cf, " +
                " ( " + mkSql + " ) mk, " +
                " ( " + brkSql + " ) brk, " +
                " ( " + lchSql + " ) lch, " +
                " ( " + otoSql + " ) oto, " +
                " ( " + dnrSql + " ) dnr, " +
                " ( " + mchSql + " ) mch ";

        List<Map> mPos = publicDAO.retrieveBySql(strSql);
        List<MerchantPos> merchantPos = BaseUtils.mapToBean(MerchantPos.class, mPos);
        return merchantPos;
    }

    private String getFields() {
        return "SELECT " +
                " mch.pk_merchant as pkMerchant , " +
                " mch.merchant_code as merchantCode, " +
                " mch.merchant_name as merchantName, " +
                " cf.amount AS cofferAmount, " +
                " cf.price  / 100 AS cofferPrice, " +
                " mk.amount AS marketAmout, " +
                " mk.price / 100 AS marketPrice, " +
                " brk.amount AS breakfastAmount, " +
                " brk.price / 100 AS breakfastPrice , " +
                " lch.amount AS lunchAmount, " +
                " lch.price / 100 AS lunchPrice , " +
                " oto.amount AS oderAmount, " +
                " oto.price / 100 AS oderPrice , " +
                " dnr.amount AS dinnerAmount, " +
                " dnr.price / 100 AS dinnerPrice, " +
                " mch.amount as totalAmount, " +
                " mch.price / 100   as totalPrice";
    }

    private String makeTable(String start_ts, String end_ts) {
        String strTable = "( select * from meal_record r ";
//        List<String> tableNames = detailAllService.gainTableName(start_ts, end_ts);
//        for (int i = 0; i < tableNames.size(); i++) {
//            strTable += "select meal_ts,meal_money,dining_code,pk_device,pk_staff from " + tableNames.get(i);
//            if (i < tableNames.size() - 1) {
//                strTable += " UNION ALL ";
//            }
//        }
        strTable += " where " +
                " meal_ts_day >= '" + start_ts + "' " +
                " and meal_ts_day <= '" + end_ts + "' " +
                ") mlr";
        return strTable;
    }

    public static void main(String[] args) {
        Double d1 = 163.299;

        double mt = BigDecimalUtil.add(d1, 2.05);
        System.out.println(mt);
        System.out.println(BigDecimalUtil.round(mt, 2));
    }
}
