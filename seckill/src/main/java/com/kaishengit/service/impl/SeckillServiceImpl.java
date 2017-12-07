package com.kaishengit.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.kaishengit.entity.Seckill;
import com.kaishengit.entity.SeckillExample;
import com.kaishengit.job.ProductInventoryJob;
import com.kaishengit.mapper.SeckillMapper;
import com.kaishengit.service.SeckillService;
import com.kaishengit.service.exception.ServiceException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.io.IOUtils;

import org.joda.time.DateTime;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by xiaogao on 2017/12/5.
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(SeckillService.class);

    @Autowired
    private SeckillMapper seckillMapper;

    @Value("${qiniu.ak}")
    private String qiniuAk;
    @Value("${qiniu.sk}")
    private String qiniuSk;
    @Value("${qiniu.buket}")
    private String qiniuBuket;
    /*jedispool 池*/
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    /**
     * 添加商品（图片）
     *
     * @param seckill
     * @param inputStream
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveSeckill(Seckill seckill, InputStream inputStream) {
        //上传七牛 调下面方法
        String key = uploadToQiniu(inputStream);
        //保存seckill对象
        seckill.setProductImage(key);
        seckillMapper.insertSelective(seckill);
        //在redis中添加商品库存量的集合
        /*方案三  利用pop的原子性  只弹出一个并且只能给一个请求*/

       try(Jedis jedis = jedisPool.getResource()) {
           for(int i = 0;i < seckill.getProductInventory();i ++) {
               jedis.lpush("seckill:" + seckill.getId() + ":inventory", String.valueOf(i));
           }
       }
        //添加秒杀结束的定时任务，用于秒杀结束时更新库存
        addSchedulerJob(seckill.getEndTime().getTime(),seckill.getId());
    }

    /**
     * 添加一个定时任务
     * @param endTime
     * @param productId
     */
    private void addSchedulerJob(Long endTime,Integer productId) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAsString("productId",productId);

        JobDetail jobDetail = JobBuilder
                .newJob(ProductInventoryJob.class)
                .setJobData(jobDataMap)
                .withIdentity(new JobKey("taskID:"+productId,"productInventoryGroup"))
                .build();

        DateTime dateTime = new DateTime(endTime);

        StringBuilder cron = new StringBuilder("0")
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

        logger.info("CRON EX: {}" ,cron.toString());

        ScheduleBuilder scheduleBuilder =
                CronScheduleBuilder.cronSchedule(cron.toString());
        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(scheduleBuilder)
                .build();

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (Exception ex) {
            throw new ServiceException(ex,"添加定时任务异常");
        }
    }
    /**
     * 显示全部抢购商品
     *
     * @return
     */
    @Override
    public List<Seckill> findAll() {
        SeckillExample seckillExample = new SeckillExample();
        seckillExample.setOrderByClause("start_time asc");
        return seckillMapper.selectByExample(seckillExample);
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    @Override
    public Seckill findById(Integer id) {
        Seckill seckill;
        try(Jedis jedis = jedisPool.getResource()) {
            String json = jedis.get("seckill:"+id);
            //判断redis中存在json不，如果不存在去数据库查 放进redis
            if(json == null) {
                seckill = seckillMapper.selectByPrimaryKey(id);
                jedis.set("seckill:"+id,JSON.toJSONString(seckill));
            } else {
               seckill = JSON.parseObject(json,Seckill.class);//反序列化
            }
            /* 方法二  将库存写入redis 键为"seckill:"+id+":num"利用decr自增减*/
            //jedis.set("seckill:"+id+":num",seckill.getProductInventory().toString());

        }

        return seckill;
       // return seckillMapper.selectByPrimaryKey(id);
    }

    /**
     * 秒杀商品
     * 此方法只符合单机（一台服务器）分布式完全不行，不能扩展服务器
     * @param id
     * @throws ServiceException
     */
    @Override
    public void seckill(Integer id) throws ServiceException {

        /*方法三  最佳方案   利用pop（弹出） 的原子性*/
        try(Jedis jedis = jedisPool.getResource()) {
            Seckill seckill = JSON.parseObject(jedis.get("seckill:"+id),Seckill.class);
            if(!seckill.isStart()) {
                throw new RuntimeException("你来早了，还没开始");
            }
            String value = jedis.lpop("product:"+id+":inventory");

            if(value == null) {
                logger.error("库存不足，秒杀失败");
                throw new ServiceException("已抢完");
            } else {
                logger.info("秒杀商品成功");
                //修改redis的缓存
                seckill.setProductInventory(seckill.getProductInventory() - 1);
                jedis.set("product:"+id,JSON.toJSONString(seckill));

                /*logger.info("开始减库存。。。。");
                Seckill seckill = seckillMapper.selectByPrimaryKey(id);
                seckill.setProductInventory(seckill.getProductInventory() - 1);
                seckillMapper.updateByPrimaryKey(seckill);

                //更新redis中的缓存
                jedis.set("seckill:"+id,JSON.toJSONString(seckill));

                logger.info("减库存成功");
*/
                /*jmsTemplate.send("seckill_inventroy_queue", new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        TextMessage textMessage = session.createTextMessage();
                        textMessage.setText(id.toString());
                        return textMessage;
                    }
                });*/
            }
        }



/*-------------------------------------------------------------------------------*/
        /*方法二  虽然解决了超卖，单也只适用于单台服务器  1.查出后放入redis中*/
       /* try(Jedis jedis = jedisPool.getResource()) {
            Long num = jedis.decr("seckill:"+id+":num");
            if(num < 0L) {
                logger.error("库存不足，秒杀失败");
                throw new ServiceException("已抢完");
            } else {
                logger.info("秒杀成功");
            }
        }*/


/*------------------------------------------------------------------------------*/
       /* *//*方案一  方法枷锁 此方法只符合单机（一台服务器）分布式完全不行，不能扩展服务器
         public synchronized void seckill(Integer id) throws ServiceException{}*//*
       //1.查看商品 必须从数据库中查 不能使用上面的findById查 因为有缓存
       Seckill seckill = seckillMapper.selectByPrimaryKey(id);
        //2.判断是否有库存 >0 减1
        if(seckill.getProductInventory() > 0) {
            seckill.setProductInventory(seckill.getProductInventory() - 1);
            seckillMapper.updateByPrimaryKey(seckill);
            logger.info("秒杀成功");
        } else {
            logger.error("库存不足，秒杀失败");
            throw new ServiceException("已抢完");
        }*/
    }
    /*================================================================================*/
    private String uploadToQiniu(InputStream inputStream) throws RuntimeException{
        /*上传七牛云*/
        Configuration configuration = new Configuration(Zone.zone1());
        UploadManager uploadManager = new UploadManager(configuration);

        /*获取口令*/
        Auth auth = Auth.create(qiniuAk,qiniuSk);
        String uploadToken = auth.uploadToken(qiniuBuket);

        try {
            Response response = uploadManager.put(IOUtils.toByteArray(inputStream),null,uploadToken);
            /*获取上传到七牛的key
            * fromJson是Gson提供的一个方法。用来将一个Json数据转换为对象。调用方法是：new Gson().fromJson(Json_string,class)
            * */
            DefaultPutRet defaultPutRet = new Gson().fromJson(response.bodyString(),DefaultPutRet.class);
            return defaultPutRet.key;//把key存入数据库
        } catch (IOException e) {
            throw new RuntimeException("上传文件到七牛失败",e);//回滚
        }
    }
}
