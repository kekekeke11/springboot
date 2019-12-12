package com.google.msg.email;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author wk
 * @Description:
 * @date 2019/12/12 17:02
 **/
@Component
@RabbitListener(queues = "FANOUT_EMAIL_QUEUE")
public class FanoutEmailConsumer {

    @RabbitHandler
    public void process(String msg) {
        System.out.println("FanoutEmailConsumer获取消息：" + msg);
    }
}
