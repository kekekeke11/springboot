package com.google.util.pubsub;

import com.google.util.config.Config;
import com.google.util.MQConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author wk
 * @Description:扇形交换机 邮件消费者
 * @date 2019/12/10 14:21
 **/
public class ConsumerEmailFanout {

    public static final String EMAIL_QUEUE = "EMAIL_QUEUE";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("邮件消费者启动！");
        //建立连接
        Connection connection = MQConnectionUtils.newConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(EMAIL_QUEUE, false, false, false, null);
        //消费者绑定交换机
        channel.queueBind(EMAIL_QUEUE, Config.FANOUT_EXCHANGE, "");
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("邮件消费者获取生产者的消息：" + new String(body, "UTF-8"));
            }
        };
        //自动应答
        channel.basicConsume(EMAIL_QUEUE, true, consumer);
    }
}
