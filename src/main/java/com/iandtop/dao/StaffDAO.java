package com.iandtop.dao;

import com.iandtop.model.DeptModelVO;
import com.iandtop.model.StaffModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lz on 2017/5/12.
 */
public interface StaffDAO {

    /**
     * 查询全部
     * @return
     */
    List<StaffModel> retrieveAll();

    /**
     * 分页查询,包括条件查询
     * @param vo
     * @return
     */
    List<StaffModel> retrieveAllWithPage(StaffModel vo);
    List<StaffModel> retrieveAllWithPageCount(StaffModel vo);

    /**
     * 插入数据
     * @param model
     * @return
     */
    int insertByMo(StaffModel model);

    /**
     * 更新数据
     * @param model
     * @return
     */
    int updateByPk(StaffModel model);

    /**
     * 按pk批量删除
     * @param pks
     * @return
     */
    int deleteByPks(List<String> pks);

    /**
     * 按编码查询
     * @param staff_code
     * @return
     */
    List<StaffModel> retrieveByCode(String staff_code);

    /**
     *  excel批量插入
     */
    int insertbyexcel(StaffModel model);

    List<StaffModel> retrieveByIDCard(@Param("wwjid") String wwjid);

    int addBatch(List<StaffModel> modelVOs);

    /**
     *
     * 按pk批量修改商户
     *
     */
    int batchupdate(StaffModel model);


    List<StaffModel> findAllForupd(StaffModel model);

    //按spstaff查询

    List<StaffModel> retrieveByPkstaff(String pk_staff);


}
