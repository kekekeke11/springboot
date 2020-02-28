package com.google.msg.sms;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wk
 * @Description:RabbitMQ手动应答模式；普通队列绑定死信队列
 * @date 2019/12/12 17:03
 **/
@Component
public class FanoutSmsConsumer4 {

    //模拟messageId存储 消息是否被消费
    static Map<String, Boolean> messageIdsCache = new HashMap<>();


    @RabbitListener(queues = "FANOUT_SMS_QUEUE4")
    public void process(Message message, @Headers Map<String, Object> headers, Channel channel) throws Exception {
        String messageId = message.getMessageProperties().getMessageId();
        if (!messageIdsCache.containsKey(messageId)) {
            messageIdsCache.put(messageId, false);
        }
        if (messageIdsCache.get(messageId)) {
            System.out.println("FanoutSmsConsumer4 该消息已被消费！");
            return;
        }
        String msg = new String(message.getBody(), "UTF-8");
        try {
            JSONObject jsonObject = JSONObject.parseObject(msg);
            int result = 1 / jsonObject.getInteger("number");
            System.out.println("result:" + result);
            //业务执行成功，消息被消费
            messageIdsCache.put(messageId, true);
            //手动ack
            Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            //手动签收
            channel.basicAck(deliveryTag, false);
            System.out.println("短信服务调用成功！FanoutSmsConsumer4 " + msg+"，messageId: "+messageIdsCache.toString());
        } catch (Exception e) {
            e.printStackTrace();
            //拒绝消费消息（丢弃消息）给死信队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }
}
