package com.tianbo.smartcity.modules.your.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.entity.DeviceFault;
import com.tianbo.smartcity.modules.your.vo.DeviceFaultVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 楼层信息接口
 * @author Pangzy
 */
public interface IDeviceFaultService extends IService<DeviceFault> {

    DeviceFaultVo getDeviceFaultAll(DeviceFault deviceFault);

    List<DeviceFaultVo> getDeviceFaultCharts(DeviceFault deviceFault,SearchVo searchVo);
}