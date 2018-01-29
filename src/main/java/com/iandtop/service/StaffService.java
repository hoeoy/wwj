package com.iandtop.service;

import com.iandtop.model.StaffModel;

import java.util.List;

public interface StaffService {
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
     *  excel批量插入
     */
    int insertbyexcel(StaffModel model);

    /**
     * 按pk修改商户
     */
    int batchupdate(StaffModel model);

    /**
     * 批量修改商户用部门code查询人员
     */
   List<StaffModel> findAllForupd(StaffModel model);
}


