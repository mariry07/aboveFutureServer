package com.tianbo.smartcity.modules.patrol.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.patrol.entity.PatrolDevice;
import com.tianbo.smartcity.modules.patrol.vo.PatrolReportVo;
import com.tianbo.smartcity.modules.your.entity.ServerAlarm;
import com.tianbo.smartcity.modules.your.vo.ServerAlarmVo;

import java.util.List;

/**
 * 巡查报表接口
 * @author fanshaohai
 */
public interface IPatrolReportService extends IService<PatrolDevice> {

	PatrolReportVo getPortalCountAll(SearchVo searchVo);
	
    List<PatrolReportVo> getSiteCharts(SearchVo searchVo);

	List<PatrolReportVo> getPlanCharts(SearchVo searchVo);

	List<PatrolReportVo> getDeviceStatusCountCharts(SearchVo searchVo);

}