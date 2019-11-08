package com.tianbo.smartcity.modules.patrol.service;

import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.patrol.entity.PatrolDeviceRepair;
import com.tianbo.smartcity.modules.your.entity.Floor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 巡查设备隐患记录接口
 * @author fanshaohai
 */
public interface PatrolDeviceRepairService extends SmartCityBaseService<PatrolDeviceRepair,String> {

    /**
    * 多条件分页获取
    * @param patrolDeviceRepair
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<PatrolDeviceRepair> findByCondition(PatrolDeviceRepair patrolDeviceRepair, SearchVo searchVo, Pageable pageable);
}