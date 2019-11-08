package com.tianbo.smartcity.modules.your.serviceimpl;

import com.tianbo.smartcity.modules.your.dao.DeviceFaultDao;
import com.tianbo.smartcity.modules.your.entity.DeviceFault;
import com.tianbo.smartcity.modules.your.service.DeviceFaultService;
import com.tianbo.smartcity.common.vo.SearchVo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;
import java.lang.reflect.Field;

/**
 * 主机故障接口实现
 * @author Pangzy
 */
@Slf4j
@Service
@Transactional
public class DeviceFaultServiceImpl implements DeviceFaultService {

    @Autowired
    private DeviceFaultDao deviceFaultDao;

    @Override
    public DeviceFaultDao getRepository() {
        return deviceFaultDao;
    }

    @Override
    public Page<DeviceFault> findByCondition(DeviceFault deviceFault, SearchVo searchVo, Pageable pageable) {

        return deviceFaultDao.findAll(new Specification<DeviceFault>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<DeviceFault> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                // TODO 可添加你的其他搜索过滤条件 默认已有创建时间过滤
                Path<Date> createTimeField=root.get("addTime");
                Path<String> deviceName = root.get("deviceName");
                Path<String> deviceId = root.get("deviceId");
                Path<String> faultTypeName = root.get("faultTypeName");
                Path<String> status = root.get("status");
                List<Predicate> list = new ArrayList<Predicate>();
                //创建时间
                if(StrUtil.isNotBlank(searchVo.getStartDate())&&StrUtil.isNotBlank(searchVo.getEndDate())){
                    Date start = DateUtil.parse(searchVo.getStartDate());
                    Date end = DateUtil.parse(searchVo.getEndDate());
                    list.add(cb.between(createTimeField, start, DateUtil.endOfDay(end)));
                }

                //模糊搜素
                if (StrUtil.isNotBlank(deviceFault.getDeviceName())) {
                    list.add(cb.like(deviceName, '%' + deviceFault.getDeviceName() + '%'));
                }
                if (StrUtil.isNotBlank(deviceFault.getDeviceId())) {
                    list.add(cb.equal(deviceId, deviceFault.getDeviceId()));
                }
                if (StrUtil.isNotBlank(deviceFault.getFaultTypeName())) {
                    list.add(cb.like(faultTypeName, '%' + deviceFault.getFaultTypeName() + '%'));
                }
                if (StrUtil.isNotBlank(deviceFault.getStatus())) {
                    list.add(cb.equal(status, deviceFault.getStatus()));
                }
                //删除标记
                Path<String> delFlagField = root.get("delFlag");
                list.add(cb.equal(delFlagField, deviceFault.getDelFlag()));
                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<DeviceFault> getDeviceFaultList(DeviceFault deviceFault, SearchVo searchVo, Pageable initPage) {
        return deviceFaultDao.getDeviceFaultList(deviceFault, searchVo,initPage);
    }

}