package com.iandtop.service.impl;

import com.iandtop.dao.JurisdictionDAO;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.model.system.JurisdictionModel;
import com.iandtop.service.JurisdictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lz on 2017/5/18.
 */
@Service
@Transactional
public class JurisdictionServiceImpl implements JurisdictionService {

    @Autowired
    private JurisdictionDAO dao;

    @Override
    public int insertByVO(JurisdictionModel model) {

        Integer result = dao.insertByVO(model);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }

    @Override
    public int updateByVO(JurisdictionModel model) {
        Integer result = dao.updateByVO(model);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }

    @Override
    public int insertByVOs(List<JurisdictionModel> models) {
        Integer result = dao.insertByVOs(models);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }

    @Override
    public int deleteByPks(List<String> pks) {

        Integer result = dao.deleteByPks(pks);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }
}
