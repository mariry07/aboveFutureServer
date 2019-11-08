package com.tianbo.smartcity.modules.patrol.service;

import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.patrol.entity.PatrolUserRoutes;

import java.util.List;


/**
 * 用户轨迹记录接口
 * @author WangCH
 */
public interface PatrolUserRoutesService extends SmartCityBaseService<PatrolUserRoutes,String> {

    /**
     *  多条件获取
     * @param patrolUserRoutes
     * @param searchVo
     * @return
     */
     List<PatrolUserRoutes> findByCondition(PatrolUserRoutes patrolUserRoutes, SearchVo searchVo);


}