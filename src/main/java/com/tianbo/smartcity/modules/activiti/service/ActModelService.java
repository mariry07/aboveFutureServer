package com.tianbo.smartcity.modules.activiti.service;

import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.modules.activiti.entity.ActModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 模型管理接口
 *
 * @author Exrick
 */
public interface ActModelService extends SmartCityBaseService<ActModel, String> {

    /**
     * 多条件分页获取
     *
     * @param actModel
     * @param pageable
     * @return
     */
    Page<ActModel> findByCondition(ActModel actModel, Pageable pageable);
}