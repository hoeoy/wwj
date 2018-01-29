package com.iandtop.service;

import com.iandtop.model.card.*;
import com.iandtop.utils.APIRestResponse;

import java.util.List;

/**
 * Created by lz on 2017/5/13.
 */
public interface CardService {

    List<CardModel> retrieveAllWithPage(CardModel vo);

    List<CardModel> retrieveAllWithPageCount(CardModel vo);

    APIRestResponse insertByVO(CardModel vo);

    int chargeByVO(CardChargeRecordModel vo);

    int refundByVO(CardRefundRecordModel vo);

    int lostCard(CardLostRecordModel vo);

    int returnCard(List<CardChangeRecordModel> vos);

    int changeCard(CardChangeRecordModel vo);

    int updatePwd(CardModel vo);

    int updateCardInfo(CardModel vo);

    CardModel selectByPkStaff(String pk_staff);
    //List<CardModel> selectByPkStaff(String pk_staff);
}
