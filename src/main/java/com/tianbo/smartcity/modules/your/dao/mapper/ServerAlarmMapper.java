package com.tianbo.smartcity.modules.your.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.entity.ServerAlarm;
import com.tianbo.smartcity.modules.your.vo.ServerAlarmVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 报警数据处理层
 * @author Pangzy
 */
public interface ServerAlarmMapper extends BaseMapper<ServerAlarm> {

    List<ServerAlarmVo> getServerAlarmCharts(SearchVo searchVo);

    List<ServerAlarmVo> getDeviceAlarmCharts(@Param("serverAlarm") ServerAlarm serverAlarm, @Param("searchVo")SearchVo searchVo);

    List<ServerAlarmVo> getAlarmTypeCharts(@Param("serverAlarm") ServerAlarm serverAlarm, @Param("searchVo") SearchVo searchVo);

    List<ServerAlarmVo> getDealTypeCharts(ServerAlarm serverAlarm, SearchVo searchVo);

    List<ServerAlarmVo> getAlamCharts(@Param("serverAlarm") ServerAlarm serverAlarm,@Param("searchVo") SearchVo searchVo);

    ServerAlarmVo getAlarmSummaryCountAll(SearchVo searchVo);
}