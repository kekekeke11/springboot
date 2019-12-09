package com.google.util.workQueue;

import com.google.util.MQConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author wk
 * @Description:生产者
 * @date 2019/12/5 15:28
 **/
public class WorkProducer {

    static String QUEUE_NAME = Config.WORK_QUEUE_01;

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("工作队列，生产者启动!");
        //获取连接
        Connection connection = MQConnectionUtils.newConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 保证一次只分发一次 限制发送给同一个消费者 不得超过一条消息
        channel.basicQos(1);
        for (int i = 1; i <= 20; i++) {
            String message = "RabbitMQ消息" + i;
            System.out.println("生产者发送消息: " + message);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        }

        channel.close();
        connection.close();
    }
}
