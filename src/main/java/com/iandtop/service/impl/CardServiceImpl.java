package com.iandtop.service.impl;

import com.iandtop.dao.*;
import com.iandtop.model.StaffModel;
import com.iandtop.model.card.*;
import com.iandtop.model.pub.RequestParamsConstants;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.service.CardService;
import com.iandtop.utils.APIRestResponse;
import com.iandtop.utils.DateUtils;
import com.iandtop.utils.ResponseUtils;
import com.iandtop.utils.RestOperateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lz on 2017/5/13.
 */
@Service
@Transactional
public class CardServiceImpl implements CardService {

    @Autowired
    private PublicDAO publicDAO;

    @Autowired
    private CardDAO cardDAO;

    @Autowired
    private CardParamDAO cardParamDAO;

    @Autowired
    private CardChangeDAO cardChangeDAO;

    @Autowired
    private CardChargeDAO cardChargeDAO;

    @Autowired
    private CardRefundDAO cardRefundDAO;

    @Autowired
    private CardLostDAO cardLostDAO;

    @Override
    public List<CardModel> retrieveAllWithPage(CardModel vo) {

        List<CardModel> result = null;

        boolean issue = RequestParamsConstants.Param_Card_Operation_Type_Issue.equals(vo.getOperator_type());
        boolean charge = RequestParamsConstants.Param_Card_Operation_Type_Charge.equals(vo.getOperator_type());
        boolean refund = RequestParamsConstants.Param_Card_Operation_Type_Refund.equals(vo.getOperator_type());
        boolean browse = RequestParamsConstants.Param_Card_Operation_Type_Browse.equals(vo.getOperator_type());
        boolean returncard = RequestParamsConstants.Param_Card_Operation_Type_Return.equals(vo.getOperator_type());
        boolean lost = RequestParamsConstants.Param_Card_Operation_Type_Lost.equals(vo.getOperator_type());
        boolean change = RequestParamsConstants.Param_Card_Operation_Type_Change.equals(vo.getOperator_type());


        if(vo.getDepartment_code() == null && vo.getDepartment_code() == null){
            return new ArrayList<CardModel>();
        }

        //发卡
        if(issue){
            //card_state === null
            vo.setStaff_type(StaffModel.Staff_Type_OnJob);
            result = cardDAO.retrieveAllNoIssueWithPage(vo);

        }else if(charge || lost || browse){
            //充值、挂失、浏览 card_state === 10,20

            result = cardDAO.retrieveAllNorOrLostWithPage(vo);

        }else if(refund || returncard){
            //退款、退卡 card_state === 10

            result = cardDAO.retrieveAllNormalWithPage(vo);

        }else if(change){
            //补卡 card_state === 20

            result = cardDAO.retrieveAllLostWithPage(vo);

        }

        return result;
    }

    @Override
    public List<CardModel> retrieveAllWithPageCount(CardModel vo) {
        List<CardModel> result = null;

        boolean issue = RequestParamsConstants.Param_Card_Operation_Type_Issue.equals(vo.getOperator_type());
        boolean charge = RequestParamsConstants.Param_Card_Operation_Type_Charge.equals(vo.getOperator_type());
        boolean refund = RequestParamsConstants.Param_Card_Operation_Type_Refund.equals(vo.getOperator_type());
        boolean browse = RequestParamsConstants.Param_Card_Operation_Type_Browse.equals(vo.getOperator_type());
        boolean returncard = RequestParamsConstants.Param_Card_Operation_Type_Return.equals(vo.getOperator_type());
        boolean lost = RequestParamsConstants.Param_Card_Operation_Type_Lost.equals(vo.getOperator_type());
        boolean change = RequestParamsConstants.Param_Card_Operation_Type_Change.equals(vo.getOperator_type());


        if(vo.getDepartment_code() == null && vo.getDepartment_code() == null){
            return new ArrayList<CardModel>();
        }

        //发卡
        if(issue){
            //card_state === null

            result = cardDAO.retrieveAllNoIssueWithPageCount(vo);

        }else if(charge || lost || browse){
            //充值、挂失、浏览 card_state === 10,20

            result = cardDAO.retrieveAllNorOrLostWithPageCount(vo);

        }else if(refund || returncard){
            //退款、退卡 card_state === 10

            result = cardDAO.retrieveAllNormalWithPageCount(vo);

        }else if(change){
            //补卡 card_state === 20

            result = cardDAO.retrieveAllLostWithPageCount(vo);

        }

        return result;
    }

    @Override
    public APIRestResponse insertByVO(CardModel vo) {

        //发卡前校验
        List<Map> cardVOs = publicDAO.retrieveBySql("select * from card_card where card_code='"+vo.getCard_code()+"' and card_state in('10','20')");

        if(cardVOs != null && cardVOs.size() > 0){
            return ResponseUtils.getSuccessAPI(false,"卡号重复",String.valueOf(StatusCodeConstants.Card_Code_Repeat));
        }

        //获取卡参数
//        List<CardParamModel> cardParamVOs = cardParamDAO.retrieveAll();
//        if(cardParamVOs == null || cardParamVOs.size() == 0){
//            return StatusCodeConstants.Fail;
//        }
//        if(cardParamVOs.size() > 1){
//            return StatusCodeConstants.Fail;
//        }

        Integer effective_months = vo.getEffective_months();
//        String password = cardParamVOs.get(0).getPassword();

        //发卡，保存卡信息
//        vo.setPassword(password);
        vo.setCard_state(CardModel.State_Normal);
        vo.setCard_issue_ts(DateUtils.currentDatetime());
        vo.setCard_ineffectived_ts(DateUtils.getDateAfterDateTime(effective_months));
        vo.setSerial(1);
        vo.setMoney_cash(0.0);
        vo.setMoney_allowance(0.0);

        String pk_card = null;
        int line = 0;
        List<Map> oldCards = publicDAO.retrieveBySql("select * from card_card where pk_staff='"+vo.getPk_staff()+"'");

        if(oldCards.size() == 0){
            line = cardDAO.insertByVO(vo);
            if(line>0){
                CardModel model = cardDAO.findPkcard(vo);
                pk_card = model.getPk_card();
            }
        }else if(oldCards != null && oldCards.size() == 1){
           // pk_card = oldCards.get(0).get("pk_card").toString();
            vo.setPk_card(oldCards.get(0).get("pk_card").toString());
            cardDAO.updateByVO(vo);
        }else{
            return ResponseUtils.getSuccessAPI(false,"此人在系统中有多张",String.valueOf(StatusCodeConstants.Psn_Have_Multiple_Card));
        }
        CardModel model = cardDAO.findPkcard(vo);
        pk_card = model.getPk_card();
        if(pk_card == null || pk_card == ""){
            return ResponseUtils.getSuccessAPI(false,"系统内部错误",String.valueOf(StatusCodeConstants.Sys_Error));
        }

        //做发卡记录
        CardChangeRecordModel cardChangeRecordVO = new CardChangeRecordModel();
        cardChangeRecordVO.setPk_card(pk_card);
        cardChangeRecordVO.setPk_staff(vo.getPk_staff());
        cardChangeRecordVO.setOperator(vo.getOperator());
        cardChangeRecordVO.setCard_code(vo.getCard_code());
        cardChangeRecordVO.setStaff_code(vo.getStaff_code());
        cardChangeRecordVO.setOperation_type(cardChangeRecordVO.Operator_Type_Issue);
        cardChangeRecordVO.setOperation_ts(DateUtils.currentDatetime());

        Integer recordResult = cardChangeDAO.saveByVO(cardChangeRecordVO);

        if(recordResult == null || recordResult == 0){
            return ResponseUtils.getSuccessAPI(false,"系统内部错误",String.valueOf(StatusCodeConstants.Sys_Error));
        }

        if(vo.getCard_deposit() != null && vo.getCard_deposit() > 0){
            //卡押金记录
            CardChargeRecordModel chargeRecord = new CardChargeRecordModel();
            chargeRecord.setPk_card(pk_card.toString());
            chargeRecord.setPk_staff(vo.getPk_staff());
            chargeRecord.setOperator(vo.getOperator());
            chargeRecord.setStaff_code(vo.getStaff_code());
            chargeRecord.setCard_code(vo.getCard_code());
            chargeRecord.setCharge_money(vo.getCard_deposit());
            chargeRecord.setCharge_type(CardChargeRecordModel.Charge_Type_Deposit);
            chargeRecord.setCharge_ts(DateUtils.currentDatetime());
            chargeRecord.setCard_batchnum(1);

            recordResult = cardChargeDAO.saveByVO(chargeRecord);
            if(recordResult == null || recordResult == 0){
                return ResponseUtils.getSuccessAPI(false,"系统内部错误",String.valueOf(StatusCodeConstants.Sys_Error));
            }

        }

        if(vo.getCard_costing() != null && vo.getCard_costing() > 0){
            //卡成本记录
            CardChargeRecordModel chargeRecord = new CardChargeRecordModel();
            chargeRecord.setPk_card(pk_card.toString());
            chargeRecord.setPk_staff(vo.getPk_staff());
            chargeRecord.setOperator(vo.getOperator());
            chargeRecord.setStaff_code(vo.getStaff_code());
            chargeRecord.setCard_code(vo.getCard_code());
            chargeRecord.setCharge_money(vo.getCard_deposit());
            chargeRecord.setCharge_type(CardChargeRecordModel.Charge_Type_Cost);
            chargeRecord.setCharge_ts(DateUtils.currentDatetime());
            chargeRecord.setCard_batchnum(1);

            recordResult = cardChargeDAO.saveByVO(chargeRecord);
            if(recordResult == null || recordResult == 0){
                return ResponseUtils.getSuccessAPI(false,"系统内部错误",String.valueOf(StatusCodeConstants.Sys_Error));
            }
        }

        return ResponseUtils.getSuccessAPI(true,"操作成功",String.valueOf(StatusCodeConstants.Success));
    }

    @Override
    public int chargeByVO(CardChargeRecordModel vo) {

        CardModel cardModel = cardDAO.retrieveByPk(vo.getPk_card());

        int result;

        if(cardModel == null){
            return StatusCodeConstants.Not_Found_Card_Info;
        }

        //更新卡表数据
        cardModel.setMoney_cash(cardModel.getMoney_cash()+vo.getCharge_money());
        cardModel.setSerial(cardModel.getSerial()+1);

        result = cardDAO.updateByVO(cardModel);
        if(result < 1){
            return StatusCodeConstants.Fail;
        }

        vo.setMoney_retain(cardModel.getMoney_cash()+cardModel.getMoney_allowance());
        vo.setCard_batchnum(cardModel.getSerial());
        vo.setCharge_ts(DateUtils.currentDatetime());
        vo.setCard_code(cardModel.getCard_code());
        result = cardChargeDAO.saveByVO(vo);

        if(result < 1){
            return StatusCodeConstants.Fail;
        }

        return StatusCodeConstants.Success;
    }

    @Override
    public int refundByVO(CardRefundRecordModel vo) {
        CardModel cardModel = cardDAO.retrieveByPk(vo.getPk_card());

        int result;

        if(cardModel == null){
            return StatusCodeConstants.Not_Found_Card_Info;
        }

        if(cardModel.getMoney_cash() < vo.getRefund_money()){
            return StatusCodeConstants.Money_Not_Enough;
        }
        //更新卡表数据
        cardModel.setMoney_cash(cardModel.getMoney_cash()-vo.getRefund_money());
        cardModel.setSerial(cardModel.getSerial() + 1);

        result = cardDAO.updateByVO(cardModel);

        if(result < 1){
            return StatusCodeConstants.Fail;
        }

        //退款记录
        vo.setMoney_retain(cardModel.getMoney_cash() + cardModel.getMoney_allowance());
        vo.setCard_batchnum(cardModel.getSerial());
        vo.setRefund_ts(DateUtils.currentDatetime());
        vo.setCard_code(cardModel.getCard_code());
        result = cardRefundDAO.saveByVO(vo);

        if(result < 1){
            return StatusCodeConstants.Fail;
        }
        return StatusCodeConstants.Success;
    }

    @Override
    public int lostCard(CardLostRecordModel vo) {
        Integer result = null;
        CardModel cardModel = cardDAO.retrieveByPk(vo.getPk_card());

        if(cardModel == null){
            return StatusCodeConstants.Fail;
        }

        vo.setLost_ts(DateUtils.currentDatetime());
        result = cardLostDAO.saveByVO(vo);

        if(result < 1){
            return StatusCodeConstants.Fail;
        }

        if(CardLostRecordModel.Type_Lost.equals(vo.getType())){
            if(cardModel.getCard_state().equals(CardModel.State_Lost)){
                return StatusCodeConstants.Not_Lost_Repeat;
            }
            cardModel.setCard_state(CardModel.State_Lost);
            result = cardDAO.updateByVO(cardModel);

        }else if(CardLostRecordModel.Type_UnLost.equals(vo.getType())){
            if(cardModel.getCard_state().equals(CardModel.State_Normal)){
                return StatusCodeConstants.Normal_Card_No_Unlost;
            }
            cardModel.setCard_state(CardModel.State_Normal);
            result = cardDAO.updateByVO(cardModel);
        }

        return result != null && result >= 1 ? StatusCodeConstants.Success : StatusCodeConstants.Fail;
    }

    @Override
    public int returnCard(List<CardChangeRecordModel> vos) {

        boolean be_return_cash;
        boolean be_return_allowance;
        boolean be_return_deposit;

        List<CardModel> tempDatas = new ArrayList<CardModel>();
        CardModel temp;

        //TODO 没有事务和异常捕捉，迫不得已为之,退卡前校验
        for (int i = 0,j=vos.size(); i<j; i++) {

            temp = cardDAO.retrieveByPk(vos.get(i).getPk_card());
            if(temp == null){
                return StatusCodeConstants.Fail;
            }
        }

        CardRefundRecordModel refundRecordModel;
        CardChangeRecordModel changeRecordModel;
        Integer result;
        for (int i = 0,j=vos.size(); i<j; i++) {

            be_return_cash = vos.get(i).isBe_return_cash();
            be_return_allowance = vos.get(i).isBe_return_allowance();
            be_return_deposit = vos.get(i).isBe_return_deposit();

            temp = cardDAO.retrieveByPk(vos.get(i).getPk_card());

            //退押金记录
            if(be_return_deposit && temp.getCard_deposit() > 0 ){
                refundRecordModel = new CardRefundRecordModel();
                refundRecordModel.setOperator(vos.get(i).getOperator());
                refundRecordModel.setPk_card(vos.get(i).getPk_card());
                refundRecordModel.setPk_staff(vos.get(i).getPk_staff());
                refundRecordModel.setCard_code(vos.get(i).getCard_code());
                refundRecordModel.setStaff_code(vos.get(i).getStaff_code());
                refundRecordModel.setRefund_money(temp.getCard_deposit());
                refundRecordModel.setRefund_type(CardRefundRecordModel.Refund_Type_Deposit);
                refundRecordModel.setRefund_ts(DateUtils.currentDatetime());
                result = cardRefundDAO.saveByVO(refundRecordModel);
                if(result < 1){
                    return StatusCodeConstants.Fail;
                }

                temp.setCard_deposit(0.0);

            }

            //退补贴记录
            if(be_return_allowance && temp.getMoney_allowance() > 0){
                refundRecordModel = new CardRefundRecordModel();
                refundRecordModel.setOperator(vos.get(i).getOperator());
                refundRecordModel.setPk_card(vos.get(i).getPk_card());
                refundRecordModel.setPk_staff(vos.get(i).getPk_staff());
                refundRecordModel.setCard_code(vos.get(i).getCard_code());
                refundRecordModel.setStaff_code(vos.get(i).getStaff_code());
                refundRecordModel.setRefund_money(temp.getMoney_allowance());
                refundRecordModel.setRefund_type(CardRefundRecordModel.Refund_Type_Allowance);
                refundRecordModel.setRefund_ts(DateUtils.currentDatetime());
                result = cardRefundDAO.saveByVO(refundRecordModel);
                if(result < 1){
                    return StatusCodeConstants.Fail;
                }

                temp.setMoney_allowance(0.0);
            }

            //退现金记录
            if(be_return_cash && temp.getMoney_cash() > 0){
                refundRecordModel = new CardRefundRecordModel();
                refundRecordModel.setPk_card(vos.get(i).getPk_card());
                refundRecordModel.setOperator(vos.get(i).getOperator());
                refundRecordModel.setPk_staff(vos.get(i).getPk_staff());
                refundRecordModel.setCard_code(vos.get(i).getCard_code());
                refundRecordModel.setStaff_code(vos.get(i).getStaff_code());
                refundRecordModel.setRefund_money(temp.getMoney_cash());
                refundRecordModel.setRefund_type(CardRefundRecordModel.Refund_Type_Cash);
                refundRecordModel.setRefund_ts(DateUtils.currentDatetime());
                result = cardRefundDAO.saveByVO(refundRecordModel);

                if(result < 1){
                    return StatusCodeConstants.Fail;
                }

                temp.setMoney_cash(0.0);
            }

            //退卡记录
            changeRecordModel = vos.get(i);
            changeRecordModel.setOperation_type(CardChangeRecordModel.getOperator_Type_Return());
            changeRecordModel.setOperation_ts(DateUtils.currentDatetime());
            result = cardChangeDAO.saveByVO(changeRecordModel);
            if(result < 1){
                return StatusCodeConstants.Fail;
            }

            //更新卡表数据
            temp.setCard_code("");
            temp.setSerial(0);
            temp.setCard_state(CardModel.State_Retuen);
            result = cardDAO.updateByVO(temp);
            if(result < 1){
                return StatusCodeConstants.Fail;
            }
        }

        return StatusCodeConstants.Success;
    }

    @Override
    public int changeCard(CardChangeRecordModel vo) {

        Integer result;

        CardModel cardModel = cardDAO.retrieveByPk(vo.getPk_card());

        //补卡前校验
        if(cardModel == null || CardModel.State_Normal.equals(cardModel.getCard_state())){
            return StatusCodeConstants.Fail;
        }

        //查询新卡号是否使用
        String sql = "select pk_card from card_card where card_code='"+vo.getCardcode_new()+"'";
        List<Map> temp = publicDAO.retrieveBySql(sql);
        if(temp.size() > 0){
            return StatusCodeConstants.Fail;
        }

        //补卡记录
        vo.setPk_card(cardModel.getPk_card());
        vo.setPk_staff(cardModel.getPk_staff());
        vo.setOperation_type(CardChangeRecordModel.getOperator_Type_Change());
        vo.setOperation_ts(DateUtils.currentDatetime());
        vo.setOld_serial_number(cardModel.getSerial());
        vo.setMoney_remain(cardModel.getMoney_cash());

        result = cardChangeDAO.saveByVO(vo);
        if(result < 1){
            return StatusCodeConstants.Fail;
        }

        //卡成本记录
        if(vo.getCard_cost() !=null && vo.getCard_cost() > 0){
            CardChargeRecordModel chargeRecordModel = new CardChargeRecordModel();
            chargeRecordModel.setStaff_code(vo.getStaff_code());
            chargeRecordModel.setPk_card(cardModel.getPk_card());
            chargeRecordModel.setPk_staff(cardModel.getPk_staff());
            chargeRecordModel.setCharge_money(vo.getCard_cost());
            chargeRecordModel.setCharge_type(CardChargeRecordModel.Charge_Type_Cost);
            chargeRecordModel.setCard_code(vo.getCardcode_new());
            chargeRecordModel.setCharge_ts(DateUtils.currentDatetime());
            chargeRecordModel.setOperator(vo.getOperator());
            chargeRecordModel.setCard_batchnum(cardModel.getSerial()+1);
            chargeRecordModel.setMoney_retain(cardModel.getMoney_cash());
            result = cardChargeDAO.saveByVO(chargeRecordModel);
            if(result < 1){
                return StatusCodeConstants.Fail;
            }
        }

        //更新卡表数据
        cardModel.setCard_code(vo.getCardcode_new());
        cardModel.setCard_costing(vo.getCard_cost());
        cardModel.setCard_deposit(vo.getCard_deposit());
        cardModel.setCard_state(CardModel.State_Normal);
        cardModel.setSerial(cardModel.getSerial()+1);
        result = cardDAO.updateByVO(cardModel);
        if(result < 1){
            return StatusCodeConstants.Fail;
        }

        return StatusCodeConstants.Success;
    }

    @Override
    public int updatePwd(CardModel vo) {
        return cardDAO.updatePwd(vo);
    }

    @Override
    public int updateCardInfo(CardModel vo) {
        return cardDAO.updateByVO(vo);
    }

    /*@Override
    public List<CardModel> selectByPkStaff(String pk_staff) {
        return cardDAO.selectByPkStaff(pk_staff);
    }*/

    @Override
    public CardModel selectByPkStaff(String pk_staff) {
        return cardDAO.selectByPkStaff(pk_staff);
    }

}
