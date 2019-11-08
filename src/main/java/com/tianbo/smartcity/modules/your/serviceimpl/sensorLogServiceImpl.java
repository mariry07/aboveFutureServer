package com.tianbo.smartcity.modules.your.serviceimpl;

import com.tianbo.smartcity.modules.your.dao.sensorLogDao;
import com.tianbo.smartcity.modules.your.entity.sensorLog;
import com.tianbo.smartcity.modules.your.service.sensorLogService;
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
 * 日志表接口实现
 * @author Pangzy
 */
@Slf4j
@Service
@Transactional
public class sensorLogServiceImpl implements sensorLogService {

    @Autowired
    private sensorLogDao sensorLogDao;

    @Override
    public sensorLogDao getRepository() {
        return sensorLogDao;
    }

    @Override
    public Page<sensorLog> findByCondition(sensorLog sensorLog, SearchVo searchVo, Pageable pageable) {

        return sensorLogDao.findAll(new Specification<sensorLog>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<sensorLog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                // TODO 可添加你的其他搜索过滤条件 默认已有创建时间过滤
                Path<Date> createTimeField=root.get("logTime");
                Path<String> deviceId=root.get("deviceId");

                List<Predicate> list = new ArrayList<Predicate>();

                //创建时间
                if(StrUtil.isNotBlank(searchVo.getStartDate())&&StrUtil.isNotBlank(searchVo.getEndDate())){
                    Date start = DateUtil.parse(searchVo.getStartDate());
                    Date end = DateUtil.parse(searchVo.getEndDate());
                    list.add(cb.between(createTimeField, start, DateUtil.endOfDay(end)));
                }
                if (StrUtil.isNotBlank(sensorLog.getDeviceId())) {
                    list.add(cb.equal(deviceId, sensorLog.getDeviceId()));
                }
                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }
}