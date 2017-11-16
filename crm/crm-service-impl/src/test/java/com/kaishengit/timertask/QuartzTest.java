package com.kaishengit.timertask;


import com.kaishengit.crm.jobstest.MyQuartzJob;
import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;

/**
 * Created by xiaogao on 2017/11/15.
 * Quartz的  使用（固定格式）
 */
public class QuartzTest {

    @Test
    public void QuartzTest() throws SchedulerException, IOException {
        //定义job ,参数为job接口的实现类 MyQuartzJob.class
        JobDetail jobDetail = JobBuilder.newJob(MyQuartzJob.class).build();
        //定义触发器Trigger
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
        //指定执行方式
        simpleScheduleBuilder.withIntervalInSeconds(5);//每隔5秒执行一次
        simpleScheduleBuilder.repeatForever();//永远重复

        //创建Trigger 根据simpleScheduleBuilder的执行方式创建相对应的Trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(simpleScheduleBuilder).build();
        //创建调度者对象Scheduler,StdSchedulerFactory(标准的调度者工厂)
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        //调度者调度Job
        scheduler.scheduleJob(jobDetail,trigger);
        scheduler.start();

        //System.in.read();
    }

    /**
     * 使用 cronTrigger 还可以传值
     * @throws SchedulerException
     * @throws IOException
     */
    @Test
    public  void cronTrigger() throws SchedulerException, IOException {
        //设置jobDetail中的参数
        JobDataMap dataMap = new JobDataMap();
       // dataMap.putAsString("accountId",1000);
        dataMap.put("accountId",1000);
        //定义job ,参数为job接口的实现类 MyQuartzJob.class
        JobDetail jobDetail = JobBuilder
                .newJob(MyQuartzJob.class)
                .setJobData(dataMap)//传值
                /*。。。。。。可以传多个值*/
                .build();
        //定义触发器Trigger   定时Cron表达式在线生成
        ScheduleBuilder simpleScheduleBuilder = CronScheduleBuilder
                .cronSchedule("0/5 * * * * ? *");//Cron表达式
        //创建Trigger 根据simpleScheduleBuilder的执行方式创建相对应的Trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(simpleScheduleBuilder).build();
        //创建调度者对象Scheduler,StdSchedulerFactory(标准的调度者工厂)
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        //调度者调度Job
        scheduler.scheduleJob(jobDetail,trigger);
        scheduler.start();

       // System.in.read();
    }
}
