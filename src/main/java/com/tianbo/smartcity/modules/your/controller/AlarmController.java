package com.tianbo.smartcity.modules.your.controller;


import com.tianbo.smartcity.base.SmartCityBaseController;
import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.common.utils.PageUtil;
import com.tianbo.smartcity.common.utils.ResultUtil;
import com.tianbo.smartcity.common.vo.PageVo;
import com.tianbo.smartcity.common.vo.Result;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.entity.ServerAlarm;
import com.tianbo.smartcity.modules.your.service.AlarmService;
import com.tianbo.smartcity.modules.your.service.IServerAlarmService;
import com.tianbo.smartcity.modules.your.vo.ServerAlarmVo;
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

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
@Api(description = "综合应用报警中心接口")
@RequestMapping("/smartcity/Alarm")
@Transactional

public class AlarmController extends SmartCityBaseController<ServerAlarm,String>{
    @Autowired
    private AlarmService alarmService;

    @Autowired
    private IServerAlarmService iserverAlarmService;

    @Override
    public SmartCityBaseService<ServerAlarm, String> getService() { return alarmService; }

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<ServerAlarm>> getByCondition(@ModelAttribute ServerAlarm serverAlarm,
                                                    @ModelAttribute SearchVo searchVo,
                                                    @ModelAttribute PageVo pageVo){

        Page<ServerAlarm> page = alarmService.findByCondition(serverAlarm, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<ServerAlarm>>().setData(page);
    }
}

