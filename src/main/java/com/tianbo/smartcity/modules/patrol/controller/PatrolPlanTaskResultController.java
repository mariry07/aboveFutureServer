package com.tianbo.smartcity.modules.patrol.controller;

import com.tianbo.smartcity.base.SmartCityBaseController;
import com.tianbo.smartcity.common.utils.PageUtil;
import com.tianbo.smartcity.common.utils.ResultUtil;
import com.tianbo.smartcity.common.vo.PageVo;
import com.tianbo.smartcity.common.vo.Result;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.patrol.entity.PatrolPlanTaskResult;
import com.tianbo.smartcity.modules.patrol.service.PatrolPlanTaskResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fanshaohai
 */
@Slf4j
@RestController
@Api(description = "巡查结果管理接口")
@RequestMapping("/smartcity/patrolPlanTaskResult")
@Transactional
public class PatrolPlanTaskResultController extends SmartCityBaseController<PatrolPlanTaskResult, String> {

    @Autowired
    private PatrolPlanTaskResultService patrolPlanTaskResultService;

    @Override
    public PatrolPlanTaskResultService getService() {
        return patrolPlanTaskResultService;
    }


    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<PatrolPlanTaskResult>> getByCondition(@ModelAttribute PatrolPlanTaskResult patrolPlanTaskResult,
                                                            @ModelAttribute SearchVo searchVo,
                                                            @ModelAttribute PageVo pageVo){

        Page<PatrolPlanTaskResult> page = patrolPlanTaskResultService.findByCondition(patrolPlanTaskResult, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<PatrolPlanTaskResult>>().setData(page);
    }

}
