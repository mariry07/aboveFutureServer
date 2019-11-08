package com.tianbo.smartcity.modules.your.serviceimpl;

import com.tianbo.smartcity.modules.your.dao.DeviceDao;
import com.tianbo.smartcity.modules.your.entity.Device;
import com.tianbo.smartcity.modules.your.entity.sensorLog;
import com.tianbo.smartcity.modules.your.service.DeviceService;
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
 * 设备表接口实现
 * @author Pangzy
 */
@Slf4j
@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceDao deviceDao;

    @Override
    public DeviceDao getRepository() {
        return deviceDao;
    }

    @Override
    public Page<Device> findByCondition(Device device, SearchVo searchVo, Pageable pageable) {

        return deviceDao.findAll(new Specification<Device>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Device> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                // TODO 可添加你的其他搜索过滤条件 默认已有创建时间过滤
                Path<Date> createTimeField=root.get("addTime");
                Path<String> name=root.get("name");
                Path<String> code=root.get("code");

                List<Predicate> list = new ArrayList<Predicate>();

                //创建时间
                if(StrUtil.isNotBlank(searchVo.getStartDate())&&StrUtil.isNotBlank(searchVo.getEndDate())){
                    Date start = DateUtil.parse(searchVo.getStartDate());
                    Date end = DateUtil.parse(searchVo.getEndDate());
                    list.add(cb.between(createTimeField, start, DateUtil.endOfDay(end)));
                }
                if (StrUtil.isNotBlank(device.getName())) {
                    list.add(cb.like(name, '%' + device.getName() + '%'));
                }
                if (StrUtil.isNotBlank(device.getCode())) {
                    list.add(cb.like(code, '%' + device.getCode() + '%'));
                }
                Path<String> delFlagField = root.get("delFlag");
                list.add(cb.equal(delFlagField, device.getDelFlag()));
                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }

    @Override
    public Device findByCodeAndDelFlagAndIdNot(String code, String id) {
        Integer delFlag =0;
        return deviceDao.findByCodeAndDelFlagAndIdNot(code,delFlag,id);
    }
}