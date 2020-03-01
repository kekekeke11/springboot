package com.google.api.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author wk
 * @Description:服务提供者
 * @date 2019/11/26 12:00
 **/
@EnableEurekaClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class StoryEureka {
    public static void main(String[] args) {
        SpringApplication.run(StoryEureka.class, args);
    }
}
