package com.iandtop.service.impl;


import com.iandtop.dao.CardParamDAO;
import com.iandtop.model.card.CardParamModel;
import com.iandtop.service.CardParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lz on 2017/5/4.
 */
@Service
@Transactional
public class CardParamServiceImpl implements CardParamService {

    @Autowired
    private CardParamDAO dao;

    @Override
    public List<CardParamModel> retrieveAll() {
        return dao.retrieveAll();
    }

    public List<CardParamModel> retrieveAllWithPage(CardParamModel vo) {
        return dao.retrieveAllWithPage(vo);
    }

    @Override
    public int insertByMo(CardParamModel vo) {
        return dao.insertByMo(vo);
    }

    @Override
    public int updateByPk(CardParamModel vo) {
        return dao.updateByPk(vo);
    }

    @Override
    public int deleteByPks(List<String> pks) {
        return dao.deleteByPks(pks);
    }

    @Override
    public List<CardParamModel> retrieveByCompany(String company_code) {
        return dao.retrieveByCompany(company_code);
    }

}
