package com.google.api.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author wk
 * @Description:消费者
 * @date 2019/11/26 16:46
 **/
@EnableEurekaClient
@SpringBootApplication
public class RegionEureka {

    public static void main(String[] args) {
        SpringApplication.run(RegionEureka.class, args);
    }

    /**
     * @LoadBalanced就能让这个RestTemplate在请求时拥有客户端负载均衡的能力
     */

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
