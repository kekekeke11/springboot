package com.google.util.simpleQueue;

import com.google.util.MQConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author wk
 * @Description:消费者
 * @date 2019/12/5 17:39
 **/
public class Customer {

    static String queue = Config.SIMPL_QUEUE_01;

    public static void main(String[] args) throws IOException, TimeoutException {

        //获取连接
        Connection connection = MQConnectionUtils.newConnection();
        //获取通道
        Channel channel = connection.createChannel();
        channel.queueDeclare(queue, false, false, false, null);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("消费者获取信息：" + msg);
            }
        };
        //监听队列
        channel.basicConsume(queue, defaultConsumer);
    }
}
