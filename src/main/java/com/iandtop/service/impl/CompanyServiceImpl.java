package com.iandtop.service.impl;

import com.iandtop.dao.CompanyDAO;
import com.iandtop.model.CompanyModel;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDAO companyDAO;


    @Override
    public List<CompanyModel> retrieveAll() {
        return companyDAO.retrieveAll();
    }

    @Override
    public List<CompanyModel> retrieveAllWithPage(CompanyModel vo) {
        return companyDAO.retrieveAllWithPage(vo);
    }

    @Override
    public int insertByMo(CompanyModel model) {

        //插入前校验
        List<CompanyModel> temp = companyDAO.retrieveByCode(model.getCompany_code());
        if(temp.size() > 0){
            return StatusCodeConstants.Code_Repeat;
        }

        Integer result = companyDAO.insertByMo(model);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }

    @Override
    public int updateByPk(CompanyModel model) {

        Integer result = companyDAO.updateByPk(model);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }

    @Override
    public int deleteByPks(List<String> pks) {

        Integer result = companyDAO.deleteByPks(pks);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }

    @Override
    public List<CompanyModel> retrieveByCode(String company_code) {
        return companyDAO.retrieveByCode(company_code);
    }
}


