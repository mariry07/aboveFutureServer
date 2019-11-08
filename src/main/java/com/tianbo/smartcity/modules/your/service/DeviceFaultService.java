package com.tianbo.smartcity.modules.your.service;

import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.entity.DeviceFault;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 主机故障接口
 * @author Pangzy
 */
public interface DeviceFaultService extends SmartCityBaseService<DeviceFault,String> {

    /**
    * 多条件分页获取
    * @param deviceFault
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<DeviceFault> findByCondition(DeviceFault deviceFault, SearchVo searchVo, Pageable pageable);

    Page<DeviceFault> getDeviceFaultList(DeviceFault deviceFault, SearchVo searchVo, Pageable initPage);
}