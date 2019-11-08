package com.tianbo.smartcity.modules.patrol.controller;

import com.tianbo.smartcity.base.SmartCityBaseController;
import com.tianbo.smartcity.common.utils.PageUtil;
import com.tianbo.smartcity.common.utils.ResultUtil;
import com.tianbo.smartcity.common.vo.PageVo;
import com.tianbo.smartcity.common.vo.Result;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.patrol.entity.PatrolPlanSite;
import com.tianbo.smartcity.modules.patrol.service.PatrolPlanSiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fanshaohai
 */
@Slf4j
@RestController
@Api(description = "巡查计划巡查点/巡更计划巡更点管理接口")
@RequestMapping("/smartcity/patrolPlanSite")
@Transactional
public class PatrolPlanSiteController extends SmartCityBaseController<PatrolPlanSite, String> {

    @Autowired
    private PatrolPlanSiteService patrolPlanSiteService;

    @Override
    public PatrolPlanSiteService getService() {
        return patrolPlanSiteService;
    }


    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<PatrolPlanSite>> getByCondition(@ModelAttribute PatrolPlanSite patrolPlanSite,
                                                            @ModelAttribute SearchVo searchVo,
                                                            @ModelAttribute PageVo pageVo){

        Page<PatrolPlanSite> page = patrolPlanSiteService.findByCondition(patrolPlanSite, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<PatrolPlanSite>>().setData(page);
    }

}
