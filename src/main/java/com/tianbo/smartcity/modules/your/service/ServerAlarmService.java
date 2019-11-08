package com.tianbo.smartcity.modules.your.service;

import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.entity.Floor;
import com.tianbo.smartcity.modules.your.entity.ServerAlarm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 报警接口
 * @author Pangzy
 */
public interface ServerAlarmService extends SmartCityBaseService<ServerAlarm,String> {

    /**
    * 多条件分页获取
    * @param serverAlarm
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<ServerAlarm> findByCondition(ServerAlarm serverAlarm, SearchVo searchVo, Pageable pageable);
}