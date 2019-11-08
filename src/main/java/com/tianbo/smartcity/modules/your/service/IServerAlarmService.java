package com.tianbo.smartcity.modules.your.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.entity.ServerAlarm;
import com.tianbo.smartcity.modules.your.vo.ServerAlarmVo;

import java.util.List;

/**
 * 报警接口
 * @author Pangzy
 */
public interface IServerAlarmService extends IService<ServerAlarm> {

    List<ServerAlarmVo> getServerAlarmCharts(SearchVo searchVo);

    List<ServerAlarmVo> getDeviceAlarmCharts(ServerAlarm serverAlarm,SearchVo searchVo);

    List<ServerAlarmVo> getAlarmTypeCharts(ServerAlarm serverAlarm, SearchVo searchVo);

    List<ServerAlarmVo> getDealTypeCharts(ServerAlarm serverAlarm, SearchVo searchVo);

    List<ServerAlarmVo> getAlamCharts(ServerAlarm serverAlarm, SearchVo searchVo);

    ServerAlarmVo getAlarmSummaryCountAll(SearchVo searchVo);
}