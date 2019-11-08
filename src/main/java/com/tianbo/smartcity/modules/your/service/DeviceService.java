package com.tianbo.smartcity.modules.your.service;

import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.entity.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 设备表接口
 * @author Pangzy
 */
public interface DeviceService extends SmartCityBaseService<Device,String> {

    /**
    * 多条件分页获取
    * @param device
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<Device> findByCondition(Device device, SearchVo searchVo, Pageable pageable);

    Device findByCodeAndDelFlagAndIdNot(String code, String id);
}