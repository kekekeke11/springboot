package com.google.msg.sms;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wk
 * @Description:死信队列消费者
 * @date 2019/12/12 17:03
 **/
@Component
public class FanoutSmsDeadConsumer {

    //模拟messageId存储 消息是否被消费存储
    static Map<String, Boolean> messageIdsCache = new HashMap<>();


    @RabbitListener(queues = "dead_queue")
    public void process(Message message, @Headers Map<String, Object> headers, Channel channel) throws Exception {
        String messageId = message.getMessageProperties().getMessageId();
        if (!messageIdsCache.containsKey(messageId)) {
            messageIdsCache.put(messageId, false);
        }
        if (messageIdsCache.get(messageId)) {
            System.out.println("死信队列FanoutSmsDeadConsumer 该消息已被消费！");
            return;
        }
        String msg = new String(message.getBody(), "UTF-8");
        //消息是否消费
        messageIdsCache.put(messageId, true);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        System.out.println("短信服务调用成功！死信队列FanoutSmsDeadConsumer " + msg + "，messageId: " + messageIdsCache.toString());
    }
}
