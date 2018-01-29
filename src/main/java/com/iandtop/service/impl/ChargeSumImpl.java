package com.iandtop.service.impl;

import com.iandtop.dao.ChargeSumDAO;
import com.iandtop.dao.PublicDAO;
import com.iandtop.model.form.ChargeSumModel;
import com.iandtop.model.meal.MealRecordSumModel;
import com.iandtop.service.ChargeSumService;
import com.iandtop.service.DetailAllService;
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
public class ChargeSumImpl implements ChargeSumService {

    @Autowired
    private ChargeSumDAO chargeSumDAO;

    @Autowired
    private DetailAllService detailAllService;

    @Autowired
    private PublicDAO publicDAO;

    @Override
    public List<ChargeSumModel> queryChargeSum(String pk_user, String start_ts, String end_ts) {
        String queryChargeSum = "select  " +
                "e.op_ts charge_ts, " +
                "IFNULL(e.charge_money,0) charge_money," +
                "IFNULL(e.card_cost,0) card_cost," +
                "IFNULL(e.pledge_income,0) pledge_income," +
                "IFNULL(e.subsidy,0) subsidy," +
                "IFNULL(e.subsidy_empty,0) subsidy_empty," +
                "IFNULL(e.pledge_refund,0) pledge_refund," +
                "IFNULL(e.refund_money,0) refund_money," +
                "IFNULL(e.subsidy_refund,0) subsidy_refund  ," +
                "IFNULL(charge_money,0)+" +
                "IFNULL(card_cost,0)+" +
                "IFNULL(pledge_income,0)-" +
                "IFNULL(pledge_refund,0)-" +
                "IFNULL(refund_money,0)  practical_money " +
                "FROM" +
                "(select  " +
                "IFNULL(c.charge_ts,r.refund_ts) op_ts, c.charge_money,c.card_cost,c.pledge_income,c.subsidy," +
                "c.subsidy_empty ,r.pledge_refund,r.refund_money,r.subsidy_refund           " +
                "from v_charge_sum c " +
                "LEFT JOIN v_refund_sum  r on  c.charge_ts = r.refund_ts   " +
                "UNION " +
                "select  " +
                "IFNULL(c.charge_ts,r.refund_ts) op_ts ,  " +
                "c.charge_money,c.card_cost,c.pledge_income,c.subsidy," +
                "c.subsidy_empty ,r.pledge_refund,r.refund_money,r.subsidy_refund " +
                " from v_charge_sum c " +
                "RIGHT  JOIN v_refund_sum  r on  c.charge_ts = r.refund_ts) e  where 1=1 ";
                if(start_ts!=null && start_ts.length()>0){
                    queryChargeSum+="and e.op_ts >= '"+start_ts+"' ";
                }
                if(end_ts!=null && end_ts.length()>0){
                    queryChargeSum+="and e.op_ts <= '"+end_ts+"' ";
                }
                queryChargeSum+="order by e.op_ts";
                List<ChargeSumModel> cardModelList =
                        BaseUtils.mapToBean(ChargeSumModel.class, publicDAO.retrieveBySql(queryChargeSum));
                return cardModelList;
    }

    @Override
    public List<ChargeSumModel> queryChargeSum(String pk_user, String start_ts, String end_ts, String if_user_sum) {
        /** List<ChargeSumModel> chargeSumModelList = queryChargeSum(pk_user,start_ts,end_ts);
        List<ChargeSumModel> chargeSumModels = new ArrayList<ChargeSumModel>();
        boolean mark;
        String operator = "";
        Double subsidy;
        Double subsidy_empty;
        Double pledge_income;
        Double pledge_refund;
        Double charge_money;
        Double card_cost;
        Double refund_money;
        Double practical_money;

        for(ChargeSumModel c : chargeSumModelList){
            ChargeSumModel chargeSumModel = new ChargeSumModel();
            mark = false;
            for(ChargeSumModel cs : chargeSumModels){
                if(cs.getOperator().equals(c.getOperator())){
                    mark = true;
                    continue;
                }
            }
            if(mark){
                    continue;
                }
                subsidy=c.getSubsidy();
                subsidy_empty=c.getSubsidy_empty();
                pledge_income=c.getPledge_income();
                pledge_refund=c.getPledge_refund();
                charge_money=c.getCharge_money();
                card_cost=c.getCard_cost();
                refund_money=c.getRefund_money();
                practical_money=c.getPractical_money();
                for(ChargeSumModel cc : chargeSumModelList){
                    if(c.getOperator().equals(cc.getOperator()) && !c.getPk_charge_record().equals(cc.getPk_charge_record())){
                        operator = cc.getOperator();
                    subsidy+=cc.getSubsidy();
                    subsidy_empty+=cc.getSubsidy_empty();
                    pledge_income+=cc.getPledge_income();
                    pledge_refund+=cc.getPledge_refund();
                    charge_money+=cc.getCharge_money();
                    card_cost+=cc.getCard_cost();
                    refund_money+=cc.getRefund_money();
                    practical_money+=cc.getPractical_money();
                }
            }
            chargeSumModel.setOperator(operator);
            chargeSumModel.setSubsidy(subsidy);
            chargeSumModel.setSubsidy_empty(subsidy_empty);
            chargeSumModel.setPledge_income(pledge_income);
            chargeSumModel.setPledge_refund(pledge_refund);
            chargeSumModel.setCharge_money(charge_money);
            chargeSumModel.setCard_cost(card_cost);
            chargeSumModel.setRefund_money(refund_money);
            chargeSumModel.setPractical_money(practical_money);
            chargeSumModels.add(chargeSumModel);
        } **/
        String queryChargeSum = "select  " +
                "e.op_ts charge_ts, " +
                "IFNULL(e.charge_money,0) charge_money," +
                "IFNULL(e.card_cost,0) card_cost," +
                "IFNULL(e.pledge_income,0) pledge_income," +
                "IFNULL(e.subsidy,0) subsidy," +
                "IFNULL(e.subsidy_empty,0) subsidy_empty," +
                "IFNULL(e.pledge_refund,0) pledge_refund," +
                "IFNULL(e.refund_money,0) refund_money," +
                "IFNULL(e.subsidy_refund,0) subsidy_refund  ," +
                "IFNULL(charge_money,0)+" +
                "IFNULL(card_cost,0)+" +
                "IFNULL(pledge_income,0)-" +
                "IFNULL(pledge_refund,0)-" +
                "IFNULL(refund_money,0)  practical_money, " +
                "u.user_name user_name " +
                "FROM" +
                "(select  " +
                "IFNULL(c.charge_ts,r.refund_ts) op_ts,IFNULL(c.operator,r.operator) operator, c.charge_money,c.card_cost,c.pledge_income,c.subsidy," +
                "c.subsidy_empty ,r.pledge_refund,r.refund_money,r.subsidy_refund           " +
                "from v_charge_sum_p c " +
                "LEFT JOIN v_refund_sum_p  r on  c.charge_ts = r.refund_ts and c.operator = r.operator  " +
                "UNION " +
                "select  " +
                "IFNULL(c.charge_ts,r.refund_ts) op_ts ,IFNULL(c.operator,r.operator) operator,  " +
                "c.charge_money,c.card_cost,c.pledge_income,c.subsidy," +
                "c.subsidy_empty ,r.pledge_refund,r.refund_money,r.subsidy_refund " +
                " from v_charge_sum_p c " +
                "RIGHT  JOIN v_refund_sum_p  r on  c.charge_ts = r.refund_ts and c.operator=r.operator) e left join sm_user u on e.operator = u.pk_user where 1=1 ";
        if(start_ts!=null && start_ts.length()>0){
            queryChargeSum+="and e.op_ts >= '"+start_ts+"' ";
        }
        if(end_ts!=null && end_ts.length()>0){
            queryChargeSum+="and e.op_ts <= '"+end_ts+"' ";
        }
        if(pk_user!=null && pk_user.length()>0){
            queryChargeSum+="and u.pk_user = "+pk_user+" ";
        }
        queryChargeSum+="order by e.op_ts";
        List<ChargeSumModel> cardModelList =
                BaseUtils.mapToBean(ChargeSumModel.class, publicDAO.retrieveBySql(queryChargeSum));
        return cardModelList;
    }

    @Override
    public List<ChargeSumModel> queryChargeSumOne(String pk_user,String start_ts, String end_ts, String if_sum) {
        List<ChargeSumModel> chargeSumModelList = queryChargeSum(pk_user,start_ts,end_ts);
        ChargeSumModel chargeSumModel = new ChargeSumModel();
        Double subsidy = 0.0;
        Double subsidy_empty = 0.0;
        Double pledge_income = 0.0;
        Double pledge_refund = 0.0;
        Double charge_money = 0.0;
        Double card_cost = 0.0;
        Double refund_money = 0.0;
        Double practical_money = 0.0;
        Double subsidy_refund = 0.0;

        for(ChargeSumModel c : chargeSumModelList){
            subsidy+=c.getSubsidy();
            subsidy_empty+=c.getSubsidy_empty();
            pledge_income+=c.getPledge_income();
            pledge_refund+=c.getPledge_refund();
            charge_money+=c.getCharge_money();
            card_cost+=c.getCard_cost();
            refund_money+=c.getRefund_money();
            practical_money+=c.getPractical_money();
            subsidy_refund+=c.getSubsidy_refund();
        }
        chargeSumModel.setSubsidy(subsidy);
        chargeSumModel.setSubsidy_empty(subsidy_empty);
        chargeSumModel.setPledge_income(pledge_income);
        chargeSumModel.setPledge_refund(pledge_refund);
        chargeSumModel.setCharge_money(charge_money);
        chargeSumModel.setCard_cost(card_cost);
        chargeSumModel.setRefund_money(refund_money);
        chargeSumModel.setPractical_money(practical_money);
        chargeSumModel.setSubsidy_refund(subsidy_refund);

        List<ChargeSumModel> chargeSumModels = new ArrayList<ChargeSumModel>();
        chargeSumModels.add(chargeSumModel);
        return chargeSumModels;
    }

    @Override
    public List<ChargeSumModel> queryMealCharge(String start_ts, String end_ts) {
        return chargeSumDAO.queryMealCharge(start_ts,end_ts);
    }

    @Override
    public int updrecordsum(String start_ts, String end_ts) {

        List<String> tableNames = detailAllService.gainTableName(start_ts,end_ts);
        int line2=chargeSumDAO.getline(start_ts,end_ts);
        int line=chargeSumDAO.delrecordsum(start_ts,end_ts);
        if (line==line2){
            String queryMealCharge = "insert into meal_record_sum (meal_ts,pk_device,meal_money,dining_code) ";
            for(int i = 0;i<tableNames.size();i++){
                queryMealCharge += "select substring(meal_ts,1,10 ) 'meal_ts',pk_device,sum(meal_money) meal_money,dining_code from  "+tableNames.get(i)+" group by pk_device ";
                if(i<tableNames.size()-1){
                    queryMealCharge+=" UNION ";
                }
            }
            publicDAO.insert(queryMealCharge);
            chargeSumDAO.delnull();
            return 1;
        }
        return 0;
    }
}
