package com.iandtop.service.impl;

import com.iandtop.dao.MealrecordUpDao;
import com.iandtop.dao.PublicDAO;
import com.iandtop.model.card.CardModel;
import com.iandtop.model.meal.MealRecordModel;
import com.iandtop.service.MealrecordUpService;
import com.iandtop.utils.BaseUtils;
import com.iandtop.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/6/12.
 */
@Service
@Transactional
public class MealrecordUpServiceImpl implements MealrecordUpService {
    @Autowired
    private PublicDAO publicDAO;
    @Autowired
    private MealrecordUpDao mealrecordUpDao;

        @Override
        public List<MealRecordModel> queryorder(MealRecordModel mealRecordModel,String start_ts, String end_ts) throws ParseException {
            List<String> tableNames = getTableName(start_ts,end_ts);//所有需要查的半月表
            String queryMeal = "select " +
                                "c.pk_meal_record,c.card_code," +
                                "s.staff_name,c.pk_staff,c.meal_ts,c.pk_device,c.pk_card,c.staff_code,c.device_code," +
                                "m.device_name,c.meal_money/100 meal_money," +
                                "c.meal_allowance/100 meal_allowance,c.allowance_retain/100 allowance_retain,c.dining_name,c.dining_code,c.meal_way," +
                                "c.meal_cash_money/100 meal_cash_money,c.cash_retain/100 cash_retain,c.money_retain/100 money_retain,c.meal_type,c.device_meal_type from (";
            for(int i = 0;i<tableNames.size();i++){
                //queryMeal += tableNames.get(i);
                queryMeal += "select pk_meal_record,pk_staff,card_code,meal_ts,pk_device,pk_card,staff_code,device_code,meal_money," +
                        "meal_allowance,allowance_retain,dining_name,dining_code,meal_way,meal_cash_money,cash_retain,money_retain,meal_type,device_meal_type " +
                        "from "+tableNames.get(i);
                if(i<tableNames.size()-1){
                    queryMeal+=" UNION ";
                }
            }
            queryMeal+=") c left join db_staff s on c.pk_staff = s.pk_staff " +
                        "left join meal_device m on c.pk_device = m.pk_device where 1=1 ";
            if(mealRecordModel.getCard_code()!=null && mealRecordModel.getCard_code().length()>0){
                queryMeal+="and c.card_code = '"+mealRecordModel.getCard_code()+"' ";
            }
            if(start_ts!=null && start_ts.length()>0){
                queryMeal+="and c.meal_ts >= '"+start_ts+"' ";
            }
            if(end_ts!=null && end_ts.length()>0){
                queryMeal+="and c.meal_ts <= '"+end_ts+"' ";
            }
            queryMeal+= "order by c.meal_ts desc";
            List<Map> maps = publicDAO.retrieveBySql(queryMeal);
            List<MealRecordModel> mealRecordModels = BaseUtils.mapToBean(MealRecordModel.class,maps);

            return mealRecordModels;
    }

    public List<String> getTableName(String start_ts, String end_ts){

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
            start_year = changes(start_year,start_month,start_day).get(0);
            start_month = patchs(changes(start_year,start_month,start_day).get(1));
            start_day = changes(start_year,start_month,start_day).get(2);
            table_name = "meal_record_" + start_year + "_" + start_month + "_" + start_day;
            String name = new String();
            name = table_name;
            tableNames.add(name);
        }
        return tableNames;
    }

    @Override
    public int updateMealrecord(MealRecordModel mealRecordModel,Double money) throws RuntimeException{
        int line;
        int line2;
        int line3=0;

            MealRecordModel mealRecordModel2 = new MealRecordModel();
//            double newmeal_allowance;//新补贴消费
//            double newmeal_cash_money;//新钱包消费
            double newcash_retain;//新现金钱包
            double newallowance_retain;//新补贴钱包
            //还原钱包
            //补贴
            newallowance_retain=(mealRecordModel.getMeal_allowance()+mealRecordModel.getAllowance_retain());
            //现金
            newcash_retain=(mealRecordModel.getMeal_cash_money()+mealRecordModel.getCash_retain());
            //仅现金
            if(mealRecordModel.getDevice_meal_type().equals("0")){
                    double nn=(newcash_retain*100-money*100);//新现金钱包余额 - 更正扣款
                    nn = nn/100;
                    double sum=mealRecordModel.getCash_retain();//老现金钱包余额
                    double nn1=(sum*100-nn*100);//老现金钱包余额减去新现金钱包余额
                    nn1 = nn1/100;
                    //mealRecordModel2.setCash_retain(mealRecordModel.getCash_retain()-nn1);//还原回卡表现金余额
                    mealRecordModel2.setCash_retain(nn);
                    mealRecordModel2.setAllowance_retain(newallowance_retain);
                    mealRecordModel2.setMeal_cash_money(nn1);
                    mealRecordModel2.setMeal_allowance(mealRecordModel.getMeal_allowance());
                    mealRecordModel2.setMoney_retain(mealRecordModel2.getCash_retain()+mealRecordModel2.getAllowance_retain());//总余额赋值= 现金余额+新补贴余额
                    mealRecordModel2.setMeal_money(nn1);//（为负数）
            }
            //仅补贴
            if(mealRecordModel.getDevice_meal_type().equals("10")){
                    double mm=(newallowance_retain*100-money*100);
                    mm = mm/100;
                    double mm1=(mealRecordModel.getAllowance_retain()*100-mm*100);
                    mm1 = mm1/100;
                    mealRecordModel2.setAllowance_retain(mm);
                    mealRecordModel2.setCash_retain(newcash_retain);
                    mealRecordModel2.setMeal_cash_money(mealRecordModel.getMeal_cash_money());
                    mealRecordModel2.setMeal_allowance(mm1);
                    mealRecordModel2.setMoney_retain(mealRecordModel2.getCash_retain()+mealRecordModel2.getAllowance_retain());
                    mealRecordModel2.setMeal_money(mm1);
            }
            //先现金后补贴
            if(mealRecordModel.getDevice_meal_type().equals("20")){
                    double mm = 0.0;
                    double nn=(newcash_retain*100-money*100);//新现金钱包减去更正后的金额
                    nn = nn/100;
                    if (nn<0){
                        mm=newallowance_retain+nn;
                        mealRecordModel2.setCash_retain(0.0);
                        mealRecordModel2.setMeal_cash_money(0.0);
                        //--------------------
                        //老补贴钱包减去 新补贴钱包
                        double mm1=(mealRecordModel.getAllowance_retain()*100-mm*100);
                        mm1 = mm1/100;
                        //老现金钱包减去新现金钱包
                        double nn1=(mealRecordModel.getCash_retain()*100-nn*100);
                        nn1 = nn1/100;
                        //更正后钱包余额
                        double Cash=nn1+nn;
                        //mealRecordModel2.setMeal_cash_money(nn1);
                        mealRecordModel2.setMeal_allowance(mm1);
                        //补贴钱包余额
                        mealRecordModel2.setAllowance_retain(mm);
                        //总余额
                        mealRecordModel2.setMoney_retain(mealRecordModel2.getAllowance_retain()+mealRecordModel2.getCash_retain());
                        //消费余额
                        mealRecordModel2.setMeal_money(mm1+mealRecordModel2.getMeal_cash_money());

                    }else {
                        mm=newallowance_retain;
                        mealRecordModel2.setCash_retain(nn);
                        //mealRecordModel2.setMeal_cash_money(nn);

                        //-------------------------------------
                        //老补贴钱包减去 新补贴钱包
                        double mm1=(mealRecordModel.getAllowance_retain()*100-mm*100);
                        mm1 = mm1/100;
                        //老现金钱包减去新现金钱包
                        double nn1=(mealRecordModel.getCash_retain()*100-nn*100);
                        nn1 = nn1/100;
                        //更正后钱包余额
                        double Cash=nn1+nn;
                        mealRecordModel2.setMeal_cash_money(nn1);
                        mealRecordModel2.setMeal_allowance(mm1);
                        //补贴钱包余额
                        mealRecordModel2.setAllowance_retain(mm);
                        //总余额
                        mealRecordModel2.setMoney_retain(mealRecordModel2.getAllowance_retain()+mealRecordModel2.getCash_retain());
                        //消费余额
                        mealRecordModel2.setMeal_money(mm1+nn1);
                    }
//                    //nn是现金钱包最后余额  mm 是补贴钱包最后余额
//
//                    //老补贴钱包减去 新补贴钱包
//                    double mm1=(mealRecordModel.getAllowance_retain()*100-mm*100);
//                    mm1 = mm1/100;
//                    //老现金钱包减去新现金钱包
//                    double nn1=(mealRecordModel.getCash_retain()*100-nn*100);
//                    nn1 = nn1/100;
//                    //更正后钱包余额
//                    double Cash=nn1+nn;
//                    //mealRecordModel2.setMeal_cash_money(nn1);
//                    mealRecordModel2.setMeal_allowance(mm1);
//                    //补贴钱包余额
//                    mealRecordModel2.setAllowance_retain(mm);
//                    //总余额
//                    mealRecordModel2.setMoney_retain(mealRecordModel2.getAllowance_retain()+mealRecordModel2.getCash_retain());
//                    //消费余额
//                    mealRecordModel2.setMeal_money(mm1+nn1);
            }
            //先补贴后现金
            if(mealRecordModel.getDevice_meal_type().equals("40")){
                double nn;//现金钱包余额
                double mm=newallowance_retain*100-money*100;//补贴钱包余额=新补贴钱包-更正收款
                mm = mm/100;
                if(mm<0){//如果补贴钱包扣至负数
                    nn=newcash_retain+mm;//补贴钱包加上该负数
                    mealRecordModel2.setAllowance_retain(0.0);//补贴钱包余额赋值

                    //----------------------------------
                    double mm1=(mealRecordModel.getAllowance_retain()*100-mm*100);
                    mm1 = mm1/100;
                    //老补贴钱包余额-新补贴钱包余额
                    double nn1=mealRecordModel.getCash_retain()*100-nn*100;
                    nn1 = nn1/100;
                    //更正后钱包余额
                    double allowance =mm1+mm;
                    mealRecordModel2.setMeal_allowance(0.0);//补贴钱包消费
                    mealRecordModel2.setMeal_cash_money(nn1);//现金钱包消费
                    mealRecordModel2.setCash_retain(nn);//现金钱包余额赋值
                    mealRecordModel2.setMoney_retain(mealRecordModel2.getAllowance_retain()+mealRecordModel2.getCash_retain());
                    mealRecordModel2.setMeal_money(allowance+nn1);//消费金额赋值
                }else{
                    nn= newcash_retain;//如果不用扣现金 现金钱包赋上还原钱包值
                    mealRecordModel2.setAllowance_retain(mm);//补贴钱包余额赋值

                    //----------------------------------

                    double mm1=(mealRecordModel.getAllowance_retain()*100-mm*100);
                    mm1 = mm1/100;
                    //老补贴钱包余额-新补贴钱包余额
                    double nn1=mealRecordModel.getCash_retain()*100-nn*100;
                    nn1 = nn1/100;
                    //更正后钱包余额
                    double allowance =mm1+mm;
                    mealRecordModel2.setMeal_allowance(mm1);//补贴钱包消费
                    mealRecordModel2.setMeal_cash_money(nn1);//现金钱包消费
                    mealRecordModel2.setCash_retain(nn);//现金钱包余额赋值
                    mealRecordModel2.setMoney_retain(mealRecordModel2.getAllowance_retain()+mealRecordModel2.getCash_retain());
                    mealRecordModel2.setMeal_money(mm1+nn1);//消费金额赋值
                }
//                double mm1=(mealRecordModel.getAllowance_retain()*100-mm*100);
//                mm1 = mm1/100;
//                //老补贴钱包余额-新补贴钱包余额
//                double nn1=mealRecordModel.getCash_retain()*100-nn*100;
//                nn1 = nn1/100;
//                //更正后钱包余额
//                double allowance =mm1+mm;
//                mealRecordModel2.setMeal_allowance(allowance);//补贴钱包消费
//                mealRecordModel2.setMeal_cash_money(nn1);//现金钱包消费
//                mealRecordModel2.setCash_retain(nn);//现金钱包余额赋值
//                mealRecordModel2.setMoney_retain(mealRecordModel2.getAllowance_retain()+mealRecordModel2.getCash_retain());
//                mealRecordModel2.setMeal_money(allowance+nn1);//消费金额赋值
            }
            mealRecordModel2.setPk_staff(mealRecordModel.getPk_staff());
            mealRecordModel2.setPk_card(mealRecordModel.getPk_card());
            mealRecordModel2.setPk_device(mealRecordModel.getPk_device());
            mealRecordModel2.setCard_code(mealRecordModel.getCard_code());
            mealRecordModel2.setStaff_code(mealRecordModel.getStaff_code());
            mealRecordModel2.setDevice_code(mealRecordModel.getDevice_code());
            mealRecordModel2.setMeal_type("3");
            mealRecordModel2.setMeal_ts(mealRecordModel.getMeal_ts());
            mealRecordModel2.setMeal_way(mealRecordModel.getMeal_way());
            mealRecordModel2.setDining_name(mealRecordModel.getDining_name());
            mealRecordModel2.setDining_code(mealRecordModel.getDining_code());
            mealRecordModel2.setDevice_meal_type(mealRecordModel.getDevice_meal_type());
            String time = mealRecordModel.getMeal_ts();

            try {
                mealRecordModel2.setTable_name(getMealRecordName(DateUtils.tsStrToLong(time)));
            }catch (Exception e){
                e.printStackTrace();
            }
            line=mealrecordUpDao.updateMealrecord(mealRecordModel2);
            if(line>0){
                line3+=1;
                String pk_card;
                pk_card=mealRecordModel.getPk_card();
                CardModel cardModel = mealrecordUpDao.findcard(pk_card);
                cardModel.setPk_card(mealRecordModel.getPk_card());
                /**
                //卡表现金钱包
                double card1=cardModel.getMoney_cash();
                double m1=mealRecordModel2.getCash_retain();
                m1=m1*100;
                double Money_allowance=(card1-m1);
                cardModel.setMoney_cash(Money_allowance);
                //卡表补贴钱包
                double card2=cardModel.getMoney_allowance();
                double m2=mealRecordModel2.getAllowance_retain();
                m2=m2*100;
                double Money_cash=card2-m2;
                cardModel.setMoney_allowance(Money_cash);
                 **/
                //仅现金
                if(mealRecordModel.getDevice_meal_type().equals("0")){
                    //卡表现金钱包
                    double card1=cardModel.getMoney_cash();
                    double m1=mealRecordModel2.getMeal_money();
                    m1=m1*100;
                    double Money_allowance=(card1-m1);
                    cardModel.setMoney_cash(Money_allowance);
                    //卡表补贴钱包
                    double card2=cardModel.getMoney_allowance();
                    cardModel.setMoney_allowance(card2);
                }
                //仅补贴
                if(mealRecordModel.getDevice_meal_type().equals("10")){
                    //卡表现金钱包
                    double card1=cardModel.getMoney_cash();
                    cardModel.setMoney_cash(card1);
                    //卡表补贴钱包
                    double card2=cardModel.getMoney_allowance();
                    double m2=mealRecordModel2.getMeal_money();
                    m2=m2*100;
                    double Money_cash=card2-m2;
                    cardModel.setMoney_allowance(Money_cash);

                }
                //先现金后补贴
                if(mealRecordModel.getDevice_meal_type().equals("20")){
                    //卡表现金钱包
                    double card1=cardModel.getMoney_cash();
                    double m1=mealRecordModel2.getMeal_cash_money();
                    m1=m1*100;
                    double Money_allowance=(card1-m1);
                    cardModel.setMoney_cash(Money_allowance);
                    //卡表补贴钱包

                    double m2=mealRecordModel2.getMeal_allowance();
                    if(m2<0){
                        double card2=cardModel.getMoney_allowance();
                        m2=m2*100;
                        double Money_cash=card2-m2;
                        cardModel.setMoney_allowance(Money_cash);
                    }
                    if(m2==0){
                        double card2=cardModel.getMoney_allowance();
                        cardModel.setMoney_allowance(card2);
                    }

                }
                //先补贴后现金
                if(mealRecordModel.getDevice_meal_type().equals("40")){
                    //卡表补贴钱包
                    double card2=cardModel.getMoney_allowance();
                    double m2=mealRecordModel2.getMeal_allowance();
                    m2=m2*100;
                    double Money_cash=card2-m2;
                    cardModel.setMoney_allowance(Money_cash);

                    //卡表现金钱包

                    double m1=mealRecordModel2.getMeal_cash_money();
                    if(m1<0){
                        double card1=cardModel.getMoney_cash();
                        m1=m1*100;
                        double Money_allowance=(card1-m1);
                        cardModel.setMoney_cash(Money_allowance);
                    }
                    if(m1==0){
                        double card1=cardModel.getMoney_cash();
                        cardModel.setMoney_cash(card1);
                    }
                }


//这里出现错误
                line2=mealrecordUpDao.updatecard(cardModel);
                if(line2>0){
                    line3=line3+1;
                }
            }
        return line3;
    }


    private static List<String> changes(String year, String month, String day){
        List<String> mdd = new ArrayList<String>();
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
        mdd.add(year);
        mdd.add(month);
        mdd.add(day);
        return mdd;
    }
    public static String getMealRecordName(Long timeMillis){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        String date = sdf.format(new Date(timeMillis));
        String year = date.substring(0,4);
        String month = date.substring(5,7);
        String day = date.substring(8);

        day = Integer.parseInt(day) < 15 ? "01" : "15";

        String tableName = "meal_record_" + year + "_" + month + "_" + day;

        return tableName;

    }
    private static String patchs(String month){
        if(month.length()==1){
            month = "0"+month;
        }
        return month;
    }

}
