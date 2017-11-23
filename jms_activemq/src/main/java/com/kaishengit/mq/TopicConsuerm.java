package com.kaishengit.mq;


import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Pub/Sub 监听器
 * Created by xiaogao on 2017/11/23.
 */
@Component
public class TopicConsuerm implements MessageListener{
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println(">>>>>>>>>>"+textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
