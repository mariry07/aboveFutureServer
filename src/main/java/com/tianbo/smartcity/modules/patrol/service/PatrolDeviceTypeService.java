package com.tianbo.smartcity.modules.patrol.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.patrol.entity.PatrolCheckItem;
import com.tianbo.smartcity.modules.patrol.entity.PatrolDeviceType;

/**
 * 设备类型接口
 * @author fanshaohai
 */
public interface PatrolDeviceTypeService extends SmartCityBaseService<PatrolDeviceType,String> {
	
	/**
	 * 父id查询子节点
	 * @param parentId
	 * @return
	 */
	List<PatrolDeviceType> findByParentId(String parentId);
	
	/**
     * 巡查类型名模糊搜索
     * @param title
     * @return
     */
    List<PatrolDeviceType> findByTitleLike(String title);


    /**
    * 多条件分页获取
    * @param patrolDeviceType
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<PatrolDeviceType> findByCondition(PatrolDeviceType patrolDeviceType, SearchVo searchVo, Pageable pageable);

	List<PatrolDeviceType> findAllByCondition(PatrolDeviceType condition);
}