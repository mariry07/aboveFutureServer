package com.tianbo.smartcity.modules.your.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tianbo.smartcity.common.utils.PageUtil;
import com.tianbo.smartcity.common.vo.PageVo;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.dao.mapper.SensorMapper;
import com.tianbo.smartcity.modules.your.entity.Sensor;
import com.tianbo.smartcity.modules.your.service.ISensorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tianbo.smartcity.modules.your.vo.SensorVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 传感器表接口实现
 * @author Pangzy
 */
@Slf4j
@Service
@Transactional
public class ISensorServiceImpl extends ServiceImpl<SensorMapper, Sensor> implements ISensorService {

    @Autowired
    private SensorMapper sensorMapper;

    @Override
    public List<SensorVo> findPage(SensorVo sensorVo, SearchVo searchVo, PageVo pageVo) {
        return sensorMapper.findPage(sensorVo,searchVo,pageVo);
    }

    @Override
    public int findPageCount(SensorVo sensorVo) {
        return sensorMapper.findPageCount(sensorVo);
    }

    @Override
    public IPage<Sensor> findPage2( PageVo pageVo, Wrapper wrapper) {
        Page page=PageUtil.initMpPage(pageVo);

        return page.setRecords(sensorMapper.findPage2(page,wrapper));
    }


}