package com.tianbo.smartcity.modules.your.serviceimpl;

import com.tianbo.smartcity.modules.your.dao.SensorDao;
import com.tianbo.smartcity.modules.your.entity.Sensor;
import com.tianbo.smartcity.modules.your.service.SensorService;
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
 * 传感器表接口实现
 * @author Pangzy
 */
@Slf4j
@Service
@Transactional
public class SensorServiceImpl implements SensorService {

    @Autowired
    private SensorDao sensorDao;

    @Override
    public SensorDao getRepository() {
        return sensorDao;
    }

    @Override
    public Page<Sensor> findByCondition(Sensor sensor, SearchVo searchVo, Pageable pageable) {

        return sensorDao.findAll(new Specification<Sensor>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Sensor> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                // TODO 可添加你的其他搜索过滤条件 默认已有创建时间过滤
                Path<Date> createTimeField=root.get("lastTime");

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
}