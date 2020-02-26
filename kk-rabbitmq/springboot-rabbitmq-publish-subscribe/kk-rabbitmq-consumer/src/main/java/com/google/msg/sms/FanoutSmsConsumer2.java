package com.google.msg.sms;

import com.google.exception.BusinessException;
import com.google.util.http.HttpRequestUtils;
import com.google.util.http.HttpResult;
import org.apache.http.HttpStatus;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wk
 * @Description:
 * @date 2019/12/12 17:03
 **/
@Component
public class FanoutSmsConsumer2 {

    @Value("${smsUrl}")
    private String smsUrl;

    //模拟messageId存储 消息是否被消费存储
    static Map<String, Boolean> messageIdsCache = new HashMap<>();


    @RabbitListener(queues = "FANOUT_SMS_QUEUE2")
    public void process(Message message) throws Exception {
        String messageId = message.getMessageProperties().getMessageId();
        //第一次获取到消息，默认没有被消费
        if (!messageIdsCache.containsKey(messageId)) {
            messageIdsCache.put(messageId, false);
        }
        if (messageIdsCache.get(messageId)) {
            System.out.println("该消息已被消费！");
            //日志输出
            return;
        }
        String msg = new String(message.getBody(), "UTF-8");
        //调用第三方接口无法访问时，自动重试（重试机制策略yml配置），N次重试后消息会消失
        HttpResult httpResult = HttpRequestUtils.doGet(smsUrl, null);
        if (httpResult == null || httpResult.getCode() != HttpStatus.SC_OK) {
            throw new BusinessException("短信服务调用失败");
        }
        //业务执行成功，消息被消费
        messageIdsCache.put(messageId, true);
        System.out.println("短信服务调用成功！FanoutSmsConsumer2 " + msg);
        //默认自动应答
    }
}
