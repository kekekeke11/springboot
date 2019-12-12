package com.google.msg.sms;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author wk
 * @Description:
 * @date 2019/12/12 17:03
 **/
@Component
@RabbitListener(queues = "FANOUT_SMS_QUEUE")
public class FanoutSmsConsumer {

    @RabbitHandler
    public void process(String msg) {
        System.out.println("FanoutSmsConsumer获取消息：" + msg);
    }
}
