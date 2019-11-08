package com.tianbo.smartcity.modules.patrol.service;

import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.patrol.entity.PatrolPlanSite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 巡查计划巡查点/巡更计划巡更点接口
 * @author fanshaohai
 */
public interface PatrolPlanSiteService extends SmartCityBaseService<PatrolPlanSite,String> {

    /**
    * 多条件分页获取
    * @param patrolPlanSite
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<PatrolPlanSite> findByCondition(PatrolPlanSite patrolPlanSite, SearchVo searchVo, Pageable pageable);
}