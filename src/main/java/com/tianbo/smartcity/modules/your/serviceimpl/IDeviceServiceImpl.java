package com.tianbo.smartcity.modules.your.serviceimpl;

import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.dao.mapper.DeviceMapper;
import com.tianbo.smartcity.modules.your.entity.Device;
import com.tianbo.smartcity.modules.your.service.IDeviceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tianbo.smartcity.modules.your.vo.DeviceFaultVo;
import com.tianbo.smartcity.modules.your.vo.DeviceVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备信息接口实现
 * @author Pangzy
 */
@Slf4j
@Service
@Transactional
public class IDeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public Device selectByCode(Device device) {
        return deviceMapper.selectByCode(device);
    }

    @Override
    public List<DeviceVo> getDeviceTypeList(Device device, SearchVo searchVo) {
        return deviceMapper.getDeviceTypeList(device,searchVo);
    }
}