package com.tianbo.smartcity.modules.your.serviceimpl;

import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.dao.mapper.ServerAlarmMapper;
import com.tianbo.smartcity.modules.your.entity.ServerAlarm;
import com.tianbo.smartcity.modules.your.service.IServerAlarmService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tianbo.smartcity.modules.your.vo.ServerAlarmVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 报警接口实现
 * @author Pangzy
 */
@Slf4j
@Service
@Transactional
public class IServerAlarmServiceImpl extends ServiceImpl<ServerAlarmMapper, ServerAlarm> implements IServerAlarmService {

    @Autowired
    private ServerAlarmMapper serverAlarmMapper;

    @Override
    public List<ServerAlarmVo> getServerAlarmCharts(SearchVo searchVo) {
        return serverAlarmMapper.getServerAlarmCharts(searchVo);
    }

    @Override
    public List<ServerAlarmVo> getDeviceAlarmCharts(ServerAlarm serverAlarm, SearchVo searchVo) {
        return serverAlarmMapper.getDeviceAlarmCharts(serverAlarm,searchVo);
    }

    @Override
    public List<ServerAlarmVo> getAlarmTypeCharts(ServerAlarm serverAlarm, SearchVo searchVo) {
        return serverAlarmMapper.getAlarmTypeCharts(serverAlarm,searchVo);
    }

    @Override
    public List<ServerAlarmVo> getDealTypeCharts(ServerAlarm serverAlarm, SearchVo searchVo) {
        return serverAlarmMapper.getDealTypeCharts(serverAlarm,searchVo);
    }

    @Override
    public List<ServerAlarmVo> getAlamCharts(@Param("serverAlarm") ServerAlarm serverAlarm, @Param("searchVo")SearchVo searchVo) {
        return serverAlarmMapper.getAlamCharts(serverAlarm,searchVo);
    }

    @Override
    public ServerAlarmVo getAlarmSummaryCountAll(SearchVo searchVo) {
        return serverAlarmMapper.getAlarmSummaryCountAll(searchVo);
    }
}