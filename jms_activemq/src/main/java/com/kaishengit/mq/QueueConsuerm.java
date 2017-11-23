package com.kaishengit.mq;


import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * MQ中的Queue监听器
 * 接收请求  在Spring配置文件中 配置(注册)监听器容器
 * Created by xiaogao on 2017/11/23.
 */
@Component
public class QueueConsuerm implements MessageListener{

    /*消息消费者*/
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;

        try {
            System.out.println("~~~~~~" + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
