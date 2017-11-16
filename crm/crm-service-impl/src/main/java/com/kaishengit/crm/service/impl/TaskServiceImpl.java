package com.kaishengit.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Task;
import com.kaishengit.crm.example.TaskExample;
import com.kaishengit.crm.exception.ServiceException;
import com.kaishengit.crm.jobs.SendJob;
import com.kaishengit.crm.mapper.TaskMapper;
import com.kaishengit.crm.service.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 待办事项业务曾
 * Created by xiaogao on 2017/11/14.
 */
@Service
public class TaskServiceImpl implements TaskService {

    private Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);


    @Autowired
    private TaskMapper taskMapper;

    /*调度工厂 Spring配置文件中有直接注入*/
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;


    /**
     * 代办列表分页
     *
     * @param account
     * @param pageNo
     * @return
     */
    @Override
    public PageInfo<Task> pageForAccountTask(Account account, Integer pageNo) {

        TaskExample taskExample = new TaskExample();
        taskExample.createCriteria().andAccountIdEqualTo(account.getId());
        PageHelper.startPage(pageNo,10);
        List<Task> taskList = taskMapper.selectByExample(taskExample);

        return new PageInfo<>(taskList);
    }

    /**
     * 保存新增待办事项
     * @param task
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveNewTask(Task task) {
        task.setDone("0");//未完成
        task.setCreateTime(new Date());

        taskMapper.insert(task);//设置返回主键 给下面做标识用
        logger.info("创建新的待办事项 {}",task.getTitle());


        /*添加新的调度任务*/
        //判断是否添加提醒时间 如果没有则不需要做 调度器需要jobDateil和Trigger，所以注入调度工厂
        if(StringUtils.isNotEmpty(task.getRemindTime())) {
        /*差异化 （即发给谁）jobDataMap传的参数*/
            JobDataMap jobDataMap = new JobDataMap();
            /*发给task.getAccountId()*/
            jobDataMap.putAsString("accountId",task.getAccountId());
            /*发的消息*/
            jobDataMap.put("message",task.getTitle());

            JobDetail jobDetail = JobBuilder
                    .newJob(SendJob.class)
                    .setJobData(jobDataMap)
                    //添加标识 供下面删除事物用
                    .withIdentity(new JobKey("taskID"+task.getId(),"sendMessageGroup"))
                    .build();//在接口的实现类中接收

            /*获得RemindTime的值为 2017-12-08 12:20 -->Cron表达式
            String -->DateTime
            * DateTimeFormatter来自org.joda.time.format.DateTimeFormatter;*/
            DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
            DateTime dateTime = timeFormatter.parseDateTime(task.getRemindTime());

            /*拼字符串*/
            StringBuffer cron = new StringBuffer("0")
                    .append(" ")
                    .append(dateTime.getMinuteOfHour())
                    .append(" ")
                    .append(dateTime.getHourOfDay())
                    .append(" ")
                    .append(dateTime.getDayOfMonth())
                    .append(" ")
                    .append(dateTime.getMonthOfYear())
                    .append(" ? ")
                    .append(dateTime.getYear());
            logger.info("CRON EX: {}",cron.toString());

            /*定义Trigger*/
            ScheduleBuilder scheduleBuilder = CronScheduleBuilder
                    .cronSchedule(cron.toString());//Cron表达式
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withSchedule(scheduleBuilder)
                    .build();

            /*调度者*/
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            try {
                scheduler.scheduleJob(jobDetail, trigger);
                scheduler.start();
            } catch(Exception ex) {
                throw new ServiceException(ex,"添加定时任务异常");
            }
        }
    }

    /**
     * 根据id查找对应的待办事项
     *
     * @param id
     * @return
     */
    @Override
    public Task findTaskById(Integer id) {
        return taskMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据Id删除对应的待办事项
     *
     * @param id
     */
    @Override
    public void deleteTaskById(Integer id) {
        //先获得再删除
        Task task = findTaskById(id);
        taskMapper.deleteByPrimaryKey(id);

        //删除定时任务
        if(StringUtils.isNotEmpty(task.getRemindTime())) {
            /*从调度器工厂获得调度器对象*/
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            //group必须和上面一样  要知道删除那个 JobKey相当于自己创建了主键 1:46
            try {
                scheduler.deleteJob(new JobKey("taskID:" + id, "sendMessageGroup"));
                logger.info("成功删除定时任务 ID:{} groupName:{}",id,"sendMessageGroup");
            } catch(Exception ex) {
                throw new ServiceException(ex,"删除定时任务异常");
            }



        }

    }
}
