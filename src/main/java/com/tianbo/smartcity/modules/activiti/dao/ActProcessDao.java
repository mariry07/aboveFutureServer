package com.tianbo.smartcity.modules.activiti.dao;

import com.tianbo.smartcity.base.SmartCityBaseDao;
import com.tianbo.smartcity.modules.activiti.entity.ActProcess;

import java.util.List;

/**
 * 流程管理数据处理层
 *
 * @author Exrick
 */
public interface ActProcessDao extends SmartCityBaseDao<ActProcess, String> {

    /**
     * 通过key获取
     *
     * @param processKey
     * @return
     */
    List<ActProcess> findByProcessKey(String processKey);

    /**
     * 通过key和latest获取
     *
     * @param processKey
     * @return
     */
    List<ActProcess> findByProcessKeyAndLatest(String processKey, Boolean latest);

    /**
     * 通过key获取按版本排序
     *
     * @param processKey
     * @return
     */
    ActProcess findTopByProcessKeyOrderByVersionDesc(String processKey);

    /**
     * 通过分类获取
     *
     * @param categoryId
     * @return
     */
    List<ActProcess> findByCategoryId(String categoryId);
}