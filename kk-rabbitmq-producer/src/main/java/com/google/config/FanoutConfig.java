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

    //邮件队列
    private String FANOUT_EMAIL_QUEUE = "FANOUT_EMAIL_QUEUE";
    //短信队列
    private String FANOUT_SMS_QUEUE = "FANOUT_SMS_QUEUE";

    //fanout类型交换机
    private String EXCHANGE_NAME = "FANOUT_EXCHANGE";

    /**
     * 1.定义邮件队列
     *
     * @return
     */
    @Bean
    public Queue fanoutEmailQueue() {
        //构造方法多态
        return new Queue(FANOUT_EMAIL_QUEUE);
    }

    /**
     * 1.定义短信队列
     *
     * @return
     */
    @Bean
    public Queue fanoutSmsQueue() {
        return new Queue(FANOUT_SMS_QUEUE);
    }

    /**
     * 2.定义交换机
     *
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        //构造方法多态
        return new FanoutExchange(EXCHANGE_NAME);
    }

    // 3.邮件队列与交换机绑定
    @Bean
    Binding bindingExchangeEamil(Queue fanoutEmailQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutEmailQueue).to(fanoutExchange);
    }

    // 4.短信队列与交换机绑定
    @Bean
    Binding bindingExchangeSms(Queue fanoutSmsQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutSmsQueue).to(fanoutExchange);
    }

}
