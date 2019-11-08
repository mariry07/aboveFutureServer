package com.tianbo.smartcity.modules.patrol.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.patrol.dao.PatrolDeviceTypeDao;
import com.tianbo.smartcity.modules.patrol.entity.PatrolDeviceType;
import com.tianbo.smartcity.modules.patrol.service.PatrolDeviceTypeService;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 设备类型接口实现
 * @author fanshaohai
 */
@Slf4j
@Service
@Transactional
public class PatrolDeviceTypeServiceImpl implements PatrolDeviceTypeService {

    @Autowired
    private PatrolDeviceTypeDao patrolDeviceTypeDao;

    @Override
    public PatrolDeviceTypeDao getRepository() {
        return patrolDeviceTypeDao;
    }
    
    @Override
    public List<PatrolDeviceType> findByParentId(String parentId) {

        return patrolDeviceTypeDao.findByParentId(parentId);
    }
    
    @Override
    public List<PatrolDeviceType> findByTitleLike(String title) {

        return patrolDeviceTypeDao.findByNameLike(title);
    }

    @Override
    public Page<PatrolDeviceType> findByCondition(PatrolDeviceType patrolDeviceType, SearchVo searchVo, Pageable pageable) {

        return patrolDeviceTypeDao.findAll(new Specification<PatrolDeviceType>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<PatrolDeviceType> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                // TODO 可添加你的其他搜索过滤条件 默认已有创建时间过滤
                Path<Date> createTimeField=root.get("createTime");

                List<Predicate> list = new ArrayList<Predicate>();

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
        }, pageable);
    }
    @Override
    public List<PatrolDeviceType> findAllByCondition(PatrolDeviceType condition) {

        return patrolDeviceTypeDao.findAll(new Specification<PatrolDeviceType>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<PatrolDeviceType> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                Path<String> nameField = root.get("name");
                Path<Integer> levelField = root.get("level");
                Path<String> parentIdField = root.get("parentId");
                
                List<Predicate> list = new ArrayList<Predicate>();
            	//名称
                if(condition.getName() != null) {
                	list.add(cb.equal(nameField, condition.getName()));
                }
                //级别
                if(condition.getLevel() != null) {
                	list.add(cb.equal(levelField, condition.getLevel()));
                }
                //父id
                if(condition.getParentId()!=null &&!"".equals(condition.getParentId())) {
                	list.add(cb.equal(parentIdField, condition.getParentId()));
                }
                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        });
    }
}