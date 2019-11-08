package com.tianbo.smartcity.modules.activiti.serviceimpl.business;

import com.tianbo.smartcity.modules.activiti.dao.business.LeaveDao;
import com.tianbo.smartcity.modules.activiti.service.business.LeaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 请假接口实现
 *
 * @author Exrick
 */
@Slf4j
@Service
@Transactional
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveDao leaveDao;

    @Override
    public LeaveDao getRepository() {
        return leaveDao;
    }
}