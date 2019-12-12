package com.google.util.amqp;

import com.google.util.MQConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author wk
 * @Description:amqp事务机制 生产者
 * @date 2019/12/12 14:12
 **/
public class Producer {

    //队列名称
    private static final String QUEUE_NAME = "AMQP_QUEUE";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("AMQP事务机制-生产者启动！");
        //创建连接
        Connection connection = MQConnectionUtils.newConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.txSelect();//开启事务

        try {
            //发布消息
            String msg = "AMQP事务机制发布的消息哦！开启事务";
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            //不加事务的情况，消息会发布成功，消费者可以接收到
            @SuppressWarnings("unused") int i = 1 / 0;
            channel.txCommit();//提交事务
        } catch (Exception e) {
            e.printStackTrace();
            channel.txRollback();//回滚事务
        } finally {
            channel.close();
            connection.close();
        }
    }
}
