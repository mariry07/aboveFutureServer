package com.tianbo.smartcity.modules.your.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tianbo.smartcity.common.vo.PageVo;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.base.entity.User;
import com.tianbo.smartcity.modules.your.entity.Sensor;
import com.tianbo.smartcity.modules.your.vo.SensorVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 传感器表数据处理层
 * @author Pangzy
 */
public interface SensorMapper extends BaseMapper<Sensor> {

    List<SensorVo> findPage(@Param("sensorVo") SensorVo sensorVo, @Param("searchVo") SearchVo searchVo, @Param("pageVo") PageVo pageVo);

    int findPageCount(SensorVo sensorVo);

    List<Sensor> findPage2(Page<Sensor> page, @Param("ew") Wrapper<Sensor> queryWrapper);
}