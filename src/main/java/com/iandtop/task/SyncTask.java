package com.iandtop.task;

import com.iandtop.dao.StaffDAO;
import com.iandtop.dao.TaskConfigDAO;
import com.iandtop.model.StaffModel;
import com.iandtop.model.sync.SyncPsnModel;
import com.iandtop.model.task.TaskConfigModel;
import com.iandtop.service.StaffService;
import com.iandtop.service.TaskConfigService;
import com.iandtop.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;

/**
 * Created by lz on 2017/7/20.
 */
@Component
public class SyncTask {

    @Autowired
    private TaskConfigDAO taskConfigDAO;

    @Autowired
    private TaskConfigService taskConfigService;


    @Scheduled(cron = "0 0/5 * * * ? ") // 间隔5秒执行
    public void taskCycle() throws Exception {
//        System.out.println("使用SpringMVC框架配置定时任务");

        List<TaskConfigModel> models = taskConfigDAO.retrieveAll();

        if(models != null && models.size() == 1){

            Integer frequency = models.get(0).getFrequency();
            String last_time = models.get(0).getLast_time();
            String currentDateTime = DateUtils.currentDatetime();

            try {
                Integer result = Integer.valueOf(DateUtils.startTimeGoEndTimeToMinute(last_time,currentDateTime));

                if(result >= frequency){

                    models.get(0).setLast_time(currentDateTime);
                    taskConfigDAO.updateByPk(models.get(0));

                    APIRestResponse syncResult = taskConfigService.Sync(models.get(0).getCompany_code(),models.get(0).getUrl(),models.get(0).getUser(),models.get(0).getPassword());

                    if(syncResult.isSuccess() == false){
                        models.get(0).setLast_time(currentDateTime);
                        taskConfigDAO.updateByPk(models.get(0));
                    }

                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

    }

}
