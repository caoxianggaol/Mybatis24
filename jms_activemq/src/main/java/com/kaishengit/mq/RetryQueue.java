package com.kaishengit.mq;

import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * 重试机制演示
 * Created by xiaogao on 2017/11/23.
 */
@Component
public class RetryQueue implements SessionAwareMessageListener {
    @Override
    public void onMessage(Message message, Session session) throws JMSException {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("Retry---->" + textMessage.getText());
            if(1==1) {
                throw new JMSException("特殊异常");
            }
            textMessage.acknowledge();//签收
        } catch (JMSException e) {
            e.printStackTrace();
            session.recover();
        }
    }
}
