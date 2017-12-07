package com.kaishengit.mq;

import com.kaishengit.entity.Seckill;
import com.kaishengit.mapper.SeckillMapper;
import org.apache.activemq.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * Created by xiaogao on 2017/12/6.
 */
@Component
public class SeckillConsumer {

    private Logger logger = LoggerFactory.getLogger(SeckillConsumer.class);

    @Autowired
    private SeckillMapper seckillMapper;

    @JmsListener(destination = "seckill_inventroy_queue",containerFactory = "jmsListenerContainerFactory")
    public void job(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            String text = textMessage.getText();
            Integer id = Integer.valueOf(text);

            logger.info("开始减库存");
            Seckill seckill = seckillMapper.selectByPrimaryKey(id);
            seckill.setProductInventory(seckill.getProductInventory() - 1);
            seckillMapper.updateByPrimaryKey(seckill);
            logger.info("减库存成功");

            message.acknowledge();//签收
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
