package com.iandtop.service.impl;

import com.iandtop.dao.TaskLogDAO;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.model.task.TaskLogModel;
import com.iandtop.service.TaskLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lz on 2017/7/20.
 */
@Service
public class TaskLogsServiceImpl implements TaskLogService {

    @Autowired
    private TaskLogDAO taskLogDAO;

    @Override
    public List<TaskLogModel> retrieveAllWithPage(TaskLogModel vo) {

        List<TaskLogModel> logs = taskLogDAO.retrieveAllWithPage(vo);

        for (TaskLogModel log : logs) {
            log.setResult(log.getResult().equals("Y") ? "成功" : "失败");
        }

        return logs;
    }

    @Override
    public List<TaskLogModel> retrieveAll() {
        return taskLogDAO.retrieveAll();
    }

    @Override
    public int deleteByPks(List<String> pks) throws RuntimeException {
        Integer result = taskLogDAO.deleteByPks(pks);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }
}
