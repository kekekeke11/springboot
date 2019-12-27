package com.google.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wk
 * @Description:
 * @date 2019/11/25 16:05
 **/
@SpringBootApplication(scanBasePackages="com.google")
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
