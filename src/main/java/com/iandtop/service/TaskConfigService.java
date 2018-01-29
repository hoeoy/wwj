package com.iandtop.service;

import com.iandtop.model.system.RoleModel;
import com.iandtop.model.task.TaskConfigModel;
import com.iandtop.utils.APIRestResponse;

import java.util.List;

/**
 * Created by lz on 2017/7/20.
 */
public interface TaskConfigService {

    APIRestResponse Sync(String company_code, String url, String user, String password) throws Exception;

    APIRestResponse SyncNow() throws Exception;

    int insertByMo(TaskConfigModel model) throws RuntimeException;

    int updateByPk(TaskConfigModel model) throws RuntimeException;

    int deleteByPks(List<String> pks) throws RuntimeException;

    List<TaskConfigModel> retrieveAll();

}
