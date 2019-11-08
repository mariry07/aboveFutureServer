package com.tianbo.smartcity.modules.patrol.dao;

import java.util.List;

import com.tianbo.smartcity.base.SmartCityBaseDao;
import com.tianbo.smartcity.modules.patrol.entity.PatrolCheckItem;

/**
 * 设备类型巡查项数据处理层
 * @author fanshaohai
 */
public interface PatrolCheckItemDao extends SmartCityBaseDao<PatrolCheckItem,String> {

	List<PatrolCheckItem> findByDeviceTypeId(String deviceTypeId);

	List<PatrolCheckItem> findByName(String name);

}