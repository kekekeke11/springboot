package com.google.config;

/**
 * @author wk
 * @Description: 交换机 队列 routingKey名称配置
 * @date 2019/12/16 15:42
 **/
public class MQConfig {

    public static final class EXCHANGE_NAMES {
        //fanout类型交换机
        public static final String FANOUT_EXCHANGE = "FANOUT_EXCHANGE";

    }

    public static final class QUEUE_NAMES {
        //邮件队列
        public static final String FANOUT_EMAIL_QUEUE = "FANOUT_EMAIL_QUEUE";

        //短信队列
        public static final String FANOUT_SMS_QUEUE = "FANOUT_SMS_QUEUE";
    }
}
