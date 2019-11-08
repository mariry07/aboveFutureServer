package com.tianbo.smartcity.modules.base.dao;

import com.tianbo.smartcity.base.SmartCityBaseDao;
import com.tianbo.smartcity.modules.base.entity.social.Github;

/**
 * Github登录数据处理层
 *
 * @author Exrick
 */
public interface GithubDao extends SmartCityBaseDao<Github, String> {

    /**
     * 通过openId获取
     *
     * @param openId
     * @return
     */
    Github findByOpenId(String openId);

    /**
     * 通过username获取
     *
     * @param username
     * @return
     */
    Github findByRelateUsername(String username);

    /**
     * 通过username删除
     *
     * @param username
     */
    void deleteByUsername(String username);
}