package com.google.producer;

import com.alibaba.fastjson.JSONObject;
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
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "1357760345@qq.com");
        jsonObject.put("content", "SpringBoot整合RabbitMQ");
        jsonObject.put("timestamp", System.currentTimeMillis());
        amqpTemplate.convertAndSend(queueName, jsonObject.toJSONString());
    }
}
