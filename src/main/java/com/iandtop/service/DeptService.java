package com.iandtop.service;

import com.iandtop.model.CompanyModel;
import com.iandtop.model.DeptModelVO;

import java.util.List;

/**
 * Created by lz on 2017/5/4.
 */
public interface DeptService {

    /**
     * 查询全部部门
     * @return
     */
    List<DeptModelVO> retrieveAll();

    /**
     * 分页查询部门信息,包括条件查询
     * @param vo
     * @return
     */
    List<DeptModelVO> retrieveAllWithPage(DeptModelVO vo);

    /**
     * 单个插入部门数据
     * @param roleModel
     * @return
     */
    int insertByMo(DeptModelVO roleModel);

    /**
     *按部门pk单个更新部门数据
     * @param roleModel 包含pk的vo
     * @return
     */
    int updateByPk(DeptModelVO roleModel);

    /**
     *按部门pk批量删除
     * @param pks
     * @return
     */
    int deleteByPks(List<String> pks);

    /**
     * 部门树
     * @param 
     * @return
     */
    List<CompanyModel> retrieveDeptTree();

    /**
     * 根据编码查询部门信息
     * @return
     */
    DeptModelVO retrieveByCode(DeptModelVO deptModel);

    /**
     * 根据主键查询部门信息
     * @param pk_department
     * @return
     */
    DeptModelVO retrieveByPk(String pk_department);

    DeptModelVO queryretrieve(String department_name);

}
