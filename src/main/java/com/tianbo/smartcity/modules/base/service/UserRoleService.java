package com.tianbo.smartcity.modules.base.service;


import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.modules.base.entity.User;
import com.tianbo.smartcity.modules.base.entity.UserRole;

import java.util.List;

/**
 * 用户角色接口
 */
public interface UserRoleService extends SmartCityBaseService<UserRole, String> {

    /**
     * 通过roleId查找
     *
     * @param roleId
     * @return
     */
    List<UserRole> findByRoleId(String roleId);

    /**
     * 通过roleId查找用户
     *
     * @param roleId
     * @return
     */
    List<User> findUserByRoleId(String roleId);

    /**
     * 删除用户角色
     *
     * @param userId
     */
    void deleteByUserId(String userId);
}
