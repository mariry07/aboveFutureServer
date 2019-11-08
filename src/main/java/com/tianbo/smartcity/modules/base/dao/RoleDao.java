package com.tianbo.smartcity.modules.base.dao;

import com.tianbo.smartcity.base.SmartCityBaseDao;
import com.tianbo.smartcity.modules.base.entity.Role;

import java.util.List;

/**
 * 角色数据处理层
 */
public interface RoleDao extends SmartCityBaseDao<Role, String> {

    /**
     * 获取默认角色
     *
     * @param defaultRole
     * @return
     */
    List<Role> findByDefaultRole(Boolean defaultRole);
}
