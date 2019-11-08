package com.tianbo.smartcity.modules.third.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tianbo.smartcity.modules.base.entity.Permission;
import com.tianbo.smartcity.modules.third.entity.ThirdPlatform;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 第三方平台标识映射
 *
 * @author yxk
 */
@Repository
public interface ThirdPlatformMapper extends BaseMapper<ThirdPlatform> {

    /**
     * 获取所有第三方平台接口
     *
     * @return 第三方平台列表
     */
    List<ThirdPlatform> getAll();

    /**
     * 根据第三方平台标识条件获取对象
     *
     * @param thirdPlatform 第三方标识
     * @return 第三方标识对象
     */
    ThirdPlatform getThird(ThirdPlatform thirdPlatform);

    /**
     * 根据第三方平台标识获取权限
     *
     * @param accessKey 第三方标识
     * @return 权限列表
     */
    List<Permission> findPermissionByAccessKey(String accessKey);

    /**
     * 根据用户id删除第三方平台
     *
     * @param id 用户id
     */
    void deleteByUserId(String id);
}
