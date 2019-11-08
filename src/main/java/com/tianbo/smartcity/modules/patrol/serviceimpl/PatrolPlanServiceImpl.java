package com.tianbo.smartcity.modules.patrol.serviceimpl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.tianbo.smartcity.common.enums.PatrolPlanType;
import com.tianbo.smartcity.common.utils.SecurityUtil;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.base.entity.User;
import com.tianbo.smartcity.modules.patrol.dao.PatrolMarkDao;
import com.tianbo.smartcity.modules.patrol.dao.PatrolPlanDao;
import com.tianbo.smartcity.modules.patrol.dao.PatrolPlanSiteDao;
import com.tianbo.smartcity.modules.patrol.dao.PatrolSiteDao;
import com.tianbo.smartcity.modules.patrol.entity.*;
import com.tianbo.smartcity.modules.patrol.service.PatrolPlanService;
import com.tianbo.smartcity.modules.patrol.service.PatrolPlanTaskService;
import com.tianbo.smartcity.quartz.jobs.PatrolJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 巡查/巡更计划接口实现
 * @author fanshaohai
 */
@Slf4j
@Service
@Transactional
public class PatrolPlanServiceImpl implements PatrolPlanService {

    @Autowired
    private PatrolPlanDao patrolPlanDao;

    @Autowired
    private PatrolMarkDao patrolMarkDao;

    @Autowired
    private PatrolSiteDao patrolSiteDao;

    @Autowired
    private PatrolPlanSiteDao patrolPlanSiteDao;

    @Autowired
    private PatrolPlanTaskService patrolPlanTaskService;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private Scheduler scheduler;

    @Override
    public PatrolPlanDao getRepository() {
        return patrolPlanDao;
    }

    @Override
    public Page<PatrolPlan> findByCondition(PatrolPlan patrolPlan, SearchVo searchVo, Pageable pageable) {

        return patrolPlanDao.findAll(new Specification<PatrolPlan>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<PatrolPlan> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                // TODO 可添加你的其他搜索过滤条件 默认已有创建时间过滤
                Path<Date> createTimeField=root.get("createTime");
                Path<Integer> typeField = root.get("type");
                Path<Integer> isPatrolField = root.get("isPatrol");
                Path<String> nameField = root.get("name");
                //创建时间
                if(StrUtil.isNotBlank(searchVo.getStartDate())&&StrUtil.isNotBlank(searchVo.getEndDate())){
                    Date start = DateUtil.parse(searchVo.getStartDate());
                    Date end = DateUtil.parse(searchVo.getEndDate());
                    list.add(cb.between(createTimeField, start, DateUtil.endOfDay(end)));
                }
                //任务名称
                if(StrUtil.isNotBlank(patrolPlan.getName())){
                    list.add(cb.like(nameField,'%' + patrolPlan.getName() + '%'));
                }
                //巡查巡更查询
            	list.add(cb.equal(isPatrolField, patrolPlan.getIsPatrol()));
                //类型
                if(patrolPlan.getType() != null) {
                	list.add(cb.equal(typeField, patrolPlan.getType()));
                }
                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }

    @Override
    public PatrolVo getPatrol(String id){
        PatrolVo patrolVo = new PatrolVo();
        //获取计划
        PatrolPlan patrolPlan = get(id);
        if (patrolPlan.getType() != null ) {
            //巡查/巡更类型名称
            try {
                String typeName = PatrolPlanType.getNameByCode(patrolPlan.getType());
                patrolPlan.setTypeName(typeName);
            }catch(Exception e) {
                patrolPlan.setTypeName("");
            }
        }
        patrolVo.setPatrolPlan(patrolPlan);
        if(patrolPlan.getIsPatrol() == 0){
            //获取巡更点
           List<PatrolPlanSite> patrolPlanSites = patrolPlanSiteDao.getAllByPlanIdOrderBySiteIndex(id);
           if(patrolPlanSites.size() > 0){
               List<String> ids = new ArrayList<>();
               for(PatrolPlanSite item: patrolPlanSites){
                   ids.add(item.getSiteId());
               }
               List<PatrolMark> patrolMarks = patrolMarkDao.getListById(ids);
               patrolVo.setPatrolMarks(patrolMarks);
           }
        }else{
            //获取巡检点
            List<PatrolPlanSite> patrolPlanSites = patrolPlanSiteDao.getAllByPlanIdOrderBySiteIndex(id);
            if(patrolPlanSites.size() > 0){
                List<String> ids = new ArrayList<>();
                for(PatrolPlanSite item: patrolPlanSites){
                    ids.add(item.getSiteId());
                }
                List<PatrolSite> patrolSites = patrolSiteDao.getListById(ids);
                patrolVo.setPatrolSites(patrolSites);
            }
        }
        return patrolVo;
    }

    @Override
    public PatrolPlan add(PatrolPlan patrolPlan, List<String> list) throws Exception{
        //设置部门ID
        User user = securityUtil.getCurrUser();
        //部门id
        patrolPlan.setDepartmentId(user.getDepartmentId());
        //正在执行
        patrolPlan.setIsDoing(1);
        //完成率
        patrolPlan.setFinishedRate(0.0f);
        //新增巡更计划
        PatrolPlan result = save(patrolPlan);
        //巡更计划巡更点关联表
        List<PatrolPlanSite> patrolPlanSites = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            PatrolPlanSite patrolPlanSite = new PatrolPlanSite();
            patrolPlanSite.setPlanId(patrolPlan.getId());
            patrolPlanSite.setSiteId(list.get(i));
            patrolPlanSite.setSiteIndex(i);
            patrolPlanSites.add(patrolPlanSite);
        }
        patrolPlanSiteDao.saveAll(patrolPlanSites);
        //生成巡更任务
        PatrolPlanTask patrolPlanTask = new PatrolPlanTask();
        //巡更或巡查任务类型
        patrolPlanTask.setIsPatrol(patrolPlan.getIsPatrol());
        //部门id
        patrolPlanTask.setDepartmentId(user.getDepartmentId());
        //做任务的用户id
        patrolPlanTask.setDoUserId(patrolPlan.getDoUserId());
        //巡更计划id
        patrolPlanTask.setPlanId(patrolPlan.getId());
        //巡更类型
        patrolPlanTask.setType(patrolPlan.getType());
        //巡更类型名称
        String typeName = PatrolPlanType.getNameByCode(patrolPlan.getType());
        patrolPlanTask.setTypeName(typeName);
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //巡更任务名称
        patrolPlanTask.setName(patrolPlan.getName() + "_" + df.format(new Date()));
        //巡更开始时间
        patrolPlanTask.setStartTime(new Date());
        //巡更结束时间
        Calendar calendar = Calendar.getInstance();
        if(patrolPlan.getType().equals(PatrolPlanType.TEMP.getCode())) {
            //临时任务
            calendar.add(Calendar.DATE, patrolPlan.getLimitDays());
        }else{
            //非临时
            calendar.add(Calendar.DATE, PatrolPlanType.getDaysByCode(patrolPlan.getType()));
        }
        patrolPlanTask.setEndTime(calendar.getTime());
        //任务状态
        patrolPlanTask.setStatus(1);
        //任务完成率
        patrolPlanTask.setFinishedRate(0.0f);
        patrolPlanTaskService.save(patrolPlanTask);
        if(!patrolPlan.getType().equals(PatrolPlanType.TEMP.getCode())){
            //非临时任务，定时生成巡更任务
            addSchedulerJob(patrolPlan);
        }
        return result;
    }

    @Override
    public String pause(String id) throws Exception{
        PatrolPlan patrolPlan = get(id);
        //暂停定时任务
        if(!patrolPlan.getType().equals(PatrolPlanType.TEMP.getCode())) {
            pauseJob(id);
        }
        //注销
        patrolPlan.setIsDoing(0);
        patrolPlanDao.saveAndFlush(patrolPlan);
        return "注销计划成功";
    }

    @Override
    public String resume(String id) throws Exception{
        PatrolPlan patrolPlan = get(id);
        //恢复定时任务
        if(!patrolPlan.getType().equals(PatrolPlanType.TEMP.getCode())) {
            resumeJob(id);
        }
        //注销
        patrolPlan.setIsDoing(1);
        patrolPlanDao.saveAndFlush(patrolPlan);
        return "恢复计划成功";
    }

    @Override
    public String delete(String[] ids) throws Exception{
        for (String id : ids) {
            PatrolPlan patrolPlan = get(id);
            if(!patrolPlan.getType().equals(PatrolPlanType.TEMP.getCode())) {
                //删除定时任务
                deleteJob(id);
            }
            //删除计划
            delete(id);
            //删除巡更点关系数据
            patrolPlanSiteDao.deleteByPlanId(id);
        }
        return "删除计划成功";
    }

    /**
     * 添加定时任务
     * @param patrolPlan
     */
    private void addSchedulerJob(PatrolPlan patrolPlan) throws Exception{
        // 启动调度器
        scheduler.start();
        //参数
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("planId", patrolPlan.getId());
        jobDataMap.put("type", patrolPlan.getType());
        jobDataMap.put("name", patrolPlan.getName());
        jobDataMap.put("limitDays", patrolPlan.getLimitDays());
        jobDataMap.put("isPatrol", patrolPlan.getIsPatrol());
        jobDataMap.put("departmentId", patrolPlan.getDepartmentId());
        jobDataMap.put("doUserId", patrolPlan.getDoUserId());
        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(PatrolJob.class)
                .withIdentity("PatrolJob_" + patrolPlan.getId(), patrolPlan.getId())
                .usingJobData(jobDataMap).build();
        //rule 表达式
        String cronExpression = PatrolPlanType.getRuleByCode(patrolPlan.getType());
        //表达式调度构建器(即任务执行的时间)
        // withMisfireHandlingInstructionDoNothing() 不触发立即执行
         CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionIgnoreMisfires();
        //按新的cronExpression表达式构建一个新的trigger
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, patrolPlan.getLimitDays());

        try {
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("PatrolJob_" + patrolPlan.getId(), patrolPlan.getId())
                    .startAt(new Date())
                    .endAt(calendar.getTime())
                    .withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
        }catch (Exception e){
           e.getStackTrace();
        }

 //       Date startTime = trigger.getStartTime();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(startTime);
//        calendar.add(Calendar.DATE, patrolPlan.getLimitDays());
//        trigger.getTriggerBuilder().endAt(calendar.getTime()).build();

    }

    /**
     * 暂停定时任务
     * @param id
     */
    private void pauseJob(String id) throws Exception{
        scheduler.pauseJob(JobKey.jobKey("PatrolJob_" + id, id));
    }

    /**
     * 恢复定时任务执行
     * @param id
     */
    private void resumeJob(String id) throws Exception{
        scheduler.resumeJob(JobKey.jobKey("PatrolJob_" + id, id));
    }

    /**
     * 删除定时任务
     * @param id
     */
    private void deleteJob(String id) throws Exception{
        scheduler.pauseTrigger(TriggerKey.triggerKey("PatrolJob_" + id, id));
        scheduler.unscheduleJob(TriggerKey.triggerKey("PatrolJob_" + id, id));
        scheduler.deleteJob(JobKey.jobKey("PatrolJob_" + id));
    }


}