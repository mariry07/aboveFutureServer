package com.tianbo.smartcity.modules.your.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tianbo.smartcity.modules.your.entity.Floor;

import java.util.List;

/**
 * 楼层信息接口
 * @author Pangzy
 */
public interface IFloorService extends IService<Floor> {

    List<Floor> getAllByCompanyIdAndDepartmentId(Floor floor);
}