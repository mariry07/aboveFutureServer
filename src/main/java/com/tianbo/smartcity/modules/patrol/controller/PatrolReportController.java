package com.tianbo.smartcity.modules.patrol.controller;

import com.tianbo.smartcity.base.SmartCityBaseController;
import com.tianbo.smartcity.common.utils.ResultUtil;
import com.tianbo.smartcity.common.vo.Result;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.patrol.entity.PatrolDevice;
import com.tianbo.smartcity.modules.patrol.entity.PatrolSite;
import com.tianbo.smartcity.modules.patrol.service.IPatrolReportService;
import com.tianbo.smartcity.modules.patrol.service.PatrolDeviceService;
import com.tianbo.smartcity.modules.patrol.service.PatrolSiteService;
import com.tianbo.smartcity.modules.patrol.vo.PatrolReportVo;
import com.tianbo.smartcity.modules.your.entity.Device;
import com.tianbo.smartcity.modules.your.entity.ServerAlarm;
import com.tianbo.smartcity.modules.your.vo.DeviceVo;
import com.tianbo.smartcity.modules.your.vo.ServerAlarmVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fanshaohai
 */
@Slf4j
@RestController
@Api(description = "巡查报表接口")
@RequestMapping("/smartcity/patrolReport")
@Transactional
public class PatrolReportController extends SmartCityBaseController<PatrolDevice, String> {

	@Autowired
    private IPatrolReportService reportService;
	
	@Autowired
    private PatrolDeviceService patrolDeviceService;
	
    @Override
    public PatrolDeviceService getService() {
        return patrolDeviceService;
    }
    
    @RequestMapping(value = "/getPortalCountAll", method = RequestMethod.POST)
    @ApiOperation(value = "获取报表总数")
    public Result<Object> getPortalCountAll(@ModelAttribute PatrolDevice device,@ModelAttribute SearchVo searchVo){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if("".equals(searchVo.getStartDate()) && "".equals(searchVo.getEndDate())) {
            Date endDate = new Date();
            Calendar c = Calendar.getInstance();
            c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
            Date startDate = c.getTime();
            searchVo.setStartDate(formatter.format(startDate));
            searchVo.setEndDate(formatter.format(endDate));

        }
        PatrolReportVo  reportVo = reportService.getPortalCountAll(searchVo);
        reportVo.setUnSiteCount(reportVo.getSiteCount()-reportVo.getAlSiteCount());
        return new ResultUtil<Object>().setData(reportVo);
    }
    @RequestMapping(value = "/getSiteCharts", method = RequestMethod.POST)
    @ApiOperation(value = "获取楼层巡查点信息汇总")
    public Result<Object> getSiteCharts(@ModelAttribute PatrolDevice device,@ModelAttribute SearchVo searchVo){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        if("".equals(searchVo.getStartDate()) && "".equals(searchVo.getEndDate())){
            Date endDate = new Date();
            Calendar c = Calendar.getInstance();
            c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
            Date startDate =  c.getTime();
            searchVo.setStartDate(formatter.format(startDate));
            searchVo.setEndDate(formatter.format(endDate));

        }
        List<PatrolReportVo> u = reportService.getSiteCharts(searchVo);
        List alSiteList = new ArrayList();
        List unSiteList = new ArrayList();
        List intactDeviceList = new ArrayList();
        List<String> reportFloorList = new ArrayList();
        if(u.size() > 0){
            for (PatrolReportVo reportVo:u) {
            	alSiteList.add(reportVo.getAlSite());
            	unSiteList.add(reportVo.getUnSite());
            	intactDeviceList.add(reportVo.getIntactDevice());
            	reportFloorList.add(reportVo.getFloorName());
            }
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("alSiteList",alSiteList);
        map.put("unSiteList",unSiteList);
        map.put("intactDeviceList",intactDeviceList);
        map.put("reportFloorList",reportFloorList);
        return new ResultUtil<Object>().setData(map);
    }
    
    @RequestMapping(value = "/getPlanCharts", method = RequestMethod.POST)
    @ApiOperation(value = "获取巡查计划类型和数量")
    public Result<Object> getPlanCharts(@ModelAttribute PatrolDevice device,@ModelAttribute SearchVo searchVo){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        if("".equals(searchVo.getStartDate()) && "".equals(searchVo.getEndDate())){
            Date endDate = new Date();
            Calendar c = Calendar.getInstance();
            c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
            Date startDate =  c.getTime();
            searchVo.setStartDate(formatter.format(startDate));
            searchVo.setEndDate(formatter.format(endDate));

        }
        List<PatrolReportVo> u = reportService.getPlanCharts(searchVo);
        List countList = new ArrayList();
        List<String> planList = new ArrayList();
        if(u.size() > 0){
            for (PatrolReportVo reportVo:u) {
                countList.add(reportVo.getPlanCount());
                planList.add(reportVo.getTypeName());
            }
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("countList",countList);
        map.put("typeList",planList);
        return new ResultUtil<Object>().setData(map);
    }
    
    @RequestMapping(value = "/getDeviceStatusCountCharts", method = RequestMethod.POST)
    @ApiOperation(value = "获取设备统计柱状图")
    public Result<Object> getDeviceTypeCountCharts( @ModelAttribute SearchVo searchVo){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        if("".equals(searchVo.getStartDate()) && "".equals(searchVo.getEndDate())){
            Date endDate = new Date();
            Calendar c = Calendar.getInstance();
            c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
            Date startDate =  c.getTime();
            searchVo.setStartDate(formatter.format(startDate));
            searchVo.setEndDate(formatter.format(endDate));

        }
        List<PatrolReportVo> u = reportService.getDeviceStatusCountCharts(searchVo);
        List countList = new ArrayList();
        List<String> deviceStatusName = new ArrayList();
        if(u.size() > 0){
            for (PatrolReportVo reportVo:u) {
                countList.add(reportVo.getDeviceStatusCount());
                deviceStatusName.add(reportVo.getStatusName());
            }
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("deviceTypeCount",countList);
        map.put("deviceTypeName",deviceStatusName);
        map.put("max",Collections.max(countList));
        return new ResultUtil<Object>().setData(map);
    }


}
