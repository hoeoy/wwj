package com.iandtop.dao;

import com.iandtop.model.card.CardModel;

import java.util.List;

/**
 * Created by lz on 2017/5/12.
 */
public interface CardDAO {

    /**
     * 查询未发卡人员
     * @param vo
     * @return
     */
    List<CardModel> retrieveAllNoIssueWithPage(CardModel vo);

    /**
     * 查询未发卡人员总数量
     * @param vo
     * @return
     */
    List<CardModel> retrieveAllNoIssueWithPageCount(CardModel vo);

    /**
     * 查询正常或挂失状态的卡
     * @param vo
     * @return
     */
    List<CardModel> retrieveAllNorOrLostWithPage(CardModel vo);

    /**
     * 查询正常或挂失状态的卡总数量
     * @param vo
     * @return
     */
    List<CardModel> retrieveAllNorOrLostWithPageCount(CardModel vo);

    /**
     * 查询正常状态的卡
     * @param vo
     * @return
     */
    List<CardModel> retrieveAllNormalWithPage(CardModel vo);

    /**
     * 查询正常状态卡的总数量
     * @param vo
     * @return
     */
    List<CardModel> retrieveAllNormalWithPageCount(CardModel vo);

    /**
     * 查询挂失状态的卡
     * @param vo
     * @return
     */
    List<CardModel> retrieveAllLostWithPage(CardModel vo);

    /**
     * 查询挂失状态卡的总数量
     * @param vo
     * @return
     */
    List<CardModel> retrieveAllLostWithPageCount(CardModel vo);

    /**
     * 卡表中插入数据,发卡的时候用
     * @param vo
     * @return
     */
    int insertByVO(CardModel vo);

    CardModel findPkcard(CardModel vo);

    /**
     * 更新卡表数据
     * @param vo
     * @return
     */
    int updateByVO(CardModel vo);

    /**
     * 根据pk_card查询卡表中对应的数据
     * @param pk_card
     * @return
     */
    CardModel retrieveByPk(String pk_card);

    CardModel selectByPkStaff(String pk_staff);
    //List<CardModel> selectByPkStaff(String pk_staff);

    int updatePwd(CardModel vo);
}
