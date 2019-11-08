package com.tianbo.smartcity.modules.patrol.service;

import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.patrol.entity.PatrolVo;
import com.tianbo.smartcity.modules.patrol.entity.PatrolPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 巡查/巡更计划接口
 * @author fanshaohai
 */
public interface PatrolPlanService extends SmartCityBaseService<PatrolPlan,String> {

    /**
    * 多条件分页获取
    * @param patrolPlan
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<PatrolPlan> findByCondition(PatrolPlan patrolPlan, SearchVo searchVo, Pageable pageable);

    /**
     * 获取巡更计划
     * @param id
     * @return
     */
    PatrolVo getPatrol(String id);

    /**
     * 新增计划
     * @param patrolPlan
     * @param list
     * @return
     */
    PatrolPlan add(PatrolPlan patrolPlan, List<String> list) throws Exception;

    /**
     * 注销/暂停计划
     * @param id
     * @return
     */
    String pause(String id) throws Exception;

    /**
     * 恢复计划
     * @param id
     * @return
     */
    String resume(String id) throws Exception;

    /**
     * 批量删除计划
     * @param ids
     * @return
     */
    String delete(String[] ids) throws Exception;



}