package com.tianbo.smartcity.modules.your.serviceimpl;

import com.tianbo.smartcity.modules.your.dao.mapper.FloorMapper;
import com.tianbo.smartcity.modules.your.entity.Floor;
import com.tianbo.smartcity.modules.your.service.IFloorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 楼层信息接口实现
 * @author Pangzy
 */
@Slf4j
@Service
@Transactional
public class IFloorServiceImpl extends ServiceImpl<FloorMapper, Floor> implements IFloorService {

    @Autowired
    private FloorMapper floorMapper;

    @Override
    public List<Floor> getAllByCompanyIdAndDepartmentId(Floor floor) {
        return floorMapper.getAllByCompanyIdAndDepartmentId(floor);
    }
}