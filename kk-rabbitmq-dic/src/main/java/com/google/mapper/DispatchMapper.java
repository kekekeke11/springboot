package com.google.mapper;

import com.google.entity.DispatchEntity;
import org.apache.ibatis.annotations.Insert;


public interface DispatchMapper {

    /**
     * 新增派单任务
     */
    @Insert("INSERT into platoon values (null,#{orderId},#{dispatchRoute},#{takeoutUserId});")
    public int insertDistribute(DispatchEntity distributeEntity);

}
