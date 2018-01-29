package com.iandtop.dao;

import com.iandtop.model.DeptModelVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lz on 2017/5/12.
 */
public interface DeptDAO {

    /**
     * 查询全部部门
     * @return
     */
    List<DeptModelVO> retrieveAll();

    /**
     * 分页查询,包括条件查询
     * @param vo
     * @return
     */
    List<DeptModelVO> retrieveAllWithPage(DeptModelVO vo);

    /**
     * 插入部门数据
     * @param roleModel
     * @return
     */
    int insertByMo(DeptModelVO roleModel);

    /**
     * 更新部门数据
     * @param roleModel
     * @return
     */
    int updateByPk(DeptModelVO roleModel);

    /**
     * 按pk批量删除
     * @param pks
     * @return
     */
    int deleteByPks(List<String> pks);

    /**
     * 按公司查询部门信息
     * @param company_code
     * @return
     */
    List<DeptModelVO> retrieveByCompany(String company_code);

    /**
     * 根据编码查询部门信息
     * @return
     */
    DeptModelVO retrieveByCode(DeptModelVO deptModel);

    DeptModelVO retrieveByDeptCode(@Param("department_code") String department_code);

    /**
     * 根据主键查询部门信息
     * @param pk_department
     * @return
     */
    DeptModelVO retrieveByPk(String pk_department);

    DeptModelVO queryretrieve(String department_name);

    int addBatch(List<DeptModelVO> modelVOs);
}
