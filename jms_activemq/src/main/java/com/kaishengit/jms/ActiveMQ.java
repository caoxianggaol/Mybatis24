package com.kaishengit.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;
import java.io.IOException;

/**
 * Created by xiaogao on 2017/11/22.
 */
public class ActiveMQ {

    @Test //生产消息
    public void sendMessageToQueue() throws JMSException {
        //1.创建连接connectionFactory   Queue(队列)
        String brokerUrl = "tcp://localhost:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        //2.创建connection
        Connection connection = connectionFactory.createConnection();
        //3.开启连接
        connection.start();
        //4.创建Session (第一个参数是否开启事务，第二个是指定签收消息的模式（自动还是客户端签收）)
        //CLIENT_ACKNOWLEDGE 客户端签收(手动签收)
        Session session = connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);
        //5.创建 Destination 目的地对象
        Destination destination = session.createQueue("text-message");
        //6.创建消息生产者  MessageProducer
        MessageProducer messageProducer = session.createProducer(destination);
        //设置持久化模式 DeliveryMode.PERSISTENT  不持久化（NON_PERSISTENT）时重启ActiveMQ消息会丢失,持久化不会丢失数据
        //看消息的重要性选择，不持久化性能高
        messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        //7.创建消息
        TextMessage textMessage = session.createTextMessage("Hello-message");
        //8.发送消息
        messageProducer.send(textMessage);
        //提交事务  要提交事务先在创建Session时 将参数设置为true false为自动提交
        session.commit(); //session.rollback();也可以提交
        //9.释放资源
        messageProducer.close();
        session.close();
        connection.close();
    }

    /*发送和消费消息 1-5 步相同*/
    @Test   //消费消息
    public void consumerMessageformQueue() throws JMSException, IOException {
        //1.创建连接connectionFactory
        String brokerUrl = "tcp://localhost:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        //2.创建Connection
        Connection connection = connectionFactory.createConnection();
        //3.开启连接
        connection.start();
        //4.创建Session]
        Session session = connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);
        //5.创建 Destination 目的地对象
        Destination destination = session.createQueue("text-message");
        //6.创建消息消费者 MessageConsumer
        MessageConsumer messageConsumer = session.createConsumer(destination);
        //7.消费消息,监听队列中的消息，如果有新的消息，则会调用执行onMessage方法
        messageConsumer.setMessageListener(new MessageListener() {
            @Override   //实质是开启子线程轮询
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("====" + textMessage.getText());
                    //设置为手动签收时 在此手动签收，队列删除
                    textMessage.acknowledge();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();
        //8.释放资源
        messageConsumer.close();
        session.close();
        connection.close();
    }
}
