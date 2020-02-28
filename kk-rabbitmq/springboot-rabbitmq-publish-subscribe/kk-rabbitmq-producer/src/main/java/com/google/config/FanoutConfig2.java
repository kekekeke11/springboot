package com.google.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * @author wk
 * @Description:发布订阅模式配置的交换机类型 fanout
 * <p>
 * 1.定义队列
 * 2.定义交换机
 * 3.队列绑定交换机
 * @date 2019/12/12 15:37
 **/
@Component
public class FanoutConfig2 {

    @Bean
    public Queue fanoutEmailQueue2() {
        //构造方法多态
        return new Queue(MQConfig2.QUEUE_NAMES.FANOUT_EMAIL_QUEUE2);
    }

    /**
     * 1.定义短信队列
     *
     * @return
     */
    @Bean
    public Queue fanoutSmsQueue2() {
        return new Queue(MQConfig2.QUEUE_NAMES.FANOUT_SMS_QUEUE2);
    }

    /**
     * 短信队列，将普通队列绑定到死信队列交换机上
     *
     * @return
     */
    @Bean
    public Queue fanoutSmsQueue4() {
        Map<String, Object> args = new HashMap<>(2);
        args.put(MQConfig2.ROUTING_KEYS.DEAD_LETTER_QUEUE_KEY, MQConfig2.EXCHANGE_NAMES.dead_exchange);
        args.put(MQConfig2.ROUTING_KEYS.DEAD_LETTER_ROUTING_KEY, MQConfig2.ROUTING_KEYS.deadRoutingKey);
        Queue queue = new Queue(MQConfig2.QUEUE_NAMES.FANOUT_SMS_QUEUE4, true, false, false, args);
        return queue;
    }


    /**
     * 1.定义短信队列
     *
     * @return
     */
    @Bean
    public Queue fanoutSmsQueue3() {
        return new Queue(MQConfig2.QUEUE_NAMES.FANOUT_SMS_QUEUE3);
    }

    /**
     * 3.邮件队列与交换机绑定
     */
    @Bean
    Binding bindingExchangeEamil2(Queue fanoutEmailQueue2, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutEmailQueue2).to(fanoutExchange);
    }

    /**
     * 4.短信队列与交换机绑定
     */
    @Bean
    Binding bindingExchangeSms2(Queue fanoutSmsQueue2, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutSmsQueue2).to(fanoutExchange);
    }

    /**
     * 4.短信队列与交换机绑定
     */
    @Bean
    Binding bindingExchangeSms3(Queue fanoutSmsQueue3, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutSmsQueue3).to(fanoutExchange);
    }

    /**
     * 4.短信队列与交换机绑定
     */
    @Bean
    Binding bindingExchangeSms4(Queue fanoutSmsQueue4, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutSmsQueue4).to(fanoutExchange);
    }


    /**
     * 配置死信队列
     *
     * @return
     */
    @Bean
    public Queue deadQueue() {
        Queue queue = new Queue(MQConfig2.QUEUE_NAMES.deadQueueName, true);
        return queue;
    }

    /**
     * 路由 交换机
     * 定义死信队列交换机
     *
     * @return
     */
    @Bean
    public DirectExchange deadExchange() {
        return new DirectExchange(MQConfig2.EXCHANGE_NAMES.dead_exchange);
    }

    /**
     * 死信队列绑定交换机
     *
     * @param deadQueue
     * @param deadExchange
     * @return
     */
    @Bean
    public Binding bindingDeadExchange(Queue deadQueue, DirectExchange deadExchange) {
        return BindingBuilder.bind(deadQueue).to(deadExchange).with(MQConfig2.ROUTING_KEYS.deadRoutingKey);
    }


}
