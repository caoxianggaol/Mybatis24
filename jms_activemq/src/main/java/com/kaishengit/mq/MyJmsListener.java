package com.kaishengit.mq;


import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * 基于注解的监听器
 * Created by xiaogao on 2017/11/23.
 */
@Component
public class MyJmsListener {

    @JmsListener(destination = "spring++queue", containerFactory = "jmsListenerContainerFactory")
    public void getMessageFromQueue(String message) {
        System.out.println("___注解--" + message);
    }


    @JmsListener(destination = "spring--topic", containerFactory = "jmsTopicListenerContainerFactory")
    public void getMessageFromTopic(String message) {
        System.out.println("注解--" + message);
    }

//=======================================================================================

    @JmsListener(destination = "spring++queue", containerFactory = "jmsListenerContainerFactory")
    public void getMessageFromQueue(Message message, Session session) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("*********"+ textMessage.getText());
            if(1==1) {
               throw new JMSException("异常");
           }
            message.acknowledge();//签收
        } catch (JMSException e) {
            e.printStackTrace();
            try {
                session.recover();
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
        }
    }
}