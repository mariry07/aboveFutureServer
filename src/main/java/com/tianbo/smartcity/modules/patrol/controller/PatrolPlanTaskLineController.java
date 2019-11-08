package com.tianbo.smartcity.modules.patrol.controller;


import com.tianbo.smartcity.base.SmartCityBaseController;
import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.modules.patrol.entity.PatrolPlanTaskLine;
import com.tianbo.smartcity.modules.patrol.service.PatrolPlanTaskLineService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangCH
 */
@Slf4j
@RestController
@Api(description ="巡更任务记录接口")
@RequestMapping("/smartcity/patrolPlanTaskLine")
public class PatrolPlanTaskLineController extends SmartCityBaseController<PatrolPlanTaskLine, String> {

    @Autowired
    private PatrolPlanTaskLineService patrolPlanTaskLineService;

    @Override
    public SmartCityBaseService<PatrolPlanTaskLine, String> getService() {
        return patrolPlanTaskLineService;
    }


}
