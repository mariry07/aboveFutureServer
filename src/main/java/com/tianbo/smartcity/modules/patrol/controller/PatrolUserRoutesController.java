package com.tianbo.smartcity.modules.patrol.controller;


import com.tianbo.smartcity.base.SmartCityBaseController;
import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.common.utils.ResultUtil;
import com.tianbo.smartcity.common.vo.Result;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.patrol.entity.PatrolUserRoutes;
import com.tianbo.smartcity.modules.patrol.service.PatrolUserRoutesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author WangCH
 */
@Slf4j
@RestController
@Api(description ="巡更用户轨迹记录接口")
@RequestMapping("/smartcity/patrolUserRoutes")
public class PatrolUserRoutesController extends SmartCityBaseController<PatrolUserRoutes, String> {

    @Autowired
    private PatrolUserRoutesService patrolUserRoutesService;

    @Override
    public SmartCityBaseService<PatrolUserRoutes, String> getService() {
        return patrolUserRoutesService;
    }

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "条件查询")
    public Result<List<PatrolUserRoutes>> getByCondition(@ModelAttribute PatrolUserRoutes patrolUserRoutes,
                                                   @ModelAttribute SearchVo searchVo){

        List<PatrolUserRoutes> list = patrolUserRoutesService.findByCondition(patrolUserRoutes, searchVo);
        return new ResultUtil<List<PatrolUserRoutes>>().setData(list);
    }





}
