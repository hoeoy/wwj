package com.iandtop.service.impl;/*package com.iandtop.service.impl;

import com.iandtop.dao.CardChargeDAO;
import com.iandtop.dao.CardDAO;
import com.iandtop.dao.MealAllowanceDAO;
import com.iandtop.model.card.CardChargeRecordModel;
import com.iandtop.model.card.CardModel;
import com.iandtop.model.meal.MealAllowanceModel;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.service.MealAllowanceNumService;
import com.iandtop.service.MealAllowanceService;
import com.iandtop.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

*//**
 * @author Klin
 * @description 描述
 * @create 2017/5/24 0024
 * @Version 1.0
 *//*
@Service
public class MealAllowanceServiceImpl implements MealAllowanceService {

    @Autowired
    private MealAllowanceDAO allowanceDAO;

    @Autowired
    private MealAllowanceNumService numService;

    @Autowired
    private CardDAO cardDAO;


    @Autowired
    private CardChargeDAO chargeDAO;

    *//**
     * @author Klin
     * @date 2017/5/26 0026
     * @parm
     * @result
     * @description 插入
     *//*
    public int save(MealAllowanceModel models) {


//        if (cardModel != null) {
        *//**
         * 判断补贴类型
         *//*
        if (models.getAllowance_type() == 1) {

            models.setAllowance_type(1);
        } else {
            models.setAllowance_type(0);
        }


        //插入批次明细
        if (allowanceDAO.save(models) <= 0) {
            return StatusCodeConstants.Fail;
        }
        //次数
//            cardModel.setSerial(1 + cardModel.getSerial());

        //更新
//            if (cardDAO.updateByVO(cardModel) > 0) {
////                models.setPk_card(cardModel.getCard_code());
//
//                CardChargeRecordModel recordModel = new CardChargeRecordModel();
//                recordModel.setPk_card(cardModel.getPk_card());
//                recordModel.setPk_staff(cardModel.getPk_staff());
//                recordModel.setCard_code(cardModel.getCard_code());
//                recordModel.setStaff_code(cardModel.getStaff_code());
//                recordModel.setCharge_money(cardModel.getMoney_allowance());
//                recordModel.setCharge_type(models.getAllowance_type() == 1 ? "5" : "6");
//                recordModel.setCharge_ts(DateUtils.currentDatetime());
//                recordModel.setCard_batchnum(cardModel.getSerial());
//
//                //判断插入流水
//                if (chargeDAO.saveByVO(recordModel) < 0)
//                    return StatusCodeConstants.Fail;
//
//
        return StatusCodeConstants.Success;
//            } else {
//                return StatusCodeConstants.Fail;
//            }
//        } else {
//            return StatusCodeConstants.Not_Found_Card_Info;
//        }
    }

    public List<MealAllowanceModel> findData(MealAllowanceModel model) {
        return allowanceDAO.findData(model);
    }

    public int isok(String pkallowancenum) {
        int status = 0;
        MealAllowanceModel mealAllowanceModel = new MealAllowanceModel();
        mealAllowanceModel.setPk_allowance_num(pkallowancenum);
        List<MealAllowanceModel> mealAllowanceList = findData(mealAllowanceModel);
        if (mealAllowanceList.size() > 0) {
            for (MealAllowanceModel allowanceTmp : mealAllowanceList) {
                CardModel cardModel = allowanceDAO.retrieveByPk(allowanceTmp.getPk_card());
                if (cardModel != null) {

                    //累加
                    if (allowanceTmp.getAllowance_type() == 1) {
                        cardModel.setMoney_allowance((cardModel.getMoney_allowance() + allowanceTmp.getMoney_allowance()));
                    } else {
                        cardModel.setMoney_allowance(allowanceTmp.getMoney_allowance());
                    }
                    //次数
                    cardModel.setSerial(1 + cardModel.getSerial());

                    if (cardDAO.updateByVO(cardModel) > 0) {
                        CardChargeRecordModel recordModel = new CardChargeRecordModel();
                        recordModel.setPk_card(cardModel.getPk_card());
                        recordModel.setPk_staff(cardModel.getPk_staff());
                        recordModel.setCard_code(cardModel.getCard_code());
                        recordModel.setStaff_code(cardModel.getStaff_code());
                        recordModel.setCharge_money(cardModel.getMoney_allowance());
                        recordModel.setCharge_type(allowanceTmp.getAllowance_type() == 1 ? "5" : "6");
                        recordModel.setCharge_ts(DateUtils.currentDatetime());
                        recordModel.setCard_batchnum(cardModel.getSerial());
                        //判断插入流水
                        if (chargeDAO.saveByVO(recordModel) < 0) {
                            status = StatusCodeConstants.Fail;
                        }
                    }
                }
                status = StatusCodeConstants.Success;
            }
        }
        return status;
    }
}*/

