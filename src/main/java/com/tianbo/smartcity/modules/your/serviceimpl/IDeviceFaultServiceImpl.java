package com.tianbo.smartcity.modules.your.serviceimpl;

import com.tianbo.smartcity.common.vo.PageVo;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.dao.mapper.DeviceFaultMapper;
import com.tianbo.smartcity.modules.your.entity.DeviceFault;
import com.tianbo.smartcity.modules.your.service.IDeviceFaultService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tianbo.smartcity.modules.your.vo.DeviceFaultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 楼层信息接口实现
 * @author Pangzy
 */
@Slf4j
@Service
@Transactional
public class IDeviceFaultServiceImpl extends ServiceImpl<DeviceFaultMapper, DeviceFault> implements IDeviceFaultService {

    @Autowired
    private DeviceFaultMapper deviceFaultMapper;

    @Override
    public DeviceFaultVo getDeviceFaultAll(DeviceFault deviceFault) {
        return deviceFaultMapper.getDeviceFaultAll(deviceFault);
    }

    @Override
    public List<DeviceFaultVo> getDeviceFaultCharts(DeviceFault deviceFault,SearchVo searchVo) {
        return deviceFaultMapper.getDeviceFaultCharts(deviceFault,searchVo);
    }


}