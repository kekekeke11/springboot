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

    static String queue = Config.SIMPLE_QUEUE_01;

    public static void main(String[] args) throws IOException, TimeoutException {

        //创建通道
        Channel channel = MQConnectionUtils.getChannel();
        //消费者关联队列
        channel.queueDeclare(queue, false, false, false, null);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            //监听获取消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("消费者获取信息：" + msg);

                //手动应答模式，
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        //设置应答模式 如果为true情况下，表示为自动应答模式 false表示为手动应答 监听队列
        //channel.basicConsume(queue, true, defaultConsumer);
        // [ˈbeɪsɪk] 悲sei克
        //默认false
        channel.basicConsume(queue, defaultConsumer);
    }
}
