package com.tianbo.smartcity.modules.your.serviceimpl;

import com.tianbo.smartcity.modules.patrol.entity.PatrolDeviceType;
import com.tianbo.smartcity.modules.your.dao.FloorDao;
import com.tianbo.smartcity.modules.your.entity.Floor;
import com.tianbo.smartcity.modules.your.service.FloorService;
import com.tianbo.smartcity.common.vo.SearchVo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
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
import java.lang.reflect.Field;

/**
 * 主机故障接口实现
 * @author Pangzy
 */
@Slf4j
@Service
@Transactional
public class FloorServiceImpl implements FloorService {

    @Autowired
    private FloorDao floorDao;

    @Override
    public FloorDao getRepository() {
        return floorDao;
    }

    @Override
    public Page<Floor> findByCondition(Floor floor, SearchVo searchVo, Pageable pageable) {

        return floorDao.findAll(new Specification<Floor>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Floor> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

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
    public List<Floor> findListByName(String name) {
        return floorDao.findListByName(name);
    }
    
    @Override
    public List<Floor> findByParentId(String parentId) {

        return floorDao.findByParentId(parentId);
    }

	@Override
	public List<Floor> findByParentIdAndDelFlag(String parentId, Integer delFlag) {
        return floorDao.findByParentIdAndDelFlag(parentId,delFlag);
	}
}