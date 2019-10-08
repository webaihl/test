package com.web.jms.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author web
 * @title: AppConsumer
 * @projectName javacode
 * @description: TODO
 * @date 19-10-3下午5:25
 */
public class AppConsumer {

    private static String URL = "tcp://127.0.0.1:61616";
    private static String topicName = "topic-test";

    public static void main(String[] args) throws JMSException {
        //1、创建ConnectionFactory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
        //2、获取Connection
        Connection connection = connectionFactory.createConnection();
        //3、启动连接
        connection.start();
        //根据connection生成session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4、创建目的地
        Destination destination = session.createTopic(topicName);
        //5 创建消费者，绑定目的地
        MessageConsumer consumer = session.createConsumer(destination);
        //6、使用消费者获取消息
        consumer.setMessageListener(message -> {
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println(textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });

        System.out.println("消息接收成功....");
        //7、关闭Connection
        //connection.close();

    }
}
