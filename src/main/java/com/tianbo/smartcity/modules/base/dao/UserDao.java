package com.tianbo.smartcity.modules.base.dao;

import com.tianbo.smartcity.base.SmartCityBaseDao;
import com.tianbo.smartcity.modules.base.entity.User;

import java.util.List;

/**
 * 用户数据处理层
 */
public interface UserDao extends SmartCityBaseDao<User, String> {

    /**
     * 通过用户名获取用户
     *
     * @param username
     * @return
     */
    List<User> findByUsername(String username);

    /**
     * 通过手机获取用户
     *
     * @param mobile
     * @return
     */
    List<User> findByMobile(String mobile);

    /**
     * 通过邮件获取用户
     *
     * @param email
     * @return
     */
    List<User> findByEmail(String email);

    /**
     * 通过部门id获取
     *
     * @param departmentId
     * @return
     */
    List<User> findByDepartmentId(String departmentId);

    /**
     * 通过用户名模糊搜索
     *
     * @param username
     * @param status
     * @return
     */
    List<User> findByUsernameLikeAndStatus(String username, Integer status);
}
