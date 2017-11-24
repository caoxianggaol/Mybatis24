package com.kaishengit.crm.jobs;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

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

        /*生产发送消息*/
        //getScheduler 调度器  给一个Spring容器
        try {
            ApplicationContext applicationContext = (ApplicationContext) jobExecutionContext.getScheduler().getContext().get("springActiveMQ");
            /*getBean从Spring容器中取值*/
            JmsTemplate jmsTemplate = (JmsTemplate) applicationContext.getBean("jmsTemplate");
            jmsTemplate.send("weixinmessage-Queue", new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    String json = "{\"id\":CaoXiangGao,\"message\":\"message\"}";
                    TextMessage textMessage = session.createTextMessage(json);
                    return textMessage;
                }
            });
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
