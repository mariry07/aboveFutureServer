package com.tianbo.smartcity.modules.base.service;

import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.modules.base.entity.QuartzJob;

import java.util.List;

/**
 * 定时任务接口
 *
 * @author Exrick
 */
public interface QuartzJobService extends SmartCityBaseService<QuartzJob, String> {

    /**
     * 通过类名获取
     *
     * @param jobClassName
     * @return
     */
    List<QuartzJob> findByJobClassName(String jobClassName);
}