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

        //死信交换机
        public final static String dead_exchange = "dead_exchange";

    }

    public static final class QUEUE_NAMES {
        //邮件队列
        public static final String FANOUT_EMAIL_QUEUE = "FANOUT_EMAIL_QUEUE";
        public static final String FANOUT_EMAIL_QUEUE2 = "FANOUT_EMAIL_QUEUE2";

        //短信队列
        public static final String FANOUT_SMS_QUEUE = "FANOUT_SMS_QUEUE";
        //短信队列
        public static final String FANOUT_SMS_QUEUE2 = "FANOUT_SMS_QUEUE2";
        public static final String FANOUT_SMS_QUEUE3 = "FANOUT_SMS_QUEUE3";
        public static final String FANOUT_SMS_QUEUE4 = "FANOUT_SMS_QUEUE4";

        //死信队列
        public static final String deadQueueName = "dead_queue";

    }

    public static final class ROUTING_KEYS {
        /**
         * 定义死信队列相关信息
         */
        public static final String deadRoutingKey = "dead_routing_key";
        /**
         * 死信队列 交换机标识符
         */
        public static final String DEAD_LETTER_QUEUE_KEY = "x-dead-letter-exchange";
        /**
         * 死信队列交换机绑定键标识符
         */
        public static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";
    }

}
