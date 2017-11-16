package com.kaishengit.crm.jobs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定时发送提醒消息任务
 * Created by xiaogao on 2017/11/16.
 */
public class SendJob implements Job{

    private Logger logger = LoggerFactory.getLogger(SendJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        /*取值（传过来的参数）*/
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String message = (String) dataMap.get("message");
        Integer accountId = (Integer) dataMap.getInt("accountId");

        logger.info("To:{} Message:{}",accountId,message);
    }
}
