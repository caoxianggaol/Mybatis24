package com.kaishengit.crm.jobstest;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 使用：实现Job接口  来自org.quartz.Job;
 * 并实现 定义（任务）的execute方法
 * Quartz框架的任务
 * Created by xiaogao on 2017/11/15.
 */
public class MyQuartzJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    /*存值：dataMap.putAsString("accountId",1000);
    取值:jobDataMap.getIntegerFromString("accountId");
      存值：dataMap.put("accountId",1000);取值：*/
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        //Integer accountId = jobDataMap.getIntegerFromString("accountId");
        Integer accountId = jobDataMap.getInt("accountId");
        System.out.println("Quartz..........."+ accountId);
    }
}
