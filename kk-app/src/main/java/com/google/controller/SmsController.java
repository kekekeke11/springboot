package com.google.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wk
 * @Description:
 * @date 2019/12/13 15:58
 **/
@RestController
public class SmsController {

    @RequestMapping(value = "/sendSms")
    public String sendSms() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content", "获取短信服务");
        jsonObject.put("timestamp", System.currentTimeMillis());
        return jsonObject.toJSONString();
    }
}
