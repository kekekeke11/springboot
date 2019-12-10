package com.google.util.pubsub;

import com.google.util.MQConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author wk
 * @Description:RabbitMQ发布订阅生产者
 * @date 2019/12/10 11:45
 **/
public class FanoutProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        //建立连接
        Connection connection = MQConnectionUtils.newConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //生产者绑定交换机 （交换机名称，交换机类型）
        channel.exchangeDeclare(Config.FANOUT_EXCHANGE, Config.exchangeType.fanout);

        System.out.println("发布订阅-生产者启动！");
        String msg = "RabbitMQ发布订阅的消息22222";
        System.out.println(msg);
        //发送消息
        channel.basicPublish(Config.FANOUT_EXCHANGE, "", null, msg.getBytes());
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
