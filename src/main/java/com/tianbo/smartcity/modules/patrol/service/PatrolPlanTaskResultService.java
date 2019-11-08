package com.tianbo.smartcity.modules.patrol.service;

import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.patrol.entity.PatrolDevice;
import com.tianbo.smartcity.modules.patrol.entity.PatrolPlanTaskResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 巡查结果接口
 * @author fanshaohai
 */
public interface PatrolPlanTaskResultService extends SmartCityBaseService<PatrolPlanTaskResult,String> {

    /**
    * 多条件分页获取
    * @param patrolPlanTaskResult
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<PatrolPlanTaskResult> findByCondition(PatrolPlanTaskResult patrolPlanTaskResult, SearchVo searchVo, Pageable pageable);
    
    List<PatrolPlanTaskResult> findAllByCondition(PatrolPlanTaskResult patrolPlanTaskResult);
}