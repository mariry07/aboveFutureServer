package com.tianbo.smartcity.modules.your.service;

import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.entity.ServerAlarm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AlarmService extends SmartCityBaseService{
    Page<ServerAlarm> findByCondition(ServerAlarm serverAlarm, SearchVo searchVo, Pageable pageable);
}
