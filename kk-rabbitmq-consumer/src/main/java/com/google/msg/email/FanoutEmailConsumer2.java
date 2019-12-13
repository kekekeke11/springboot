package com.google.msg.email;

import com.google.exception.BusinessException;
import com.google.util.http.HttpRequestUtils;
import com.google.util.http.HttpResult;
import org.apache.http.HttpStatus;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author wk
 * @Description:重试机制
 * @date 2019/12/13 11:35
 **/
@Component
public class FanoutEmailConsumer2 {

    @Value("${emailUrl}")
    private String emailUrl;

    @RabbitListener(queues = "FANOUT_EMAIL_QUEUE2")
    public void process(String msg) {
        System.out.println("FanoutEmailConsumer22222获取消息：" + msg);
        //调用第三方接口无法访问时，自动重试（重试机制策略yml配置），N次重试后消息会消失
        HttpResult httpResult = HttpRequestUtils.doGet(emailUrl, null);
        if (httpResult == null || httpResult.getCode() != HttpStatus.SC_OK) {
            throw new BusinessException("邮件服务调用失败");
        }
        System.out.println("邮件服务调用成功！FanoutEmailConsumer2");
    }
}
