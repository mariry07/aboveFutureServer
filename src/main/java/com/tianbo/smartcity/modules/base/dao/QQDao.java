package com.tianbo.smartcity.modules.base.dao;

import com.tianbo.smartcity.base.SmartCityBaseDao;
import com.tianbo.smartcity.modules.base.entity.social.QQ;

/**
 * qq登录数据处理层
 *
 * @author Exrick
 */
public interface QQDao extends SmartCityBaseDao<QQ, String> {

    /**
     * 通过openId获取
     *
     * @param openId
     * @return
     */
    QQ findByOpenId(String openId);

    /**
     * 通过username获取
     *
     * @param username
     * @return
     */
    QQ findByRelateUsername(String username);

    /**
     * 通过username删除
     *
     * @param username
     */
    void deleteByUsername(String username);
}