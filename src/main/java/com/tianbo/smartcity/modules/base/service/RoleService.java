package com.tianbo.smartcity.modules.base.service;


import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.modules.base.entity.Role;

import java.util.List;

/**
 * 角色接口
 */
public interface RoleService extends SmartCityBaseService<Role, String> {

    /**
     * 获取默认角色
     *
     * @param defaultRole
     * @return
     */
    List<Role> findByDefaultRole(Boolean defaultRole);
}
