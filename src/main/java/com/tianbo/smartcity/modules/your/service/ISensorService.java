package com.tianbo.smartcity.modules.your.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tianbo.smartcity.common.vo.PageVo;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.entity.Sensor;
import com.tianbo.smartcity.modules.your.vo.SensorVo;
import io.micrometer.core.instrument.search.Search;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 传感器表接口
 * @author Pangzy
 */
public interface ISensorService extends IService<Sensor> {

    List<SensorVo> findPage(SensorVo sensorVo, SearchVo searchVo, PageVo pageVo);

    int findPageCount(SensorVo sensorVo);

    IPage<Sensor> findPage2(PageVo pageVo, Wrapper wrapper);
}