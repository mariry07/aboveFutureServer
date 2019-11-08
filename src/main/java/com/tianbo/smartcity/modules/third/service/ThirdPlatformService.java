package com.tianbo.smartcity.modules.third.service;


import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.base.entity.Permission;
import com.tianbo.smartcity.modules.third.entity.ThirdPlatform;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 第三方平台标识接口
 *
 * @author yxk
 */
public interface ThirdPlatformService extends SmartCityBaseService<ThirdPlatform, String> {

    /**
     * 获取所有第三方平台接口
     *
     * @return 第三方平台列表
     */
    @Override
    List<ThirdPlatform> getAll();

    /**
     * 多条件分页获取第三方平台列表
     *
     * @param thirdPlatform 第三方平台对象
     * @param searchVo      查询条件
     * @param pageable      分页对象
     * @return 分页列表
     */
    Page<ThirdPlatform> findByCondition(ThirdPlatform thirdPlatform, SearchVo searchVo, Pageable pageable);

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
     * @param id 用户id
     */
    void deleteByUserId(String id);
}
