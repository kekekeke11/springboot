package com.google.msg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wk
 * @Description: 消费者
 * @date 2019/12/12 16:46
 **/
@SpringBootApplication
public class RabbitMQConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMQConsumerApplication.class, args);
    }
}
