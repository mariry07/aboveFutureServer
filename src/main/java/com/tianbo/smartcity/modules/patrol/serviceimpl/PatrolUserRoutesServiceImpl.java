package com.tianbo.smartcity.modules.patrol.serviceimpl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.patrol.dao.PatrolUserRoutesDao;
import com.tianbo.smartcity.modules.patrol.entity.PatrolUserRoutes;
import com.tianbo.smartcity.modules.patrol.service.PatrolUserRoutesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 用户轨迹记录接口实现
 * @author WangCH
 */
@Slf4j
@Service
@Transactional
public class PatrolUserRoutesServiceImpl implements PatrolUserRoutesService {

    @Autowired
    private PatrolUserRoutesDao patrolUserRoutesDao;

    @Override
    public PatrolUserRoutesDao getRepository() {
        return patrolUserRoutesDao;
    }

    /**
     *  多条件获取
     * @param patrolMark
     * @param searchVo
     * @return
     */
    @Override
    public List<PatrolUserRoutes> findByCondition(PatrolUserRoutes patrolMark, SearchVo searchVo) {

        return patrolUserRoutesDao.findAll(new Specification<PatrolUserRoutes>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<PatrolUserRoutes> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();

                // TODO 可添加你的其他搜索过滤条件 默认已有创建时间过滤
                Path<Date> createTimeField = root.get("createTime");
               
                //创建时间
                if(StrUtil.isNotBlank(searchVo.getStartDate())&&StrUtil.isNotBlank(searchVo.getEndDate())){
                    Date start = DateUtil.parse(searchVo.getStartDate());
                    Date end = DateUtil.parse(searchVo.getEndDate());
                    list.add(cb.between(createTimeField, start, DateUtil.endOfDay(end)));
                }

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        });
    }

}