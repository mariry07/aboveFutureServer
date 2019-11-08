package com.tianbo.smartcity.modules.your.service;

import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.entity.Sensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 传感器表接口
 * @author Pangzy
 */
public interface SensorService extends SmartCityBaseService<Sensor,String> {

    /**
    * 多条件分页获取
    * @param sensor
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<Sensor> findByCondition(Sensor sensor, SearchVo searchVo, Pageable pageable);
}