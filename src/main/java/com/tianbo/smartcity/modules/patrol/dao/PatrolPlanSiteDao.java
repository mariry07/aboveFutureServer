package com.tianbo.smartcity.modules.patrol.dao;

import com.tianbo.smartcity.base.SmartCityBaseDao;
import com.tianbo.smartcity.modules.patrol.entity.PatrolPlanSite;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 巡查计划巡查点/巡更计划巡更点数据处理层
 * @author fanshaohai
 */
public interface PatrolPlanSiteDao extends SmartCityBaseDao<PatrolPlanSite,String> {

    /**
     * 根据计划id删除关系数据
     * @param planId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value="update tb_patrol_plan_site set del_flag = 1 where plan_id = ?1",nativeQuery = true)
    int deleteByPlanId(String planId);

    /**
     * 根据计划id获取巡更计划巡更点
     * @param planId
     * @return
     */
    List<PatrolPlanSite> getAllByPlanIdOrderBySiteIndex(String planId);


}