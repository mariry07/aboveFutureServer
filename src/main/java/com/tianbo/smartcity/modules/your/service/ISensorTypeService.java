package com.tianbo.smartcity.modules.your.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tianbo.smartcity.modules.your.entity.SensorType;

import java.util.List;

/**
 * 传感器类型接口
 * @author Pangzy
 */
public interface ISensorTypeService extends IService<SensorType> {

    List<SensorType> getListBySystemType(SensorType sensorType);
}