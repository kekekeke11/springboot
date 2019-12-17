package com.google.mapper;

import com.google.entity.OrderEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface OrderMapper {

    @Insert(value = "INSERT INTO `order_info` (name,orderCreatetime,orderMoney,orderState,commodityId,orderId)" +
            "VALUE (#{name}, #{orderCreatetime}, #{orderMoney}, #{orderState}, #{commodityId},#{orderId})")
    int addOrder(OrderEntity orderEntity);

    @Select("SELECT id,name,orderCreatetime,"
            + "orderState,orderMoney, "
            + "commodityid,orderId from order_info where orderId=#{orderId};")
    OrderEntity findOrderId(@Param("orderId") String orderId);

}
