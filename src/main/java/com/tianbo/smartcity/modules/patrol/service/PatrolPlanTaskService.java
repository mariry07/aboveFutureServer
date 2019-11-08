package com.tianbo.smartcity.modules.patrol.service;

import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.patrol.entity.PatrolVo;
import com.tianbo.smartcity.modules.patrol.entity.PatrolPlanTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 巡查/巡更任务接口
 * @author fanshaohai
 */
public interface PatrolPlanTaskService extends SmartCityBaseService<PatrolPlanTask,String> {

    /**
    * 多条件分页获取
    * @param patrolPlanTask
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<PatrolPlanTask> findByCondition(PatrolPlanTask patrolPlanTask, SearchVo searchVo, Pageable pageable);

    /**
     * 获取巡更任务
     * @param id
     * @return
     */
    PatrolVo getPatrol(String id);
}