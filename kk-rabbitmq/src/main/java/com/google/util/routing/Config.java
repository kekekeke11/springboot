package com.google.util.routing;

/**
 * @author wk
 * @Description:
 * @date 2019/12/10 11:47
 **/
public class Config {

    //扇形交换机
    public static final String FANOUT_EXCHANGE = "FANOUT_EXCHANGE";

    //路由
    public static final String DIRECT_EXCHANGE = "DIRECT_EXCHANGE";

    public static class exchangeType {
        public static final String fanout = "fanout";
        public static final String direct = "direct";
    }

}
