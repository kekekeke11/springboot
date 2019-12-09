package com.google.util.workQueue;

import com.google.util.MQConnectionUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author wk
 * @Description:
 * @date 2019/12/6 18:07
 **/
public class Customer2 {

    static String queue = Config.WORK_QUEUE_01;

    public static void main(String[] args) throws IOException, TimeoutException {
        customerMember_02();
    }

    public static void customerMember_02() throws IOException, TimeoutException {
        //创建通道
        Channel channel = MQConnectionUtils.getChannel();
        //消费者关联队列
        channel.queueDeclare(queue, false, false, false, null);
        // 保证一次只分发一次 限制发送给同一个消费者 不得超过一条消息
        channel.basicQos(1);
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("消费者——2——获取信息：" + msg);

                //手动应答模式，
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(queue, false, defaultConsumer);
    }
}
