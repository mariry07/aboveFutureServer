package com.tianbo.smartcity.modules.patrol.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.patrol.entity.PatrolDevice;
import com.tianbo.smartcity.modules.patrol.vo.PatrolReportVo;

/**
 * 巡查报表处理层
 * @author fanshaohai
 */
public interface PatrolReportMapper extends BaseMapper<PatrolDevice> {

	PatrolReportVo getPortalCountAll(SearchVo searchVo);

	List<PatrolReportVo> getSiteCharts(SearchVo searchVo);

	List<PatrolReportVo> getPlanCharts(SearchVo searchVo);

	List<PatrolReportVo> getDeviceStatusCountCharts(SearchVo searchVo);

}