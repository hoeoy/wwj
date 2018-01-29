package com.iandtop.service.impl;

import com.iandtop.dao.MerchantDAO;
import com.iandtop.model.meal.MerchantModel;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lz on 2017/5/19.
 */
@Service
@Transactional
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantDAO merchantDAO;

    @Override
    public List<MerchantModel> retrieveAll() {
        return merchantDAO.retrieveAll();
    }

    @Override
    public List<MerchantModel> retrieveAllWithPage(MerchantModel vo) {
        return merchantDAO.retrieveAllWithPage(vo);
    }

    @Override
    public int insertByMo(MerchantModel model) {

        //校验  编码不能重复
        List<MerchantModel> checkResult = merchantDAO.retrieveByCode(model.getMerchant_code());
        if(checkResult != null && checkResult.size() > 0){
            return StatusCodeConstants.Code_Repeat;
        }

        Integer result = merchantDAO.insertByMo(model);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }

    @Override
    public int updateByPk(MerchantModel model) {
        Integer result = merchantDAO.updateByPk(model);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }

    @Override
    public int deleteByPks(List<String> pks) {
        Integer result = merchantDAO.deleteByPks(pks);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }

    @Override
    public List<MerchantModel> retrieveByCode(String merchant_code) {
        return merchantDAO.retrieveByCode(merchant_code);
    }

    @Override
    public List<MerchantModel> retrieveByPk(String pk) {
        return merchantDAO.retrieveByPk(pk);
    }
}
