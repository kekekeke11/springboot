package com.google.util.pubsub;

import com.google.util.MQConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author wk
 * @Description:扇形交换机 消费者
 * @date 2019/12/10 14:11
 **/
public class ConsumerSmsFanout {

    public static final String SMS_QUEUE = "SMS_QUEUE";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("短信消费者启动！");
        //建立连接
        Connection connection = MQConnectionUtils.newConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(SMS_QUEUE, false, false, false, null);
        //消费者队列绑定交换机
        channel.queueBind(SMS_QUEUE, Config.FANOUT_EXCHANGE, "");
        //消费监听消息
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("短信消费者获取生产者消息：" + msg);
            }
        };
        //自动应答
        channel.basicConsume(SMS_QUEUE, true, consumer);
    }
}
