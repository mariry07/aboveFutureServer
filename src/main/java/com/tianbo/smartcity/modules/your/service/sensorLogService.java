package com.tianbo.smartcity.modules.your.service;

import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.entity.Floor;
import com.tianbo.smartcity.modules.your.entity.sensorLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 日志表接口
 * @author Pangzy
 */
public interface sensorLogService extends SmartCityBaseService<sensorLog,String> {

    /**
    * 多条件分页获取
    * @param sensorLog
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<sensorLog> findByCondition(sensorLog sensorLog, SearchVo searchVo, Pageable pageable);
}