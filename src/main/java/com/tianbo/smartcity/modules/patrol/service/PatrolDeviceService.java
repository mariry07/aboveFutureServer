package com.tianbo.smartcity.modules.patrol.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.patrol.entity.PatrolDevice;

/**
 * 巡查设备接口
 * @author fanshaohai
 */
public interface PatrolDeviceService extends SmartCityBaseService<PatrolDevice,String> {

    /**
    * 多条件分页获取
    * @param patrolDevice
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<PatrolDevice> findByCondition(PatrolDevice patrolDevice, SearchVo searchVo, Pageable pageable);

	List<PatrolDevice> findAllByCondition(PatrolDevice patrolDevice);
}