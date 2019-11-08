package com.tianbo.smartcity.modules.patrol.dao;

import java.util.List;

import com.tianbo.smartcity.base.SmartCityBaseDao;
import com.tianbo.smartcity.modules.patrol.entity.PatrolDeviceType;

/**
 * 设备类型数据处理层
 * @author fanshaohai
 */
public interface PatrolDeviceTypeDao extends SmartCityBaseDao<PatrolDeviceType,String> {
	
	List<PatrolDeviceType> findByParentId(String parentId);

	List<PatrolDeviceType> findByNameLike(String title);

}