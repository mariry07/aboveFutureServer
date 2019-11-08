package com.tianbo.smartcity.modules.base.dao.mapper;

import com.tianbo.smartcity.modules.base.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 通过用户id获取
     *
     * @param userId
     * @return
     */
    List<Permission> findByUserId(@Param("userId") String userId);
}
