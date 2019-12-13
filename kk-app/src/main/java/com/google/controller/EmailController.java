package com.google.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author wk
 * @Description:发送邮件
 * @date 2019/12/13 11:38
 **/
@RestController
public class EmailController {

    @RequestMapping(value = "/sendEmail")
    public String sendEmail() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content", "获取邮件服务");
        jsonObject.put("timestamp", System.currentTimeMillis());
        return jsonObject.toJSONString();
    }

}
