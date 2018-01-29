package com.iandtop.service.impl;

import com.iandtop.dao.DiningDAO;
import com.iandtop.model.meal.DiningModel;
import com.iandtop.model.meal.MerchantModel;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.service.DiningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lz on 2017/5/19.
 */
@Service
@Transactional
public class DiningServiceImpl implements DiningService {

    @Autowired
    private DiningDAO diningDAO;

    @Override
    public List<DiningModel> retrieveAll() {
        return diningDAO.retrieveAll();
    }

    @Override
    public List<DiningModel> retrieveAllWithPage(DiningModel vo) {
        return diningDAO.retrieveAllWithPage(vo);
    }

    @Override
    public int insertByMo(DiningModel model) {

        //校验，编码不能重复
        List<DiningModel> checkResult = diningDAO.retrieveByCode(model.getDining_code());
        if(checkResult != null && checkResult.size() > 0){
            return StatusCodeConstants.Code_Repeat;
        }

        Integer result = diningDAO.insertByMo(model);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }

    @Override
    public int updateByPk(DiningModel model) {
        Integer result = diningDAO.updateByPk(model);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }

    @Override
    public int deleteByPks(List<String> pks) {
        Integer result = diningDAO.deleteByPks(pks);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }

    @Override
    public List<DiningModel> retrieveByCode(String merchant_code) {
        return diningDAO.retrieveByCode(merchant_code);
    }

    @Override
    public List<DiningModel> retrieveByPk(String pk) {
        return diningDAO.retrieveByPk(pk);
    }
}
