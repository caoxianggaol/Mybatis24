package com.kaishengit.job;

import com.kaishengit.entity.Seckill;
import com.kaishengit.mapper.SeckillMapper;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class ProductInventoryJob implements Job {

    private Logger logger = LoggerFactory.getLogger(ProductInventoryJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String productId = jobDataMap.getString("productId");

        try {
            ApplicationContext ctx = (ApplicationContext) jobExecutionContext.getScheduler().getContext().get("springApplicationContext");
            //Mapper
            SeckillMapper seckillMapper = (SeckillMapper) ctx.getBean("productMapper");
            //JedisPool
            JedisPool jedisPool = (JedisPool) ctx.getBean("jedisPool");

            Jedis jedis = jedisPool.getResource();
            Long size = jedis.llen("product:"+productId+":inventory");

            Seckill seckill = seckillMapper.selectByPrimaryKey(Integer.valueOf(productId));
            seckill.setProductInventory(size.intValue());
            seckillMapper.updateByPrimaryKey(seckill);


            logger.info("商品{}修改库存成功{}",productId,size);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
