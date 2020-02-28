package com.google.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.google.base.BaseApiService;
import com.google.base.ResponseBase;
import com.google.entity.OrderEntity;
import com.google.mapper.OrderMapper;
import com.google.rabbitmq.RabbitmqConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService extends BaseApiService implements RabbitTemplate.ConfirmCallback {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional
    public ResponseBase addOrderAndDispatch(String data) {

        //获取前台传来的数据
        if (StringUtils.isBlank(data)) {
            return setResultError("参数不能为空!");
        }
        JSONObject jsonObject = JSON.parseObject(data);
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setName(jsonObject.getString("name"));
        orderEntity.setOrderCreatetime(new Date());
        // 价格是300元
        orderEntity.setOrderMoney(jsonObject.getBigDecimal("money"));
        // 状态为 未支付
        orderEntity.setOrderState(0);
        // 商品id
        orderEntity.setCommodityId(jsonObject.getLong("CommodityId"));
        String orderId = UUID.randomUUID().toString();
        orderEntity.setOrderId(orderId);

        // 1.先下单，创建订单 (往订单数据库中插入一条数据)
        int orderResult = orderMapper.addOrder(orderEntity);
        System.out.println("创建订单orderResult:" + orderResult);
        if (orderResult <= 0) {
            return setResultError("下单失败!");
        }
        // 2.使用消息中间件将参数存在派单队列中
        send(orderId);

        //模拟场景3 前面创建的订单会回滚，补单开始起作用
        //int i = 1 / 0;

        return setResultSuccess();
    }

    private void send(String orderId) {
        JSONObject jsonObect = new JSONObject();
        jsonObect.put("orderId", orderId);
        String msg = jsonObect.toJSONString();
        System.out.println("生产者发送的msg:" + msg);
        // 封装消息
        Message message = MessageBuilder.withBody(msg.getBytes()).setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setContentEncoding("utf-8").setMessageId(orderId).build();
        // 构建回调返回的数据(消息Id)
        CorrelationData correlationData = new CorrelationData(orderId);

        // 发送消息 true：RabbitMQ会调用Basic.Return命令将消息返回给生产者
        //false：RabbitMQ会把消息直接丢弃
        this.rabbitTemplate.setMandatory(true);
        //Confirm机制，会调用该send方法
        this.rabbitTemplate.setConfirmCallback(this);

        //(交换机,路由Key,Message,CorrelationData)
        rabbitTemplate.convertAndSend(RabbitmqConfig.ORDER_EXCHANGE_NAME, RabbitmqConfig.ORDER_ROUTING_KEY, message, correlationData);

    }

    /**
     * 生产消息确认机制 （生产者往服务器端发送消息的时候，采用应答机制）
     *
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String orderId = correlationData.getId();
        System.out.println("消息id:" + correlationData.getId());
        if (ack) {
            System.out.println("消息发送确认成功");
        } else {
            //重试机制 （最大重试次数配置）
            System.out.println("生产者消息发送确认失败，开始重试:" + cause);
            send(orderId);
        }

    }

}
