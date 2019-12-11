package com.google.util.routing;

import com.google.util.MQConnectionUtils;
import com.google.util.config.Config;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author wk
 * @Description:RabbitMQ 路由模式
 * @date 2019/12/10 11:45
 **/
public class RoutingProducer {

    //交换机名称
    private static String EXCHANGE_NAME = Config.DIRECT_EXCHANGE;

    public static void main(String[] args) throws IOException, TimeoutException {
        //建立连接
        Connection connection = MQConnectionUtils.newConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //生产者绑定交换机 （交换机名称，交换机类型）
        channel.exchangeDeclare(EXCHANGE_NAME, Config.exchangeType.direct);

        String routingKey = "sms";
        System.out.println("路由模式-生产者启动！");
        String msg = "[" + routingKey + "]RabbitMQ路由模式的消息❤";
        System.out.println(msg);
        //发送消息
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
