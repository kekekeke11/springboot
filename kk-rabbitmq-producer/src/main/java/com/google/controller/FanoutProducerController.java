package com.google.controller;

import com.google.producer.FanoutProducer;
import com.google.util.ParamUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author wk
 * @Description:
 * @date 2019/12/12 16:45
 **/
@RestController
public class FanoutProducerController {

    @Autowired
    private FanoutProducer fanoutProducer;

    @RequestMapping(value = "/sendMessage")
    public String sendMessage(String queueName) {
        if (StringUtils.isBlank(queueName)) {
            return "queueName参数非法";
        }
        if (queueName.contains("SMS")) {
            fanoutProducer.sendSms(queueName);
        } else {
            fanoutProducer.send(queueName);
        }
        return "fanout发送成功！" + ParamUtil.dateConvertString(new Date(), "yyyyMMddHHmmssSSS");
    }
}
