package com.tianbo.smartcity.modules.your.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tianbo.smartcity.common.vo.PageVo;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.entity.DeviceFault;
import com.tianbo.smartcity.modules.your.vo.DeviceFaultVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 楼层信息数据处理层
 * @author Pangzy
 */
public interface DeviceFaultMapper extends BaseMapper<DeviceFault> {

    DeviceFaultVo getDeviceFaultAll(DeviceFault deviceFault);

    List<DeviceFaultVo> getDeviceFaultCharts(@Param("deviceFault") DeviceFault deviceFault,@Param("searchVo") SearchVo searchVo);
}