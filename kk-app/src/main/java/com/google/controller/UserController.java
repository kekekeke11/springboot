package com.google.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wk
 * @Description:
 * @date 2019/11/25 16:08
 **/
@RestController
public class UserController {

    @RequestMapping(value = "")
    public String hello() {
        return "hello";
    }
}
