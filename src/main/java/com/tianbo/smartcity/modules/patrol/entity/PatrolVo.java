package com.tianbo.smartcity.modules.patrol.entity;

import lombok.Data;

import java.util.List;

/**
 * 巡更巡检信息实体
 */
@Data
public class PatrolVo {

    private static final long serialVersionUID = 1L;

    /**
     * 计划
     */
    private PatrolPlan patrolPlan;

    /**
     * 任务
     */
    private PatrolPlanTask patrolPlanTask;

    /**
     * 巡更点
     */
    private List<PatrolMark> patrolMarks;

    /**
     * 巡检点
     */
    private List<PatrolSite> patrolSites;


}
