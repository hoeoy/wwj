package com.iandtop.dao;

import com.iandtop.model.task.TaskConfigModel;
import com.iandtop.model.task.TaskLogModel;

import java.util.List;

/**
 * RoleDAO
 */
public interface TaskLogDAO {

    List<TaskLogModel> retrieveAllWithPage(TaskLogModel vo);

    /**
     * 查询全部
     * @return
     */
    List<TaskLogModel> retrieveAll();


    /**
     * 插入数据
     * @param model
     * @return
     */
    int insertByMo(TaskLogModel model);

    /**
     * 更新数据
     * @param model
     * @return
     */
    int updateByPk(TaskLogModel model);

    /**
     * 按pk批量删除
     * @param pks
     * @return
     */
    int deleteByPks(List<String> pks);

}



