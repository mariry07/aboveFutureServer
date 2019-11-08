package com.tianbo.smartcity.modules.your.service;

import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.entity.Floor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 主机故障接口
 * @author Pangzy
 */
public interface FloorService extends SmartCityBaseService<Floor,String> {

    /**
    * 多条件分页获取
    * @param floor
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<Floor> findByCondition(Floor floor, SearchVo searchVo, Pageable pageable);

    /**
     * 根据name查询数据
     * @param name
     * @return
     */
    List<Floor> findListByName(String name);
    
    /**
	 * 父id查询子节点
	 * @param parentId
	 * @return
	 */
	List<Floor> findByParentId(String parentId);
	
	/**
	 * 父id查询子节点
	 * @param parentId
	 * @return
	 */
	List<Floor> findByParentIdAndDelFlag(String parentId,Integer delFlag);
}