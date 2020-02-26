package com.google.util.topic;

import com.google.util.MQConnectionUtils;
import com.google.util.config.Config;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author wk
 * @Description:RabbitMQ 通配符模式（主题）
 * @date 2019/12/10 11:45
 **/
public class TopicProducer {

    //交换机名称
    private static String EXCHANGE_NAME = Config.TOPIC_EXCHANGE;

    public static void main(String[] args) throws IOException, TimeoutException {
        //建立连接
        Connection connection = MQConnectionUtils.newConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //生产者绑定交换机 （交换机名称，交换机类型）
        channel.exchangeDeclare(EXCHANGE_NAME, Config.exchangeType.topic);

        String routingKey = "msg.info";
        //String routingKey = "msg.info.email";
        System.out.println("通配符模式-生产者启动！");
        String msg = "[" + routingKey + "]RabbitMQ通配符模式的消息❤";
        System.out.println(msg);
        //发送消息
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
