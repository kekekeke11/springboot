package com.google.consumer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.google.entity.OrderEntity;
import com.google.mapper.OrderMapper;
import com.rabbitmq.client.Channel;

/**
 * 补单消费者
 */
@Component
public class CreateOrderConsumer {

    @Autowired
    private OrderMapper orderMapper;

    @RabbitListener(queues = "order_create_queue")
    public void process(Message message, @Headers Map<String, Object> headers, Channel channel) throws Exception {

        String messageId = message.getMessageProperties().getMessageId();
        String msg = new String(message.getBody(), "UTF-8");
        System.out.println("补单消费者" + msg + ",消息id:" + messageId);
        JSONObject jsonObject = JSONObject.parseObject(msg);
        String orderId = jsonObject.getString("orderId");

        // 判断订单是否存在，如果不存在 实现自动补单机制
        OrderEntity orderEntityResult = orderMapper.findOrderId(orderId);
        if (orderEntityResult != null) {
            System.out.println("订单已经存在 无需补单  orderId:" + orderId);
            // 手动签收消息,通知mq服务器端删除该消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }

        System.out.println("订单号码:" + orderId + ", 不存在，开始进行补单！");
        // 订单不存在 ，则需要进行补单
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setName("蚂蚁课堂永久会员充值");
        orderEntity.setOrderCreatetime(new Date());
        // 价格是300元
        orderEntity.setOrderMoney(new BigDecimal("300"));
        // 状态为 未支付
        orderEntity.setOrderState(0);
        Long commodityId = 30l;
        // 商品id
        orderEntity.setCommodityId(commodityId);
        orderEntity.setOrderId(orderId);

        // ##################################################
        // 1.先下单，创建订单 (往订单数据库中插入一条数据)
        try {
            int orderResult = orderMapper.addOrder(orderEntity);
            System.out.println("orderResult:" + orderResult);
            if (orderResult >= 0) {
                // 手动签收消息,通知mq服务器端删除该消息
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
            //重试进行补偿 多次失败 使用日志记录采用定时job检查或者人工补偿
        } catch (Exception e) {
            //日志记录
            // 丢弃该消息
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }

    }
}
