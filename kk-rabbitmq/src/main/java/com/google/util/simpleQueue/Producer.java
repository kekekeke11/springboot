package com.google.util.simpleQueue;

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
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection = MQConnectionUtils.newConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //创建队列声明
        String queue = "wk_queue_02";
        channel.queueDeclare(queue, false, false, false, null);
        String message = "第一次使用RabbitMQ";
        System.out.println("生产者发送消息: " + message);
        channel.basicPublish("", queue, null, message.getBytes());
        channel.close();
        connection.close();
    }
}
