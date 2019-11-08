package com.tianbo.smartcity.modules.patrol.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.patrol.entity.PatrolSite;

/**
 * 巡查点接口
 * @author fanshaohai
 */
public interface PatrolSiteService extends SmartCityBaseService<PatrolSite,String> {

    /**
    * 多条件分页获取
    * @param patrolSite
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<PatrolSite> findByCondition(PatrolSite patrolSite, SearchVo searchVo, Pageable pageable);

	PatrolSite add(PatrolSite site);

	List<PatrolSite> findAllByCondition(PatrolSite patrolSite);
}