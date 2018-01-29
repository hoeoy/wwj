package com.iandtop.dao;

import com.iandtop.model.system.RoleModel;
import com.iandtop.model.task.TaskConfigModel;

import java.util.List;

/**
 * RoleDAO
 */
public interface TaskConfigDAO {
    /**
     * 查询全部
     * @return
     */
    List<TaskConfigModel> retrieveAll();


    /**
     * 插入数据
     * @param model
     * @return
     */
    int insertByMo(TaskConfigModel model);

    /**
     * 更新数据
     * @param model
     * @return
     */
    int updateByPk(TaskConfigModel model);

    /**
     * 按pk批量删除
     * @param pks
     * @return
     */
    int deleteByPks(List<String> pks);

}



