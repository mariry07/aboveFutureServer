package com.tianbo.smartcity.modules.your.serviceimpl;

import com.tianbo.smartcity.modules.your.dao.mapper.sensorLogMapper;
import com.tianbo.smartcity.modules.your.entity.sensorLog;
import com.tianbo.smartcity.modules.your.service.IsensorLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 日志表接口实现
 * @author Pangzy
 */
@Slf4j
@Service
@Transactional
public class IsensorLogServiceImpl extends ServiceImpl<sensorLogMapper, sensorLog> implements IsensorLogService {

    @Autowired
    private sensorLogMapper sensorLogMapper;
}