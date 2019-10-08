package com.web.jms.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.TimeUnit;

/**
 * @author web
 * @title: AppProducer 消息生产
 * @projectName javacode
 * @description: TODO
 * @date 19-10-3下午5:08
 */

public class AppProducer {

    private static String URL = "tcp://127.0.0.1:61616";
    private static String topicName = "topic-test";

    public static void main(String[] args) throws JMSException, InterruptedException {
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
        //5 创建生产者，绑定目的地
        MessageProducer producer = session.createProducer(destination);
        //6、使用生产者发送消息
        for (int i=0;i<100;i++){
            TextMessage textMessage = session.createTextMessage();
            textMessage.setText("test"+i);
            TimeUnit.SECONDS.sleep(1);
            producer.send(textMessage);
        }
        System.out.println("消息发送成功....");
        //7、关闭Connection
        connection.close();

    }
}
