package com.tianbo.smartcity.modules.your.serviceimpl;

import com.tianbo.smartcity.modules.your.dao.mapper.SensorTypeMapper;
import com.tianbo.smartcity.modules.your.entity.SensorType;
import com.tianbo.smartcity.modules.your.service.ISensorTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 传感器类型接口实现
 * @author Pangzy
 */
@Slf4j
@Service
@Transactional
public class ISensorTypeServiceImpl extends ServiceImpl<SensorTypeMapper, SensorType> implements ISensorTypeService {

    @Autowired
    private SensorTypeMapper sensorTypeMapper;

    @Override
    public List<SensorType> getListBySystemType(SensorType sensorType) {
        return sensorTypeMapper.getListBySystemType(sensorType);
    }
}