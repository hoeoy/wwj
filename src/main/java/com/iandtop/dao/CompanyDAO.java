package com.iandtop.dao;

import com.iandtop.model.CompanyModel;
import com.iandtop.model.DeptModelVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CompanyDAO
 */
public interface CompanyDAO {
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



