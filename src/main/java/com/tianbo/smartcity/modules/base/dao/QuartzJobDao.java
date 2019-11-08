package com.tianbo.smartcity.modules.base.dao;

import com.tianbo.smartcity.base.SmartCityBaseDao;
import com.tianbo.smartcity.modules.base.entity.QuartzJob;

import java.util.List;

/**
 * 定时任务数据处理层
 *
 * @author Exrick
 */
public interface QuartzJobDao extends SmartCityBaseDao<QuartzJob, String> {

    /**
     * 通过类名获取
     *
     * @param jobClassName
     * @return
     */
    List<QuartzJob> findByJobClassName(String jobClassName);
}