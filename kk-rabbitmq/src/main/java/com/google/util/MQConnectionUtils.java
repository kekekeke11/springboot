package com.google.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author wk
 * @Description:
 * @date 2019/12/5 14:30
 **/
public class MQConnectionUtils {

    public static Connection newConnection() throws IOException, TimeoutException {

        //1.定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //2.设置服务器地址
        factory.setHost("127.0.0.1");
        // 3.设置协议端口号
        factory.setPort(5672);
        // 4.设置vhost
        factory.setVirtualHost("/wk_virtual_hosts");
        // 5.设置用户名称
        factory.setUsername("root");
        // 6.设置用户密码
        factory.setPassword("1234");
        // 7.创建新的连接
        Connection newConnection = factory.newConnection();
        return newConnection;
    }

    /**
     * 创建渠道
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    public static Channel getChannel() throws IOException, TimeoutException {
        //获取连接
        Connection connection = MQConnectionUtils.newConnection();
        //创建通道
        return connection.createChannel();
    }
}
