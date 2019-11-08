package com.tianbo.smartcity.modules.your.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.entity.Device;
import com.tianbo.smartcity.modules.your.vo.DeviceFaultVo;
import com.tianbo.smartcity.modules.your.vo.DeviceVo;

import java.util.List;

/**
 * 设备信息接口
 * @author Pangzy
 */
public interface IDeviceService extends IService<Device> {

    Device selectByCode(Device device);

    List<DeviceVo> getDeviceTypeList(Device device, SearchVo searchVo);
}