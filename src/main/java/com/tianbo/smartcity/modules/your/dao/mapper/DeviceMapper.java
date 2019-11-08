package com.tianbo.smartcity.modules.your.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.entity.Device;
import com.tianbo.smartcity.modules.your.vo.DeviceFaultVo;
import com.tianbo.smartcity.modules.your.vo.DeviceVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备信息数据处理层
 * @author Pangzy
 */
public interface DeviceMapper extends BaseMapper<Device> {

    Device selectByCode(Device device);

    List<DeviceVo> getDeviceTypeList(@Param("device") Device device, @Param("searchVo")SearchVo searchVo);
}