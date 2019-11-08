package com.tianbo.smartcity.modules.your.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tianbo.smartcity.modules.your.entity.SensorType;

import java.util.List;

/**
 * 传感器类型数据处理层
 * @author Pangzy
 */
public interface SensorTypeMapper extends BaseMapper<SensorType> {

    List<SensorType> getListBySystemType(SensorType sensorType);
}