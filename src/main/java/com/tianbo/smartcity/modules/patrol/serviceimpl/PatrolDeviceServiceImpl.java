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
import com.tianbo.smartcity.modules.patrol.dao.PatrolDeviceDao;
import com.tianbo.smartcity.modules.patrol.dao.PatrolDeviceTypeDao;
import com.tianbo.smartcity.modules.patrol.entity.PatrolDevice;
import com.tianbo.smartcity.modules.patrol.entity.PatrolDeviceType;
import com.tianbo.smartcity.modules.patrol.service.PatrolDeviceService;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 巡查设备接口实现
 * @author fanshaohai
 */
@Slf4j
@Service
@Transactional
public class PatrolDeviceServiceImpl implements PatrolDeviceService {

    @Autowired
    private PatrolDeviceDao patrolDeviceDao;
    
    @Autowired
    private PatrolDeviceTypeDao patrolDeviceTypeDao;

    @Override
    public PatrolDeviceDao getRepository() {
        return patrolDeviceDao;
    }

    @Override
    public Page<PatrolDevice> findByCondition(PatrolDevice patrolDevice, SearchVo searchVo, Pageable pageable) {

        return patrolDeviceDao.findAll(new Specification<PatrolDevice>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<PatrolDevice> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                // TODO 可添加你的其他搜索过滤条件 默认已有创建时间过滤
                Path<Date> createTimeField=root.get("createTime");
                Path<String> nameField = root.get("name");
                Path<Integer> statusField = root.get("status");
                Path<String> deviceTypeIdField = root.get("deviceTypeId");
                Path<String> siteIdField = root.get("siteId");
                
                List<Predicate> list = new ArrayList<Predicate>();

                //创建时间
                if(StrUtil.isNotBlank(searchVo.getStartDate())&&StrUtil.isNotBlank(searchVo.getEndDate())){
                    Date start = DateUtil.parse(searchVo.getStartDate());
                    Date end = DateUtil.parse(searchVo.getEndDate());
                    list.add(cb.between(createTimeField, start, DateUtil.endOfDay(end)));
                }
                // 名称模糊搜素
                if(StrUtil.isNotBlank(patrolDevice.getName())){
                    list.add(cb.like(nameField,'%'+patrolDevice.getName()+'%'));
                }
                //状态
                if(patrolDevice.getStatus() != null) {
                	list.add(cb.equal(statusField, patrolDevice.getStatus()));
                }
                // 名称模糊搜素
                if(StrUtil.isNotBlank(patrolDevice.getSiteId())){
                    list.add(cb.equal(siteIdField,patrolDevice.getSiteId()));
                }
                if(StrUtil.isNotBlank(patrolDevice.getDeviceTypeName())) {
                	//根据设备类型名称模糊查询设备类型列表
                	List<PatrolDeviceType> deviceTypeList = patrolDeviceTypeDao.findByNameLike("%"+patrolDevice.getDeviceTypeName()+"%");
                	List<String> deviceTypeIds = new ArrayList<String>();
                	if(deviceTypeList!=null&&deviceTypeList.size()>0) {
                		for(PatrolDeviceType t :deviceTypeList) {
                			deviceTypeIds.add(t.getId());
                		}
                	}
                	//设备类型搜索
                	if(deviceTypeIds!=null&&deviceTypeIds.size()>0) {
                		list.add(deviceTypeIdField.in(deviceTypeIds));
                	}else {
                		list.add(deviceTypeIdField.in(""));
                	}
                }

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }
    @Override
    public List<PatrolDevice> findAllByCondition(PatrolDevice patrolDevice) {

        return patrolDeviceDao.findAll(new Specification<PatrolDevice>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<PatrolDevice> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                // TODO 可添加你的其他搜索过滤条件 默认已有创建时间过滤
                Path<String> siteIdField = root.get("siteId");


                List<Predicate> list = new ArrayList<Predicate>();

                //巡查点id
                if(patrolDevice.getSiteId() != null) {
                	list.add(cb.equal(siteIdField, patrolDevice.getSiteId()));
                }

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        });
    }
}