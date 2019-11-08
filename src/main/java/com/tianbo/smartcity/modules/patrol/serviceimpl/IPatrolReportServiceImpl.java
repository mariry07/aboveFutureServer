package com.tianbo.smartcity.modules.patrol.serviceimpl;

import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.patrol.dao.mapper.PatrolReportMapper;
import com.tianbo.smartcity.modules.patrol.entity.PatrolDevice;
import com.tianbo.smartcity.modules.patrol.service.IPatrolReportService;
import com.tianbo.smartcity.modules.patrol.vo.PatrolReportVo;
import com.tianbo.smartcity.modules.your.entity.ServerAlarm;
import com.tianbo.smartcity.modules.your.vo.ServerAlarmVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 巡查报表接口实现
 * @author fanshaohai
 */
@Slf4j
@Service
@Transactional
public class IPatrolReportServiceImpl extends ServiceImpl<PatrolReportMapper, PatrolDevice> implements IPatrolReportService {

    @Autowired
    private PatrolReportMapper reportMapper;

    @Override
    public PatrolReportVo getPortalCountAll(SearchVo searchVo) {
        return reportMapper.getPortalCountAll(searchVo);
    }
    
    @Override
    public List<PatrolReportVo> getSiteCharts(SearchVo searchVo) {
        return reportMapper.getSiteCharts(searchVo);
    }

	@Override
	public List<PatrolReportVo> getPlanCharts(SearchVo searchVo) {
		return reportMapper.getPlanCharts(searchVo);
	}

	@Override
	public List<PatrolReportVo> getDeviceStatusCountCharts(SearchVo searchVo) {
		return reportMapper.getDeviceStatusCountCharts(searchVo);
	}
}