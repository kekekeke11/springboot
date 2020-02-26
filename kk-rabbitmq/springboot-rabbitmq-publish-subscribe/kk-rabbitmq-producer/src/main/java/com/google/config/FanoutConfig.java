package com.google.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


/**
 * @author wk
 * @Description:发布订阅模式配置的交换机类型 fanout
 * @date 2019/12/12 15:37
 **/
@Component
public class FanoutConfig {

    /*
        1.定义队列
        2.定义交换机
        3.队列绑定交换机
     */

    /**
     * 1.定义邮件队列
     *
     * @return
     */
    @Bean
    public Queue fanoutEmailQueue() {
        //构造方法多态
        return new Queue(MQConfig.QUEUE_NAMES.FANOUT_EMAIL_QUEUE);
    }


    /**
     * 1.定义短信队列
     *
     * @return
     */
    @Bean
    public Queue fanoutSmsQueue() {
        return new Queue(MQConfig.QUEUE_NAMES.FANOUT_SMS_QUEUE);
    }

    /**
     * 2.定义交换机
     * 主题交换机TopicExchange
     *
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        //构造方法多态
        return new FanoutExchange(MQConfig.EXCHANGE_NAMES.FANOUT_EXCHANGE);
    }

    /**
     * 3.邮件队列与交换机绑定
     * Queue fanoutEmailQueue, FanoutExchange fanoutExchange 此处参数名称要与方法名称一致，
     * 通过spring bean id 去找对象
     */
    @Bean
    Binding bindingExchangeEamil(Queue fanoutEmailQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutEmailQueue).to(fanoutExchange);
    }

    /**
     * 4.短信队列与交换机绑定
     */
    @Bean
    Binding bindingExchangeSms(Queue fanoutSmsQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutSmsQueue).to(fanoutExchange);
    }
}
