package com.tianbo.smartcity.modules.patrol.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.patrol.entity.PatrolCheckItem;
import com.tianbo.smartcity.modules.patrol.entity.PatrolDevice;
import com.tianbo.smartcity.modules.patrol.entity.PatrolDeviceType;

/**
 * 设备类型巡查项接口
 * @author fanshaohai
 */
public interface PatrolCheckItemService extends SmartCityBaseService<PatrolCheckItem,String> {

    /**
    * 多条件分页获取
    * @param patrolCheckItem
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<PatrolCheckItem> findByCondition(PatrolCheckItem patrolCheckItem, SearchVo searchVo, Pageable pageable);
    
    List<PatrolCheckItem> findAllByCondition(PatrolCheckItem patrolCheckItem);
    
	List<PatrolCheckItem> findByDeviceTypeId(String deviceTypeId);

	List<PatrolCheckItem> findByName(String name);

}