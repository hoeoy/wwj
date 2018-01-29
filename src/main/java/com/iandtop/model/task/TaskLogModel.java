package com.iandtop.model.task;

import com.iandtop.model.pub.SuperModel;

/**
 * Created by lz on 2017/7/20.
 */
public class TaskLogModel extends SuperModel {

    private String pk_task_log;
    private String log_ts;
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getPk_task_log() {
        return pk_task_log;
    }

    public void setPk_task_log(String pk_task_log) {
        this.pk_task_log = pk_task_log;
    }

    public String getLog_ts() {
        return log_ts;
    }

    public void setLog_ts(String log_ts) {
        this.log_ts = log_ts;
    }

    @Override
    public String getTableName() {
        return null;
    }
}
