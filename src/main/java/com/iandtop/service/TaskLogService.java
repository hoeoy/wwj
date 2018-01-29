package com.iandtop.service;

import com.iandtop.model.StaffModel;
import com.iandtop.model.task.TaskLogModel;

import java.util.List;

/**
 * Created by lz on 2017/7/20.
 */
public interface TaskLogService {

    /**
     * 分页查询,包括条件查询
     * @param vo
     * @return
     */
    List<TaskLogModel> retrieveAllWithPage(TaskLogModel vo);

    List<TaskLogModel> retrieveAll();

    int deleteByPks(List<String> pks) throws RuntimeException;

}
