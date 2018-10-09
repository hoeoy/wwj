package com.iandtop.service.impl;

import com.iandtop.dao.PublicDAO;
import com.iandtop.dao.TenantPosDAO;
import com.iandtop.model.device.DeviceModel;
import com.iandtop.model.form.MerchantPOSMondel;
import com.iandtop.model.form.MerchantPos;
import com.iandtop.model.meal.MerchantModel;
import com.iandtop.service.TenantPosService;
import com.iandtop.utils.BaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
        return resultForSumMerchant(pk_merchant, start_ts, end_ts, false);
    }

    @Override
    public List<MerchantPos> findSumbyMerchantExtend(Long pk_merchant, String start_ts, String end_ts) {
        return resultForSumMerchant(pk_merchant, start_ts, end_ts, true);
    }

    private List<MerchantPos> resultForSumMerchant(Long pk_merchant, String start_ts, String end_ts, Boolean isExtend) {
        String selectSql = "SELECT  " +
                "  pk_merchant, " +
                "  merchant_name ," +
                "  merchant_code , " +
                "  count(0) AS amount ,  " +
                "  SUM(meal_money)/100 AS price    " +
                "FROM  " +
                "  meal_record r " +
                "WHERE  " +
                "  meal_ts_day >= '" + start_ts + "'  " +
                "AND meal_ts_day <= '" + end_ts + "'  ";
        if (isExtend) {
            selectSql += "AND pk_merchant_staff <> " + pk_merchant + "  ";
        }
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
        whereSql = " and device_type = '1' and dining_code = '3' ";
        String dnrSql = selectSql + whereSql + groupBySql;
        whereSql = " and device_type = '1' and dining_code = '2' ";
        String otoSql = selectSql + whereSql + groupBySql;

        List<Map> mchPos = publicDAO.retrieveBySql(mchSql);
        List<Map> cfPos = publicDAO.retrieveBySql(cfSql);
        List<Map> mkPos = publicDAO.retrieveBySql(mkSql);
        List<Map> brkPos = publicDAO.retrieveBySql(brkSql);
        List<Map> lchPos = publicDAO.retrieveBySql(lchSql);
        List<Map> dnrPos = publicDAO.retrieveBySql(dnrSql);
        List<Map> otoPos = publicDAO.retrieveBySql(otoSql);

        Map mPosMap = new HashMap();
        mchPos.forEach(mch -> {
            mPosMap.put("pkMerchant", mch.get("pk_merchant"));
            mPosMap.put("merchantCode", mch.get("merchant_code"));
            mPosMap.put("merchantName", mch.get("merchant_name"));
            mPosMap.put("totalAmount", mch.get("amount"));
            mPosMap.put("totalPrice", mch.get("price"));
        });
        cfPos.forEach(cf -> {
            mPosMap.put("cofferAmount", cf.get("amount"));
            mPosMap.put("cofferPrice", cf.get("price"));
        });
        mkPos.forEach(mk -> {
            mPosMap.put("marketAmout", mk.get("amount"));
            mPosMap.put("marketPrice", mk.get("price"));
        });
        brkPos.forEach(brk -> {
            mPosMap.put("breakfastAmount", brk.get("amount"));
            mPosMap.put("breakfastPrice", brk.get("price"));
        });
        lchPos.forEach(lch -> {
            mPosMap.put("lunchAmount", lch.get("amount"));
            mPosMap.put("lunchPrice", lch.get("price"));
        });
        dnrPos.forEach(dnr -> {
            mPosMap.put("dinnerAmount", dnr.get("amount"));
            mPosMap.put("dinnerPrice", dnr.get("price"));
        });
        otoPos.forEach(oto -> {
            mPosMap.put("oderAmount", oto.get("amount"));
            mPosMap.put("oderPrice", oto.get("price"));
        });

        List<MerchantPos> merchantPos = BaseUtils.mapToBean(MerchantPos.class, Arrays.asList(mPosMap));
        return merchantPos;
    }
}
