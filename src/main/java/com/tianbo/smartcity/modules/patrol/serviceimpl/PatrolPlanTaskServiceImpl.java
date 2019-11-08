package com.tianbo.smartcity.modules.patrol.serviceimpl;

import cn.hutool.core.util.StrUtil;
import com.tianbo.smartcity.common.enums.PatrolPlanType;
import com.tianbo.smartcity.modules.patrol.dao.PatrolMarkDao;
import com.tianbo.smartcity.modules.patrol.dao.PatrolPlanSiteDao;
import com.tianbo.smartcity.modules.patrol.dao.PatrolPlanTaskDao;
import com.tianbo.smartcity.modules.patrol.dao.PatrolSiteDao;
import com.tianbo.smartcity.modules.patrol.entity.*;
import com.tianbo.smartcity.modules.patrol.service.PatrolPlanService;
import com.tianbo.smartcity.modules.patrol.service.PatrolPlanTaskService;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import com.tianbo.smartcity.common.vo.SearchVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 巡查/巡更任务接口实现
 * @author fanshaohai
 */
@Slf4j
@Service
@Transactional
public class PatrolPlanTaskServiceImpl implements PatrolPlanTaskService {

    @Autowired
    private PatrolPlanTaskDao patrolPlanTaskDao;

    @Autowired
    private PatrolPlanSiteDao patrolPlanSiteDao;

    @Autowired
    private PatrolMarkDao patrolMarkDao;

    @Autowired
    private PatrolSiteDao patrolSiteDao;

    @Autowired
    private PatrolPlanService patrolPlanService;

    @Override
    public PatrolPlanTaskDao getRepository() {
        return patrolPlanTaskDao;
    }

    @Override
    public Page<PatrolPlanTask> findByCondition(PatrolPlanTask patrolPlanTask, SearchVo searchVo, Pageable pageable) {

        return patrolPlanTaskDao.findAll(new Specification<PatrolPlanTask>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<PatrolPlanTask> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                // TODO 可添加你的其他搜索过滤条件 默认已有创建时间过滤
                Path<Integer> statusField = root.get("status");
                Path<Integer> doUserIdField = root.get("doUserId");
                Path<Integer> isPatrolField = root.get("isPatrol");
                Path<Date> startTimeField=root.get("startTime");
                Path<Date> endTimeField=root.get("endTime");
                Path<String> nameField = root.get("name");
                List<Predicate> list = new ArrayList<Predicate>();
            	 //巡查点查询

                list.add(cb.equal(isPatrolField, patrolPlanTask.getIsPatrol()));
                if(StrUtil.isNotBlank(searchVo.getStartDate())&&StrUtil.isNotBlank(searchVo.getEndDate())){
                    Date start = DateUtil.parse(searchVo.getStartDate());
                    Date end = DateUtil.parse(searchVo.getEndDate());
//                    list.add(cb.between(createTimeField, start, DateUtil.endOfDay(end)));
                    list.add(cb.greaterThanOrEqualTo(startTimeField,start));
                    list.add(cb.lessThanOrEqualTo(endTimeField,end));
                }               
                //任务名称
                if(StrUtil.isNotBlank(patrolPlanTask.getName())){
                    list.add(cb.like(nameField,'%' + patrolPlanTask.getName() + '%'));
                }
            	//状态
                if(patrolPlanTask.getStatus() != null) {
                	list.add(cb.equal(statusField, patrolPlanTask.getStatus()));
                }
                //执行人员
                if(patrolPlanTask.getDoUserId() != null&&!"".equals(patrolPlanTask.getDoUserId())) {                	
                	list.add(cb.equal(doUserIdField, patrolPlanTask.getDoUserId()));
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
        //获取任务
        PatrolPlanTask patrolPlanTask = get(id);
        if (patrolPlanTask.getType() != null ) {
            //巡查/巡更类型名称
            try {
                String typeName = PatrolPlanType.getNameByCode(patrolPlanTask.getType());
                patrolPlanTask.setTypeName(typeName);
            }catch(Exception e) {
                patrolPlanTask.setTypeName("");
            }
        }
        patrolVo.setPatrolPlanTask(patrolPlanTask);
        //获取计划
        PatrolPlan patrolPlan = patrolPlanService.get(patrolPlanTask.getPlanId());
        if(patrolPlan.getIsPatrol() == 0){
            //获取巡更点
            List<PatrolPlanSite> patrolPlanSites = patrolPlanSiteDao.getAllByPlanIdOrderBySiteIndex(patrolPlan.getId());
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
            List<PatrolPlanSite> patrolPlanSites = patrolPlanSiteDao.getAllByPlanIdOrderBySiteIndex(patrolPlan.getId());
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
}