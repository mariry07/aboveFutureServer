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
import com.tianbo.smartcity.modules.patrol.dao.PatrolCheckItemDao;
import com.tianbo.smartcity.modules.patrol.entity.PatrolCheckItem;
import com.tianbo.smartcity.modules.patrol.entity.PatrolDevice;
import com.tianbo.smartcity.modules.patrol.service.PatrolCheckItemService;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 设备类型巡查项接口实现
 * @author fanshaohai
 */
@Slf4j
@Service
@Transactional
public class PatrolCheckItemServiceImpl implements PatrolCheckItemService {

    @Autowired
    private PatrolCheckItemDao patrolCheckItemDao;

    @Override
    public PatrolCheckItemDao getRepository() {
        return patrolCheckItemDao;
    }

    @Override
    public Page<PatrolCheckItem> findByCondition(PatrolCheckItem patrolCheckItem, SearchVo searchVo, Pageable pageable) {

        return patrolCheckItemDao.findAll(new Specification<PatrolCheckItem>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<PatrolCheckItem> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                // TODO 可添加你的其他搜索过滤条件 默认已有创建时间过滤
                Path<Date> createTimeField=root.get("createTime");
                Path<String> nameField = root.get("name");
                Path<String> deviceTypeIdField = root.get("deviceTypeId");
                
                List<Predicate> list = new ArrayList<Predicate>();

                // 名称模糊搜素
                if(StrUtil.isNotBlank(patrolCheckItem.getName())){
                    list.add(cb.like(nameField,'%'+patrolCheckItem.getName()+'%'));
                }
                //选择的巡查类型节点id
                list.add(cb.equal(deviceTypeIdField,patrolCheckItem.getDeviceTypeId()));
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
    public List<PatrolCheckItem> findAllByCondition(PatrolCheckItem patrolCheckItem) {

        return patrolCheckItemDao.findAll(new Specification<PatrolCheckItem>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<PatrolCheckItem> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                // TODO 可添加你的其他搜索过滤条件 默认已有创建时间过滤
                Path<String> deviceTypeIdField = root.get("deviceTypeId");
                Path<String> nameField = root.get("name");

                List<Predicate> list = new ArrayList<Predicate>();

                //设备类型id
                if(patrolCheckItem.getDeviceTypeId() != null) {
                	list.add(cb.equal(deviceTypeIdField, patrolCheckItem.getDeviceTypeId()));
                }
                if(patrolCheckItem.getName() != null) {
                	list.add(cb.equal(nameField, patrolCheckItem.getName()));
                }

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        });
    }

	@Override
	public List<PatrolCheckItem> findByDeviceTypeId(String deviceTypeId) {
		return patrolCheckItemDao.findByDeviceTypeId(deviceTypeId);
	}

	@Override
	public List<PatrolCheckItem> findByName(String name) {
		return patrolCheckItemDao.findByName(name);
	}
}