package com.tianbo.smartcity.modules.activiti.service;

import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.modules.base.entity.Department;
import com.tianbo.smartcity.modules.base.entity.Role;
import com.tianbo.smartcity.modules.base.entity.User;
import com.tianbo.smartcity.modules.activiti.entity.ActNode;

import java.util.List;

/**
 * 流程节点用户接口
 *
 * @author Exrick
 */
public interface ActNodeService extends SmartCityBaseService<ActNode, String> {

    /**
     * 通过nodeId获取用户
     *
     * @param nodeId
     * @return
     */
    List<User> findUserByNodeId(String nodeId);

    /**
     * 通过nodeId获取角色
     *
     * @param nodeId
     * @return
     */
    List<Role> findRoleByNodeId(String nodeId);

    /**
     * 通过nodeId获取部门
     *
     * @param nodeId
     * @return
     */
    List<Department> findDepartmentByNodeId(String nodeId);

    /**
     * 通过nodeId获取部门id
     *
     * @param nodeId
     * @return
     */
    List<String> findDepartmentIdsByNodeId(String nodeId);

    /**
     * 通过nodeId删除
     *
     * @param nodeId
     */
    void deleteByNodeId(String nodeId);

    /**
     * 通过relateId删除
     *
     * @param relateId
     */
    void deleteByRelateId(String relateId);
}