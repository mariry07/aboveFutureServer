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

import com.tianbo.smartcity.common.utils.QrCodeUtil;
import com.tianbo.smartcity.common.utils.SecurityUtil;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.base.entity.User;
import com.tianbo.smartcity.modules.patrol.dao.PatrolSiteDao;
import com.tianbo.smartcity.modules.patrol.entity.PatrolCheckItem;
import com.tianbo.smartcity.modules.patrol.entity.PatrolMark;
import com.tianbo.smartcity.modules.patrol.entity.PatrolSite;
import com.tianbo.smartcity.modules.patrol.service.PatrolSiteService;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 巡查点接口实现
 * @author fanshaohai
 */
@Slf4j
@Service
@Transactional
public class PatrolSiteServiceImpl implements PatrolSiteService {

    @Autowired
    private PatrolSiteDao patrolSiteDao;
    
    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public PatrolSiteDao getRepository() {
        return patrolSiteDao;
    }

    @Override
    public Page<PatrolSite> findByCondition(PatrolSite patrolSite, SearchVo searchVo, Pageable pageable) {

        return patrolSiteDao.findAll(new Specification<PatrolSite>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<PatrolSite> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                // TODO 可添加你的其他搜索过滤条件 默认已有创建时间过滤
                Path<Date> createTimeField=root.get("createTime");
                Path<String> nameField = root.get("name");
                Path<String> floorIdField = root.get("floorId");

                List<Predicate> list = new ArrayList<Predicate>();

                //创建时间
                if(StrUtil.isNotBlank(searchVo.getStartDate())&&StrUtil.isNotBlank(searchVo.getEndDate())){
                    Date start = DateUtil.parse(searchVo.getStartDate());
                    Date end = DateUtil.parse(searchVo.getEndDate());
                    list.add(cb.between(createTimeField, start, DateUtil.endOfDay(end)));
                }
                // 名称模糊搜素
                if(StrUtil.isNotBlank(patrolSite.getName())){
                    list.add(cb.like(nameField,'%'+patrolSite.getName()+'%'));
                }
             // 名称模糊搜素
                if(StrUtil.isNotBlank(patrolSite.getFloorId())){
                    list.add(cb.equal(floorIdField,patrolSite.getFloorId()));
                }

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }
    
    @Override
    public List<PatrolSite> findAllByCondition(PatrolSite patrolSite) {

        return patrolSiteDao.findAll(new Specification<PatrolSite>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<PatrolSite> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                // TODO 可添加你的其他搜索过滤条件 默认已有创建时间过滤
                Path<String> nameField = root.get("name");

                List<Predicate> list = new ArrayList<Predicate>();

              
                if(patrolSite.getName() != null) {
                	list.add(cb.equal(nameField, patrolSite.getName()));
                }

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        });
    }

	@Override
	public PatrolSite add(PatrolSite site) {
		User user = securityUtil.getCurrUser();
        //二维码生成base64
        String base64 = QrCodeUtil.createQrCode(site.getId());
        //更新二维码数据
        site.setPhotoId("data:image/jpg;base64," + base64);
        //新增数据
        PatrolSite result = save(site);
        return result;
	}
}