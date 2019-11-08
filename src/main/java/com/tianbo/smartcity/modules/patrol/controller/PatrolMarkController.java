package com.tianbo.smartcity.modules.patrol.controller;


import com.tianbo.smartcity.base.SmartCityBaseController;
import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.common.enums.PatrolPlanType;
import com.tianbo.smartcity.common.utils.PageUtil;
import com.tianbo.smartcity.common.utils.ResultUtil;
import com.tianbo.smartcity.common.vo.PageVo;
import com.tianbo.smartcity.common.vo.Result;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.patrol.entity.PatrolMark;
import com.tianbo.smartcity.modules.patrol.entity.PatrolPlan;
import com.tianbo.smartcity.modules.patrol.service.PatrolMarkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author WangCH
 */
@Slf4j
@RestController
@Api(description ="巡更点接口")
@RequestMapping("/smartcity/patrolMark")
public class PatrolMarkController extends SmartCityBaseController<PatrolMark, String> {

    @Autowired
    private PatrolMarkService patrolMarkService;

    @Override
    public SmartCityBaseService<PatrolMark, String> getService() {
        return patrolMarkService;
    }

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<PatrolMark>> getByCondition(@ModelAttribute PatrolMark patrolMark,
                                                    @ModelAttribute SearchVo searchVo,
                                                    @ModelAttribute PageVo pageVo){

        Page<PatrolMark> page = patrolMarkService.findByCondition(patrolMark, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<PatrolMark>>().setData(page);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "新增巡更点")
    @Override
    public Result<PatrolMark> save(@ModelAttribute PatrolMark patrolMark) {

        PatrolMark result = patrolMarkService.add(patrolMark);
        return new ResultUtil<PatrolMark>().setData(result);
    }

    @RequestMapping(value = "/getCountByName", method = RequestMethod.GET)
    @ApiOperation(value = "根据名称获取巡更点数目")
    public Result<Integer> getCountByName(@ModelAttribute PatrolMark patrolMark) {
        Integer isExist = patrolMarkService.countByNameAndIdNot(patrolMark.getName(),patrolMark.getId());
        return new ResultUtil<Integer>().setData(isExist);
    }


}
