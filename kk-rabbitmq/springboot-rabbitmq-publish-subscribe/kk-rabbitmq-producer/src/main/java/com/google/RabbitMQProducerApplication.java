package com.google;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wk
 * @Description: 生产者
 * @date 2019/12/12 16:46
 **/
@SpringBootApplication
public class RabbitMQProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMQProducerApplication.class, args);
    }
}
