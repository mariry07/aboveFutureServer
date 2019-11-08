package com.tianbo.smartcity.modules.your.dao;

import com.tianbo.smartcity.base.SmartCityBaseDao;
import com.tianbo.smartcity.modules.your.entity.Device;

/**
 * 设备表数据处理层
 * @author Pangzy
 */
public interface DeviceDao extends SmartCityBaseDao<Device, String> {

    Device findByCodeAndDelFlagAndIdNot(String code,Integer delFlag, String id);
}