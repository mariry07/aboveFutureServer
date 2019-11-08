package com.tianbo.smartcity.modules.base.dao;

import com.tianbo.smartcity.base.SmartCityBaseDao;
import com.tianbo.smartcity.modules.base.entity.UserRole;

import java.util.List;

/**
 * 用户角色数据处理层
 */
public interface UserRoleDao extends SmartCityBaseDao<UserRole, String> {

    /**
     * 通过roleId查找
     *
     * @param roleId
     * @return
     */
    List<UserRole> findByRoleId(String roleId);

    /**
     * 删除用户角色
     *
     * @param userId
     */
    void deleteByUserId(String userId);
}
