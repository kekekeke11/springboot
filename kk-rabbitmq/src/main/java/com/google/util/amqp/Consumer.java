package com.google.util.amqp;

import com.google.util.MQConnectionUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author wk
 * @Description:amqp事务机制 消费者
 * @date 2019/12/12 14:13
 **/
public class Consumer {

    //队列名称
    private static final String QUEUE_NAME = "AMQP_QUEUE";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("AMQP-消费者启动！");
        //创建通道
        Channel channel = MQConnectionUtils.getChannel();
        //声明消息队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            //监听获取消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("AMQP事务机制-消费者获取信息：" + msg);
                //手动应答模式，
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        //设置应答模式 如果为true情况下，表示为自动应答模式 false表示为手动应答 监听队列
        //channel.basicConsume(queue, true, defaultConsumer);
        // [ˈbeɪsɪk] 悲sei克
        //默认false
        channel.basicConsume(QUEUE_NAME, defaultConsumer);
    }
}
