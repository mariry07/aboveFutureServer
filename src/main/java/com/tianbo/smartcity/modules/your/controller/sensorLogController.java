package com.tianbo.smartcity.modules.your.controller;

import com.tianbo.smartcity.base.SmartCityBaseController;
import com.tianbo.smartcity.common.utils.PageUtil;
import com.tianbo.smartcity.common.utils.ResultUtil;
import com.tianbo.smartcity.common.vo.PageVo;
import com.tianbo.smartcity.common.vo.Result;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.entity.Floor;
import com.tianbo.smartcity.modules.your.entity.sensorLog;
import com.tianbo.smartcity.modules.your.service.FloorService;
import com.tianbo.smartcity.modules.your.service.sensorLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Pangzy
 */
@Slf4j
@RestController
@Api(description = "日志表管理接口")
@RequestMapping("/smartcity/sensorLog")
@Transactional
public class sensorLogController extends SmartCityBaseController<sensorLog, String> {

    @Autowired
    private com.tianbo.smartcity.modules.your.service.sensorLogService sensorLogService;

    @Override
    public sensorLogService getService() {
        return sensorLogService;
    }


    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<sensorLog>> getByCondition(@ModelAttribute sensorLog sensorLog,
                                                            @ModelAttribute SearchVo searchVo,
                                                            @ModelAttribute PageVo pageVo){

        Page<sensorLog> page = sensorLogService.findByCondition(sensorLog, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<sensorLog>>().setData(page);
    }

}
