package com.kaishengit.mq;

import org.apache.activemq.command.ActiveMQTopic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.IOException;

/**
 * 发送请求
 * Created by xiaogao on 2017/11/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-jms.xml")
public class SpringJmsSendTest {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test  //发送消息到队列
    public void sendMessageToQueue() throws IOException {

        jmsTemplate.send("spring++queue",new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage("spring-->JMS");
                return textMessage;
            }
        });
      System.in.read();
    }



    @Test  //消息到主题（Topic）
    public void sendMessageToTopic() {
        ActiveMQTopic topic = new ActiveMQTopic("spring--topic");
        jmsTemplate.send(topic, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage("spring-->topic");
                return textMessage;
            }
        });
    }


}
