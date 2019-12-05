package com.google;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author wk
 * @Description:注册中心
 * @date 2019/11/26 12:00
 **/
@EnableEurekaServer
@SpringBootApplication
public class HerosEureka {
    public static void main(String[] args) {
        SpringApplication.run(HerosEureka.class, args);
    }
}
