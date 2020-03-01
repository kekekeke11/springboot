package com.google;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author wk
 * @Description:注册中心集群 9100服务
 * @date 2019/11/26 12:00
 **/
@EnableEurekaServer
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class HerosEureka_9100 {
    public static void main(String[] args) {
        SpringApplication.run(HerosEureka_9100.class, args);
    }
}
