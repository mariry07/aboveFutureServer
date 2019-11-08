package com.tianbo.smartcity.modules.activiti.dao;

import com.tianbo.smartcity.base.SmartCityBaseDao;
import com.tianbo.smartcity.modules.activiti.entity.ActBusiness;

import java.util.List;

/**
 * 申请业务数据处理层
 *
 * @author Exrick
 */
public interface ActBusinessDao extends SmartCityBaseDao<ActBusiness, String> {

    /**
     * 通过流程定义id获取
     *
     * @param procDefId
     * @return
     */
    List<ActBusiness> findByProcDefId(String procDefId);
}