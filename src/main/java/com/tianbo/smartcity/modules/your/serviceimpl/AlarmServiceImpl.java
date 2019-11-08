package com.tianbo.smartcity.modules.your.serviceimpl;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.dao.AlarmDao;
import com.tianbo.smartcity.modules.your.entity.ServerAlarm;
import com.tianbo.smartcity.modules.your.service.AlarmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AlarmServiceImpl implements AlarmService{
    @Autowired
    private AlarmDao alarmDao;

    @Override
    public AlarmDao getRepository() { return alarmDao; }

    @Override
    public Page<ServerAlarm> findByCondition(ServerAlarm serverAlarm, SearchVo searchVo, Pageable pageable) {
        //这个findall方法不知道封装了啥 感觉就是获取个分页相关的信息？
        return alarmDao.findAll(new Specification<ServerAlarm>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<ServerAlarm> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                // TODO 可添加你的其他搜索过滤条件 默认已有创建时间过滤
                Path<Date> createTimeField=root.get("addTime");
                Path<String> deviceName=root.get("deviceName");
                Path<String> status=root.get("status");
                Path<String> alarmTypeName=root.get("alarmTypeName");
                Path<String> delFlag=root.get("delFlag");
                Path<String> deviceId=root.get("deviceId");
                List<Predicate> list = new ArrayList<Predicate>();

                //创建时间
                if(StrUtil.isNotBlank(searchVo.getStartDate())&&StrUtil.isNotBlank(searchVo.getEndDate())){
                    Date start = DateUtil.parse(searchVo.getStartDate());
                    Date end = DateUtil.parse(searchVo.getEndDate());
                    list.add(cb.between(createTimeField, start, DateUtil.endOfDay(end)));
                }
                //模糊搜素
                if (StrUtil.isNotBlank(serverAlarm.getAlarmTypeName())) {
                    list.add(cb.like(alarmTypeName, '%' + serverAlarm.getAlarmTypeName() + '%'));
                }
                if (StrUtil.isNotBlank(serverAlarm.getDeviceName())) {
                    list.add(cb.like(deviceName, '%' + serverAlarm.getDeviceName() + '%'));
                }
                if (StrUtil.isNotBlank(serverAlarm.getStatus())) {
                    list.add(cb.equal(status, serverAlarm.getStatus()));
                }
                if (StrUtil.isNotBlank(serverAlarm.getStatus())) {
                    list.add(cb.equal(status, serverAlarm.getStatus()));
                }
                if (StrUtil.isNotBlank(serverAlarm.getDeviceId())) {
                    list.add(cb.equal(deviceId, serverAlarm.getDeviceId()));
                }
                list.add(cb.equal(delFlag, serverAlarm.getDelFlag()));
                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        },pageable);
    }
}