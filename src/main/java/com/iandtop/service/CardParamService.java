package com.iandtop.service;

import com.iandtop.model.card.CardParamModel;

import java.util.List;

/**
 * Created by lz on 2017/5/4.
 */
public interface CardParamService {

    /**
     * 查询全部
     * @return
     */
    List<CardParamModel> retrieveAll();

    /**
     * 分页查询,包括条件查询
     * @param vo
     * @return
     */
    List<CardParamModel> retrieveAllWithPage(CardParamModel vo);

    /**
     * 插入数据
     * @param vo
     * @return
     */
    int insertByMo(CardParamModel vo);

    /**
     * 更新数据
     * @param vo
     * @return
     */
    int updateByPk(CardParamModel vo);

    /**
     * 按pk批量删除
     * @param pks
     * @return
     */
    int deleteByPks(List<String> pks);

    /**
     * 按公司查询
     * @param company_code
     * @return
     */
    List<CardParamModel> retrieveByCompany(String company_code);

}
