package com.tianbo.smartcity.modules.patrol.serviceimpl;

import com.tianbo.smartcity.modules.patrol.dao.PatrolPlanTaskLineDao;
import com.tianbo.smartcity.modules.patrol.service.PatrolPlanTaskLineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 巡更任务记录接口实现
 * @author WangCH
 */
@Slf4j
@Service
@Transactional
public class PatrolPlanTaskLineServiceImpl implements PatrolPlanTaskLineService {

    @Autowired
    private PatrolPlanTaskLineDao patrolPlanTaskLineDao;

    @Override
    public PatrolPlanTaskLineDao getRepository() {
        return patrolPlanTaskLineDao;
    }


}