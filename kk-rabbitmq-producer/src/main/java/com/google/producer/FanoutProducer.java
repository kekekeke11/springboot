package com.google.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author wk
 * @Description:SrpingBoot整合RabbitMQ发布订阅模式-生产者
 * @date 2019/12/12 16:32
 **/
@Service
public class FanoutProducer {


    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 生产者发送消息
     *
     * @param queueName
     */
    public void send(String queueName) {
        String msg = "SpringBoot整合RabbitMQ:" + new Date();
        System.out.println("Fanout生产者发送消息：" + msg);
        amqpTemplate.convertAndSend(queueName, msg);
    }
}
