package com.tianbo.smartcity.modules.your.service;

import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.entity.SensorType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 传感器故障接口
 * @author Pangzy
 */
public interface SensorTypeService extends SmartCityBaseService<SensorType,String> {

    /**
    * 多条件分页获取
    * @param sensorType
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<SensorType> findByCondition(SensorType sensorType, SearchVo searchVo, Pageable pageable);
}