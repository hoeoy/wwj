package com.iandtop.service.impl;

import com.iandtop.dao.PublicDAO;
import com.iandtop.dao.TenantPosDAO;
import com.iandtop.model.device.DeviceModel;
import com.iandtop.model.form.MerchantPOSMondel;
import com.iandtop.model.form.MerchantPos;
import com.iandtop.model.meal.MerchantModel;
import com.iandtop.service.DetailAllService;
import com.iandtop.service.TenantPosService;
import com.iandtop.utils.BigDecimalUtil;
import com.iandtop.utils.BaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xss on 2017-05-24.
 */
@Service
@Transactional
public class TenantPosImpl implements TenantPosService{

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
    public List<MerchantPOSMondel> queryMerchantDay(String pk_merchant,String pk_device,String start_ts, String end_ts) {
        String queryMerchantDay = "select substring(r.meal_ts,1,10) sum_date,m.merchant_code,m.merchant_name,d.device_code,d.device_name , " +
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
                "from (";
        List<String> tableNames = detailAllService.gainTableName(start_ts,end_ts);
        for(int i = 0;i<tableNames.size();i++){
            queryMerchantDay += "select meal_ts,meal_money,dining_code,pk_device from "+tableNames.get(i);
            if(i<tableNames.size()-1){
                queryMerchantDay+=" UNION ALL ";
            }
        }
        queryMerchantDay+=")r left join meal_device  d on r.pk_device = d.pk_device " +
                "left join meal_merchant m on d.pk_merchant = m.pk_merchant " +

                "where 1=1 ";
        if(pk_device!=null && pk_device.length()>0){
            queryMerchantDay+="and r.pk_device = '"+pk_device+"' ";
        }
        if(pk_merchant!=null && pk_merchant.length()>0){
            queryMerchantDay+="and d.pk_merchant = '"+pk_merchant+"' ";
        }
        if(start_ts!=null && start_ts.length()>0){
            queryMerchantDay+="and substring(r.meal_ts,1,10) >= '"+start_ts+"' ";
        }
        if(end_ts!=null && end_ts.length()>0){
            queryMerchantDay+="and substring(r.meal_ts,1,10) <= '"+end_ts+"' ";
        }
        queryMerchantDay+="group by substring(r.meal_ts,1,10)";
        List<MerchantPOSMondel> merchantPOSMondels =
                BaseUtils.mapToBean(MerchantPOSMondel.class,publicDAO.retrieveBySql(queryMerchantDay));
        return merchantPOSMondels;
    }

    @Override
    public List<MerchantPOSMondel> queryMerchantSum(String pk_merchant,String pk_device, String start_ts, String end_ts) {
        String queryMerchantDay = "select substring(r.meal_ts,1,10) sum_date,m.merchant_code,m.merchant_name,d.device_code,d.device_name , " +
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
                "from (";
        List<String> tableNames = detailAllService.gainTableName(start_ts,end_ts);
        for(int i = 0;i<tableNames.size();i++){
            queryMerchantDay += "select meal_ts,meal_money,dining_code,pk_device from "+tableNames.get(i);
            if(i<tableNames.size()-1){
                queryMerchantDay+=" UNION ALL ";
            }
        }
        queryMerchantDay+=")r left join meal_device  d on r.pk_device = d.pk_device " +
                "left join meal_merchant m on d.pk_merchant = m.pk_merchant " +
                "left join meal_dining i on r.dining_code = i.dining_code " +
                "where 1=1 ";
        if(pk_device!=null && pk_device.length()>0){
            queryMerchantDay+="and r.pk_device = '"+pk_device+"' ";
        }
        if(pk_merchant!=null && pk_merchant.length()>0){
            queryMerchantDay+="and d.pk_merchant = '"+pk_merchant+"' ";
        }
        if(start_ts!=null && start_ts.length()>0){
            queryMerchantDay+="and substring(r.meal_ts,1,10) >= '"+start_ts+"' ";
        }
        if(end_ts!=null && end_ts.length()>0){
            queryMerchantDay+="and substring(r.meal_ts,1,10) <= '"+end_ts+"' ";
        }
        queryMerchantDay+="group by substring(r.meal_ts,1,10),d.device_code";
        List<MerchantPOSMondel> merchantPOSMondels =
                BaseUtils.mapToBean(MerchantPOSMondel.class,publicDAO.retrieveBySql(queryMerchantDay));
        MerchantPOSMondel merchantPOSMondel;
        Integer breakfast_num  = 0;

        int lunch_num = 0;
        int dinner_num = 0;
        int night_num = 0;
        int else_num = 0;
        int sum_num = 0;

        Double breakfast_money = 0.0;
        Double lunch_money = 0.0;
        Double dinner_money = 0.0;
        Double Night_money = 0.0;
        Double else_money = 0.0;
        Double sum_money = 0.0;

        List<MerchantPOSMondel> merchantPOSMondelList = new ArrayList<MerchantPOSMondel>();
        for(int i = 0;i<merchantPOSMondels.size();i++){
            merchantPOSMondel = new MerchantPOSMondel();
            boolean mark = false;
            try{
                breakfast_num= Integer.parseInt(merchantPOSMondels.get(i).getBreakfast_num()) ;
                breakfast_money=merchantPOSMondels.get(i).getBreakfast_money();
                lunch_num=Integer.parseInt(merchantPOSMondels.get(i).getLunch_num());
                lunch_money=merchantPOSMondels.get(i).getLunch_money();
                dinner_num=Integer.parseInt(merchantPOSMondels.get(i).getDinner_num());
                dinner_money=merchantPOSMondels.get(i).getDinner_money();
                night_num=Integer.parseInt(merchantPOSMondels.get(i).getNight_num());
                Night_money=merchantPOSMondels.get(i).getNight_money();
                else_num=Integer.parseInt(merchantPOSMondels.get(i).getElse_num());
                else_money=merchantPOSMondels.get(i).getElse_money();
                sum_num=Integer.parseInt(merchantPOSMondels.get(i).getSum_num());
                sum_money=merchantPOSMondels.get(i).getSum_money();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            for(MerchantPOSMondel m : merchantPOSMondelList){
                if(m.getDevice_code().equals(merchantPOSMondels.get(i).getDevice_code())){
                    mark = true;
                }
            }
            if(mark){
                continue;
            }
                for(int j = i+1;j<merchantPOSMondels.size();j++){
                    if(merchantPOSMondels.get(i).getDevice_code().equals(merchantPOSMondels.get(j).getDevice_code())){

                        breakfast_num += Integer.parseInt( merchantPOSMondels.get(j).getBreakfast_num());
                        breakfast_money = BigDecimalUtil.add(breakfast_money,merchantPOSMondels.get(j).getBreakfast_money());


                        lunch_num+=Integer.parseInt(merchantPOSMondels.get(j).getLunch_num());
                        lunch_money = BigDecimalUtil.add(lunch_money,merchantPOSMondels.get(j).getLunch_money());

                        dinner_num+=Integer.parseInt(merchantPOSMondels.get(j).getDinner_num());
                        dinner_money= BigDecimalUtil.add(dinner_money,merchantPOSMondels.get(j).getDinner_money());

                        night_num+=Integer.parseInt(merchantPOSMondels.get(j).getNight_num());
                        Night_money= BigDecimalUtil.add(Night_money,merchantPOSMondels.get(j).getNight_money());

                        else_num+=Integer.parseInt(merchantPOSMondels.get(j).getElse_num());
                        else_money = BigDecimalUtil.add(else_money,merchantPOSMondels.get(j).getElse_money());

                        sum_num+=Integer.parseInt(merchantPOSMondels.get(j).getSum_num());
                        sum_money = BigDecimalUtil.add(sum_money,merchantPOSMondels.get(j).getSum_money());


                    }
                }
            merchantPOSMondel.setSum_date(merchantPOSMondels.get(i).getSum_date());
            merchantPOSMondel.setMerchant_code(merchantPOSMondels.get(i).getMerchant_code());
            merchantPOSMondel.setMerchant_name(merchantPOSMondels.get(i).getMerchant_name());
            merchantPOSMondel.setDevice_code(merchantPOSMondels.get(i).getDevice_code());
            merchantPOSMondel.setDevice_name(merchantPOSMondels.get(i).getDevice_name());
            merchantPOSMondel.setBreakfast_num(String.valueOf(breakfast_num));
            merchantPOSMondel.setBreakfast_money(breakfast_money);
            merchantPOSMondel.setLunch_num(String.valueOf(lunch_num));
            merchantPOSMondel.setLunch_money(lunch_money);
            merchantPOSMondel.setDinner_num(String.valueOf(dinner_num));
            merchantPOSMondel.setDinner_money(dinner_money);
            merchantPOSMondel.setNight_num(String.valueOf(night_num));
            merchantPOSMondel.setNight_money(Night_money);
            merchantPOSMondel.setElse_num(String.valueOf(else_num));
            merchantPOSMondel.setElse_money(else_money);
            merchantPOSMondel.setSum_num(String.valueOf(sum_num));
            merchantPOSMondel.setSum_money(sum_money);
            merchantPOSMondelList.add(merchantPOSMondel);
        }
        return merchantPOSMondelList;
    }

    @Override
    public List<MerchantPos> findSumbyMerchant( Long pk_merchant, String start_ts, String end_ts) {
        String strTable = makeTable(start_ts,end_ts);
        String strFields = getFields();
        String strSql = strFields +
                " FROM " +
                " meal_merchant mm, " +
                " ( " +
                "  SELECT " +
                "   count(0) AS total_amount, " +
                "   SUM(mlr.meal_money) AS total_price " +
                "  FROM " + strTable +
                "  WHERE " +
                "   mlr.pk_device IN ( " +
                "    SELECT " +
                "     mld.pk_device " +
                "    FROM " +
                "     meal_device mld " +
                "    WHERE " +
                "     mld.pk_merchant = "+ pk_merchant +
                "    AND mld.pk_device IN ( " +
                "     SELECT " +
                "      mlr.pk_device " +
                "     FROM " + strTable +
                "     GROUP BY " +
                "      mlr.pk_device " +
                "    ) " +
                "   ) " +
                " ) mch, " +
                " ( " +
                "  SELECT " +
                "   count(0) AS amount, " +
                "   SUM(mlr.meal_money) AS price " +
                "  FROM " + strTable +
                "  WHERE " +
                "   mlr.pk_device IN ( " +
                "    SELECT " +
                "     b.pk_device " +
                "    FROM " +
                "     ( " +
                "      SELECT " +
                "       mld.pk_device, " +
                "       mld.device_type " +
                "      FROM " +
                "       meal_device mld " +
                "      WHERE " +
                "       mld.pk_device IN ( " +
                "        SELECT " +
                "         mlr.pk_device " +
                "        FROM " + strTable +
                "        GROUP BY " +
                "         mlr.pk_device " +
                "       ) " +
                "      AND mld.pk_device IN ( " +
                "       SELECT " +
                "        mld.pk_device " +
                "       FROM " +
                "        meal_device mld " +
                "       WHERE " +
                "        mld.pk_merchant = "+ pk_merchant +
                "       AND mld.pk_device IN ( " +
                "        SELECT " +
                "         mlr.pk_device " +
                "        FROM " + strTable +
                "        GROUP BY " +
                "         mlr.pk_device " +
                "       ) " +
                "      ) " +
                "     ) b " +
                "    WHERE " +
                "     b.device_type = '2' " +
                "   ) " +
                " ) cf, " +
                " ( " +
                "  SELECT " +
                "   count(0) AS amount, " +
                "   SUM(mlr.meal_money) AS price " +
                "  FROM " + strTable +
                "  WHERE " +
                "   mlr.pk_device IN ( " +
                "    SELECT " +
                "     b.pk_device " +
                "    FROM " +
                "     ( " +
                "      SELECT " +
                "       mld.pk_device, " +
                "       mld.device_type " +
                "      FROM " +
                "       meal_device mld " +
                "      WHERE " +
                "       mld.pk_device IN ( " +
                "        SELECT " +
                "         mlr.pk_device " +
                "        FROM " + strTable +
                "        GROUP BY " +
                "         mlr.pk_device " +
                "       ) " +
                "      AND mld.pk_device IN ( " +
                "       SELECT " +
                "        mld.pk_device " +
                "       FROM " +
                "        meal_device mld " +
                "       WHERE " +
                "        mld.pk_merchant = "+ pk_merchant +
                "       AND mld.pk_device IN ( " +
                "        SELECT " +
                "         mlr.pk_device " +
                "        FROM " + strTable +
                "        GROUP BY " +
                "         mlr.pk_device " +
                "       ) " +
                "      ) " +
                "     ) b " +
                "    WHERE " +
                "     b.device_type = '3' " +
                "   ) " +
                " ) mk, " +
                " ( " +
                "  SELECT " +
                "   count(0) AS amount, " +
                "   SUM(mlr.meal_money) AS price " +
                "  FROM " + strTable +
                "  WHERE " +
                "   mlr.pk_device IN ( " +
                "    SELECT " +
                "     b.pk_device " +
                "    FROM " +
                "     ( " +
                "      SELECT " +
                "       mld.pk_device, " +
                "       mld.device_type " +
                "      FROM " +
                "       meal_device mld " +
                "      WHERE " +
                "       mld.pk_device IN ( " +
                "        SELECT " +
                "         mlr.pk_device " +
                "        FROM " + strTable +
                "        GROUP BY " +
                "         mlr.pk_device " +
                "       ) " +
                "      AND mld.pk_device IN ( " +
                "       SELECT " +
                "        mld.pk_device " +
                "       FROM " +
                "        meal_device mld " +
                "       WHERE " +
                "        mld.pk_merchant = "+ pk_merchant +
                "       AND mld.pk_device IN ( " +
                "        SELECT " +
                "         mlr.pk_device " +
                "        FROM " + strTable +
                "        GROUP BY " +
                "         mlr.pk_device " +
                "       ) " +
                "      ) " +
                "     ) b " +
                "    WHERE " +
                "     b.device_type = '1' " +
                "   ) " +
                "  AND mlr.dining_code = '0' " +
                " ) brk, " +
                " ( " +
                "  SELECT " +
                "   count(0) AS amount, " +
                "   SUM(mlr.meal_money) AS price " +
                "  FROM " + strTable +
                "  WHERE " +
                "   mlr.pk_device IN ( " +
                "    SELECT " +
                "     b.pk_device " +
                "    FROM " +
                "     ( " +
                "      SELECT " +
                "       mld.pk_device, " +
                "       mld.device_type " +
                "      FROM " +
                "       meal_device mld " +
                "      WHERE " +
                "       mld.pk_device IN ( " +
                "        SELECT " +
                "         mlr.pk_device " +
                "        FROM " + strTable +
                "        GROUP BY " +
                "         mlr.pk_device " +
                "       ) " +
                "      AND mld.pk_device IN ( " +
                "       SELECT " +
                "        mld.pk_device " +
                "       FROM " +
                "        meal_device mld " +
                "       WHERE " +
                "        mld.pk_merchant = "+ pk_merchant +
                "       AND mld.pk_device IN ( " +
                "        SELECT " +
                "         mlr.pk_device " +
                "        FROM " + strTable +
                "        GROUP BY " +
                "         mlr.pk_device " +
                "       ) " +
                "      ) " +
                "     ) b " +
                "    WHERE " +
                "     b.device_type = '1' " +
                "   ) " +
                "  AND mlr.dining_code = '1' " +
                " ) lch, " +
                " ( " +
                "  SELECT " +
                "   count(0) AS amount, " +
                "   SUM(mlr.meal_money) AS price " +
                "  FROM " + strTable +
                "  WHERE " +
                "   mlr.pk_device IN ( " +
                "    SELECT " +
                "     b.pk_device " +
                "    FROM " +
                "     ( " +
                "      SELECT " +
                "       mld.pk_device, " +
                "       mld.device_type " +
                "      FROM " +
                "       meal_device mld " +
                "      WHERE " +
                "       mld.pk_device IN ( " +
                "        SELECT " +
                "         mlr.pk_device " +
                "        FROM " + strTable +
                "        GROUP BY " +
                "         mlr.pk_device " +
                "       ) " +
                "      AND mld.pk_device IN ( " +
                "       SELECT " +
                "        mld.pk_device " +
                "       FROM " +
                "        meal_device mld " +
                "       WHERE " +
                "        mld.pk_merchant = "+ pk_merchant +
                "       AND mld.pk_device IN ( " +
                "        SELECT " +
                "         mlr.pk_device " +
                "        FROM " + strTable +
                "        GROUP BY " +
                "         mlr.pk_device " +
                "       ) " +
                "      ) " +
                "     ) b " +
                "    WHERE " +
                "     b.device_type = '1' " +
                "   ) " +
                "  AND mlr.dining_code = '2' " +
                " ) oto, " +
                " ( " +
                "  SELECT " +
                "   count(0) AS amount, " +
                "   SUM(mlr.meal_money) AS price " +
                "  FROM " + strTable +
                "  WHERE " +
                "   mlr.pk_device IN ( " +
                "    SELECT " +
                "     b.pk_device " +
                "    FROM " +
                "     ( " +
                "      SELECT " +
                "       mld.pk_device, " +
                "       mld.device_type " +
                "      FROM " +
                "       meal_device mld " +
                "      WHERE " +
                "       mld.pk_device IN ( " +
                "        SELECT " +
                "         mlr.pk_device " +
                "        FROM " + strTable +
                "        GROUP BY " +
                "         mlr.pk_device " +
                "       ) " +
                "      AND mld.pk_device IN ( " +
                "       SELECT " +
                "        mld.pk_device " +
                "       FROM " +
                "        meal_device mld " +
                "       WHERE " +
                "        mld.pk_merchant = "+ pk_merchant +
                "       AND mld.pk_device IN ( " +
                "        SELECT " +
                "         mlr.pk_device " +
                "        FROM " + strTable +
                "        GROUP BY " +
                "         mlr.pk_device " +
                "       ) " +
                "      ) " +
                "     ) b " +
                "    WHERE " +
                "     b.device_type = '1' " +
                "   ) " +
                "  AND mlr.dining_code = '3' " +
                " ) dnr " +
                "WHERE " +
                " mm.pk_merchant ="+ pk_merchant;

        List<MerchantPos> merchantPos = BaseUtils.mapToBean(MerchantPos.class, publicDAO.retrieveBySql(strSql));

        return merchantPos;
    }

    @Override
    public List<MerchantPos> findSumbyMerchantExtend(Long pk_merchant, String start_ts, String end_ts) {
        String strTable = makeTable(start_ts, end_ts);
        String strFields = getFields();
        String strSql = strFields +
                " FROM " +
                " meal_merchant mm, " +
                " ( " +
                "  SELECT " +
                "   count(0) AS total_amount, " +
                "   SUM(mlr.meal_money) AS total_price " +
                "  FROM " + strTable +
                "  WHERE " +
                "   mlr.pk_device IN ( " +
                "    SELECT " +
                "     mld.pk_device " +
                "    FROM " +
                "     meal_device mld " +
                "    WHERE " +
                "     mld.pk_merchant = " + pk_merchant +
                "    AND mld.pk_device IN ( " +
                "     SELECT " +
                "      mlr.pk_device " +
                "     FROM " + strTable +
                "     GROUP BY " +
                "      mlr.pk_device " +
                "    ) " +
                "   ) " +
                "  AND mlr.pk_staff NOT IN ( " +
                "   SELECT " +
                "    ds.pk_staff " +
                "   FROM " +
                "    db_staff ds " +
                "   WHERE " +
                "    ds.pk_merchant = " + pk_merchant +
                "  ) " +
                " ) mch, " +
                " ( " +
                "  SELECT " +
                "   count(0) AS amount, " +
                "   SUM(mlr.meal_money) AS price " +
                "  FROM " + strTable +
                "  WHERE " +
                "   mlr.pk_device IN ( " +
                "    SELECT " +
                "     b.pk_device " +
                "    FROM " +
                "     ( " +
                "      SELECT " +
                "       mld.pk_device, " +
                "       mld.device_type " +
                "      FROM " +
                "       meal_device mld " +
                "      WHERE " +
                "       mld.pk_device IN ( " +
                "        SELECT " +
                "         mlr.pk_device " +
                "        FROM " + strTable +
                "        GROUP BY " +
                "         mlr.pk_device " +
                "       ) " +
                "      AND mld.pk_device IN ( " +
                "       SELECT " +
                "        mld.pk_device " +
                "       FROM " +
                "        meal_device mld " +
                "       WHERE " +
                "        mld.pk_merchant = " + pk_merchant +
                "       AND mld.pk_device IN ( " +
                "        SELECT " +
                "         mlr.pk_device " +
                "        FROM " + strTable +
                "        GROUP BY " +
                "         mlr.pk_device " +
                "       ) " +
                "      ) " +
                "     ) b " +
                "    WHERE " +
                "     b.device_type = '2' " +
                "   ) " +
                "  AND mlr.pk_staff NOT IN ( " +
                "   SELECT " +
                "    ds.pk_staff " +
                "   FROM " +
                "    db_staff ds " +
                "   WHERE " +
                "    ds.pk_merchant = " + pk_merchant +
                "  ) " +
                " ) cf, " +
                " ( " +
                "  SELECT " +
                "   count(0) AS amount, " +
                "   SUM(mlr.meal_money) AS price " +
                "  FROM " + strTable +
                "  WHERE " +
                "   mlr.pk_device IN ( " +
                "    SELECT " +
                "     b.pk_device " +
                "    FROM " +
                "     ( " +
                "      SELECT " +
                "       mld.pk_device, " +
                "       mld.device_type " +
                "      FROM " +
                "       meal_device mld " +
                "      WHERE " +
                "       mld.pk_device IN ( " +
                "        SELECT " +
                "         mlr.pk_device " +
                "        FROM " + strTable +
                "        GROUP BY " +
                "         mlr.pk_device " +
                "       ) " +
                "      AND mld.pk_device IN ( " +
                "       SELECT " +
                "        mld.pk_device " +
                "       FROM " +
                "        meal_device mld " +
                "       WHERE " +
                "        mld.pk_merchant = " + pk_merchant +
                "       AND mld.pk_device IN ( " +
                "        SELECT " +
                "         mlr.pk_device " +
                "        FROM " + strTable +
                "        GROUP BY " +
                "         mlr.pk_device " +
                "       ) " +
                "      ) " +
                "     ) b " +
                "    WHERE " +
                "     b.device_type = '3' " +
                "   ) " +
                "  AND mlr.pk_staff NOT IN ( " +
                "   SELECT " +
                "    ds.pk_staff " +
                "   FROM " +
                "    db_staff ds " +
                "   WHERE " +
                "    ds.pk_merchant = " + pk_merchant +
                "  ) " +
                " ) mk, " +
                " ( " +
                "  SELECT " +
                "   count(0) AS amount, " +
                "   SUM(mlr.meal_money) AS price " +
                "  FROM " + strTable +
                "  WHERE " +
                "   mlr.pk_device IN ( " +
                "    SELECT " +
                "     b.pk_device " +
                "    FROM " +
                "     ( " +
                "      SELECT " +
                "       mld.pk_device, " +
                "       mld.device_type " +
                "      FROM " +
                "       meal_device mld " +
                "      WHERE " +
                "       mld.pk_device IN ( " +
                "        SELECT " +
                "         mlr.pk_device " +
                "        FROM " + strTable +
                "        GROUP BY " +
                "         mlr.pk_device " +
                "       ) " +
                "      AND mld.pk_device IN ( " +
                "       SELECT " +
                "        mld.pk_device " +
                "       FROM " +
                "        meal_device mld " +
                "       WHERE " +
                "        mld.pk_merchant = " + pk_merchant +
                "       AND mld.pk_device IN ( " +
                "        SELECT " +
                "         mlr.pk_device " +
                "        FROM " + strTable +
                "        GROUP BY " +
                "         mlr.pk_device " +
                "       ) " +
                "      ) " +
                "     ) b " +
                "    WHERE " +
                "     b.device_type = '1' " +
                "   ) " +
                "  AND mlr.pk_staff NOT IN ( " +
                "   SELECT " +
                "    ds.pk_staff " +
                "   FROM " +
                "    db_staff ds " +
                "   WHERE " +
                "    ds.pk_merchant = " + pk_merchant +
                "  ) " +
                "  AND mlr.dining_code = '0' " +
                " ) brk, " +
                " ( " +
                "  SELECT " +
                "   count(0) AS amount, " +
                "   SUM(mlr.meal_money) AS price " +
                "  FROM " + strTable +
                "  WHERE " +
                "   mlr.pk_device IN ( " +
                "    SELECT " +
                "     b.pk_device " +
                "    FROM " +
                "     ( " +
                "      SELECT " +
                "       mld.pk_device, " +
                "       mld.device_type " +
                "      FROM " +
                "       meal_device mld " +
                "      WHERE " +
                "       mld.pk_device IN ( " +
                "        SELECT " +
                "         mlr.pk_device " +
                "        FROM " + strTable +
                "        GROUP BY " +
                "         mlr.pk_device " +
                "       ) " +
                "      AND mld.pk_device IN ( " +
                "       SELECT " +
                "        mld.pk_device " +
                "       FROM " +
                "        meal_device mld " +
                "       WHERE " +
                "        mld.pk_merchant = " + pk_merchant +
                "       AND mld.pk_device IN ( " +
                "        SELECT " +
                "         mlr.pk_device " +
                "        FROM " + strTable +
                "        GROUP BY " +
                "         mlr.pk_device " +
                "       ) " +
                "      ) " +
                "     ) b " +
                "    WHERE " +
                "     b.device_type = '1' " +
                "   ) " +
                "  AND mlr.pk_staff NOT IN ( " +
                "   SELECT " +
                "    ds.pk_staff " +
                "   FROM " +
                "    db_staff ds " +
                "   WHERE " +
                "    ds.pk_merchant = " + pk_merchant +
                "  ) " +
                "  AND mlr.dining_code = '1' " +
                " ) lch, " +
                " ( " +
                "  SELECT " +
                "   count(0) AS amount, " +
                "   SUM(mlr.meal_money) AS price " +
                "  FROM " + strTable +
                "  WHERE " +
                "   mlr.pk_device IN ( " +
                "    SELECT " +
                "     b.pk_device " +
                "    FROM " +
                "     ( " +
                "      SELECT " +
                "       mld.pk_device, " +
                "       mld.device_type " +
                "      FROM " +
                "       meal_device mld " +
                "      WHERE " +
                "       mld.pk_device IN ( " +
                "        SELECT " +
                "         mlr.pk_device " +
                "        FROM " + strTable +
                "        GROUP BY " +
                "         mlr.pk_device " +
                "       ) " +
                "      AND mld.pk_device IN ( " +
                "       SELECT " +
                "        mld.pk_device " +
                "       FROM " +
                "        meal_device mld " +
                "       WHERE " +
                "        mld.pk_merchant = " + pk_merchant +
                "       AND mld.pk_device IN ( " +
                "        SELECT " +
                "         mlr.pk_device " +
                "        FROM " + strTable +
                "        GROUP BY " +
                "         mlr.pk_device " +
                "       ) " +
                "      ) " +
                "     ) b " +
                "    WHERE " +
                "     b.device_type = '1' " +
                "   ) " +
                "  AND mlr.pk_staff NOT IN ( " +
                "   SELECT " +
                "    ds.pk_staff " +
                "   FROM " +
                "    db_staff ds " +
                "   WHERE " +
                "    ds.pk_merchant = " + pk_merchant +
                "  ) " +
                "  AND mlr.dining_code = '2' " +
                " ) oto, " +
                " ( " +
                "  SELECT " +
                "   count(0) AS amount, " +
                "   SUM(mlr.meal_money) AS price " +
                "  FROM " + strTable +
                "  WHERE " +
                "   mlr.pk_device IN ( " +
                "    SELECT " +
                "     b.pk_device " +
                "    FROM " +
                "     ( " +
                "      SELECT " +
                "       mld.pk_device, " +
                "       mld.device_type " +
                "      FROM " +
                "       meal_device mld " +
                "      WHERE " +
                "       mld.pk_device IN ( " +
                "        SELECT " +
                "         mlr.pk_device " +
                "        FROM " + strTable +
                "        GROUP BY " +
                "         mlr.pk_device " +
                "       ) " +
                "      AND mld.pk_device IN ( " +
                "       SELECT " +
                "        mld.pk_device " +
                "       FROM " +
                "        meal_device mld " +
                "       WHERE " +
                "        mld.pk_merchant = " + pk_merchant +
                "       AND mld.pk_device IN ( " +
                "        SELECT " +
                "         mlr.pk_device " +
                "        FROM " + strTable +
                "        GROUP BY " +
                "         mlr.pk_device " +
                "       ) " +
                "      ) " +
                "     ) b " +
                "    WHERE " +
                "     b.device_type = '1' " +
                "   ) " +
                "  AND mlr.pk_staff NOT IN ( " +
                "   SELECT " +
                "    ds.pk_staff " +
                "   FROM " +
                "    db_staff ds " +
                "   WHERE " +
                "    ds.pk_merchant = " + pk_merchant +
                "  ) " +
                "  AND mlr.dining_code = '3' " +
                " ) dnr " +
                "WHERE " +
                "mm.pk_merchant = " + pk_merchant;

        List<MerchantPos> merchantPos = BaseUtils.mapToBean(MerchantPos.class, publicDAO.retrieveBySql(strSql));

        return merchantPos;
    }

    private String getFields() {
        return "SELECT " +
                    " mm.pk_merchant as pkMerchant , " +
                    " mm.merchant_code as merchantCode, " +
                    " mm.merchant_name as merchantName, " +
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
                    " mch.total_amount as totalAmount, " +
                    " mch.total_price / 100   as totalPrice";
    }

    private String makeTable(String start_ts, String end_ts) {
        String strTable = "( select * from (";
        List<String> tableNames = detailAllService.gainTableName(start_ts, end_ts);
        for (int i = 0; i < tableNames.size(); i++) {
            strTable += "select meal_ts,meal_money,dining_code,pk_device,pk_staff from " + tableNames.get(i);
            if (i < tableNames.size() - 1) {
                strTable += " UNION ALL ";
            }
        }
        strTable += ")r where " +
                " substring(r.meal_ts,1,10) >= '" + start_ts + "' " +
                " and substring(r.meal_ts,1,10) <= '" + end_ts + "' " +
                ") mlr";
        return strTable;
    }

    public static void main(String[] args) {
        Double d1 = 163.299;

        double mt = BigDecimalUtil.add(d1,2.05);
        System.out.println(mt);
        System.out.println(BigDecimalUtil.round(mt,2));
    }
}
