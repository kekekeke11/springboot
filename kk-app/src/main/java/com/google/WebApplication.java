package com.google;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wk
 * @Description:
 * @date 2019/11/25 16:05
 **/
@MapperScan("com.google.mapper")
@SpringBootApplication(scanBasePackages="com.google")
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
