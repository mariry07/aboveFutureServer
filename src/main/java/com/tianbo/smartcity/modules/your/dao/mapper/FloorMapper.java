package com.tianbo.smartcity.modules.your.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tianbo.smartcity.modules.your.entity.Floor;

import java.util.List;

/**
 * 楼层信息数据处理层
 * @author Pangzy
 */
public interface FloorMapper extends BaseMapper<Floor> {

    List<Floor> getAllByCompanyIdAndDepartmentId(Floor floor);
}