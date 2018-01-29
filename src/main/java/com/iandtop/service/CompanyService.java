package com.iandtop.service;

import com.iandtop.model.CompanyModel;

import java.util.List;

public interface CompanyService {
    /**
     * 查询全部
     * @return
     */
    List<CompanyModel> retrieveAll();

    /**
     * 分页查询,包括条件查询
     * @param vo
     * @return
     */
    List<CompanyModel> retrieveAllWithPage(CompanyModel vo);

    /**
     * 插入数据
     * @param model
     * @return
     */
    int insertByMo(CompanyModel model);

    /**
     * 更新数据
     * @param model
     * @return
     */
    int updateByPk(CompanyModel model);

    /**
     * 按pk批量删除
     * @param pks
     * @return
     */
    int deleteByPks(List<String> pks);

    /**
     * 按公司编码查询
     * @param company_code
     * @return
     */
    List<CompanyModel> retrieveByCode(String company_code);
}


