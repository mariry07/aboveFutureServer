package com.tianbo.smartcity.quartz.jobs;

import com.tianbo.smartcity.common.enums.PatrolPlanType;
import com.tianbo.smartcity.modules.patrol.entity.PatrolPlanTask;
import com.tianbo.smartcity.modules.patrol.service.PatrolPlanTaskService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 巡更计划定时任务
 * @author WangCH
 */
@Slf4j
@Service
@Transactional
public class PatrolJob implements Job {

    @Autowired
    private PatrolPlanTaskService patrolPlanTaskService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try{
            log.info("生成巡更/巡查任务");
            //参数
            JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
            //实体
            PatrolPlanTask patrolPlanTask = new PatrolPlanTask();
            //巡更或巡查任务类型
            Integer isPatrol = dataMap.getIntValue("isPatrol");
            patrolPlanTask.setIsPatrol(isPatrol);
            //部门id
            String departmentId = dataMap.getString("departmentId");
            patrolPlanTask.setDepartmentId(departmentId);
            //做任务的用户id
            String doUserId = dataMap.getString("doUserId");
            patrolPlanTask.setDoUserId(doUserId);
            //巡更计划id
            String planId = dataMap.getString("planId");
            patrolPlanTask.setPlanId(planId);
            //巡更类型
            Integer type = dataMap.getIntValue("type");
            patrolPlanTask.setType(type);
            //巡更类型名称
            String typeName = PatrolPlanType.getNameByCode(type);
            patrolPlanTask.setTypeName(typeName);
            //设置日期格式
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            //巡更任务名称
            String name = dataMap.getString("name");
            patrolPlanTask.setName(name + "_" + df.format(new Date()));
            //巡更开始时间
            patrolPlanTask.setStartTime(new Date());
            //巡更结束时间
            Integer limitDays = PatrolPlanType.getDaysByCode(type);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, limitDays);
            patrolPlanTask.setEndTime(calendar.getTime());
            //任务状态
            patrolPlanTask.setStatus(1);
            //任务完成率
            patrolPlanTask.setFinishedRate(0.0f);
            patrolPlanTaskService.save(patrolPlanTask);
        }catch (Exception e){
            log.info(e.toString());
        }
    }

}
