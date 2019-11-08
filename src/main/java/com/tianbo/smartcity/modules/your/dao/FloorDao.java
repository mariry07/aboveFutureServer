package com.tianbo.smartcity.modules.your.dao;

import com.tianbo.smartcity.base.SmartCityBaseDao;
import com.tianbo.smartcity.modules.your.entity.Floor;

import java.util.List;

/**
 * 主机故障数据处理层
 * @author Pangzy
 */
public interface FloorDao extends SmartCityBaseDao<Floor,String> {

    List<Floor> findListByName(String name);
    
    List<Floor> findByParentId(String parentId);

	List<Floor> findByParentIdAndDelFlag(String parentId, Integer delFlag);
}