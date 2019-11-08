package com.tianbo.smartcity.modules.your.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tianbo.smartcity.base.SmartCityBaseController;
import com.tianbo.smartcity.common.utils.PageUtil;
import com.tianbo.smartcity.common.utils.ResultUtil;
import com.tianbo.smartcity.common.vo.PageVo;
import com.tianbo.smartcity.common.vo.Result;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.entity.DeviceFault;
import com.tianbo.smartcity.modules.your.entity.ServerAlarm;
import com.tianbo.smartcity.modules.your.service.DeviceFaultService;
import com.tianbo.smartcity.modules.your.service.IDeviceFaultService;
import com.tianbo.smartcity.modules.your.service.IServerAlarmService;
import com.tianbo.smartcity.modules.your.vo.DeviceFaultVo;
import com.tianbo.smartcity.modules.your.vo.ServerAlarmVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Pangzy
 */
@Slf4j
@RestController
@Api(description = "主机故障管理接口")
@RequestMapping("/smartcity/deviceFault")
@Transactional
public class DeviceFaultController extends SmartCityBaseController<DeviceFault, String> {

    @Autowired
    private DeviceFaultService deviceFaultService;

    @Autowired
    private IDeviceFaultService ideviceFaultService;

    @Autowired
    private IServerAlarmService iserverAlarmService;

    @Override
    public DeviceFaultService getService() {
        return deviceFaultService;
    }


    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<DeviceFault>> getByCondition(@ModelAttribute DeviceFault deviceFault,
                                                    @ModelAttribute SearchVo searchVo,
                                                    @ModelAttribute PageVo pageVo){

        Page<DeviceFault> page = deviceFaultService.findByCondition(deviceFault, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<DeviceFault>>().setData(page);
    }

    @RequestMapping(value = "/getDeviceFaultList", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<DeviceFault>> getDeviceFaultList(@ModelAttribute DeviceFault deviceFault,
                                                        @ModelAttribute SearchVo searchVo,
                                                        @ModelAttribute PageVo pageVo){

        Page<DeviceFault> page = deviceFaultService.getDeviceFaultList(deviceFault, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<DeviceFault>>().setData(page);
    }


    @RequestMapping(value = "/getDeviceFaultAll", method = RequestMethod.GET)
    @ApiOperation(value = "获取故障设备汇总")
    public Result<Object> getDeviceFaultAll(@ModelAttribute DeviceFault deviceFault){

        DeviceFaultVo u = ideviceFaultService.getDeviceFaultAll(deviceFault);
        return new ResultUtil<Object>().setData(u);
    }


    @RequestMapping(value = "/getDeviceFaultCharts", method = RequestMethod.POST)
    @ApiOperation(value = "获取故障设备折线图信息")
    public Result<Object> getDeviceFaultCharts(@ModelAttribute DeviceFault deviceFault,
                                               @ModelAttribute SearchVo searchVo){
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
        List<String> deviceFaultChartsX = new ArrayList();
        List deviceFaultChartsY = new ArrayList();
        Date start = null;
        Date dEnd = null;
        try {
            start = formatter.parse(searchVo.getStartDate());
            dEnd = formatter.parse(searchVo.getEndDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        deviceFaultChartsX.add(searchVo.getStartDate());
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(start);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            deviceFaultChartsX.add(formatter1.format(calBegin.getTime())
            );
        }

        Map<String,Object> map= new HashMap<String,Object>();
        Map<String,Object> deviceFaultChartsMap= new HashMap<String,Object>();
        List<DeviceFaultVo> deviceFaultCharts = ideviceFaultService.getDeviceFaultCharts(deviceFault,searchVo);
        for (DeviceFaultVo deviceFaultVo:deviceFaultCharts) {
            deviceFaultChartsMap.put(deviceFaultVo.getCtime(),deviceFaultVo.getCount());
        }
        for (String x: deviceFaultChartsX) {
            if(deviceFaultChartsMap.get(x) != null){
                deviceFaultChartsY.add(deviceFaultChartsMap.get(x));
            }else{
                deviceFaultChartsY.add(0);
            }
        }
        map.put("max",Collections.max(deviceFaultChartsY));
        map.put("deviceFaultChartsX",deviceFaultChartsX);
        map.put("deviceFaultChartsY",deviceFaultChartsY);

        return new ResultUtil<Object>().setData(map);
    }
    @RequestMapping(value = "/getDevicePoliceAndFaultCharts", method = RequestMethod.POST)
    @ApiOperation(value = "获取故障设备折线图信息")
    public Result<Object> getDevicePoliceAndFaultCharts(@ModelAttribute DeviceFault deviceFault,
                                                        @ModelAttribute SearchVo searchVo){
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
        List<String> deviceFaultChartsX = new ArrayList();
        List deviceFaultChartsY = new ArrayList();
        List serverAlarmChartsY = new ArrayList();
        Date start = null;
        Date dEnd = null;
        try {
            start = formatter.parse(searchVo.getStartDate());
            dEnd = formatter.parse(searchVo.getEndDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        deviceFaultChartsX.add(searchVo.getStartDate());
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(start);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            deviceFaultChartsX.add(formatter1.format(calBegin.getTime())
            );
        }

        Map<String,Object> map= new HashMap<String,Object>();
        Map<String,Object> deviceFaultChartsMap= new HashMap<String,Object>();
        List<DeviceFaultVo> deviceFaultCharts = ideviceFaultService.getDeviceFaultCharts(deviceFault,searchVo);
        for (DeviceFaultVo deviceFaultVo:deviceFaultCharts) {
            deviceFaultChartsMap.put(deviceFaultVo.getCtime(),deviceFaultVo.getCount());
        }
        for (String x: deviceFaultChartsX) {
            if(deviceFaultChartsMap.get(x) != null){
                deviceFaultChartsY.add(deviceFaultChartsMap.get(x));
            }else{
                deviceFaultChartsY.add(0);
            }
        }

        Map<String,Object> serverAlarmMap= new HashMap<String,Object>();
        List<ServerAlarmVo> serverAlarmCharts = iserverAlarmService.getServerAlarmCharts(searchVo);
        for (ServerAlarmVo ServerAlarmVo:serverAlarmCharts) {
            serverAlarmMap.put(ServerAlarmVo.getCtime(),ServerAlarmVo.getCount());
        }
        for (String x: deviceFaultChartsX) {
            if(serverAlarmMap.get(x) != null){
                serverAlarmChartsY.add(serverAlarmMap.get(x));
            }else{
                serverAlarmChartsY.add(0);
            }
        }
        if(Integer.parseInt(Collections.max(deviceFaultChartsY).toString()) > Integer.parseInt(Collections.max(serverAlarmChartsY).toString())){
            map.put("max",Collections.max(deviceFaultChartsY));
        }else {
            map.put("max",Collections.max(serverAlarmChartsY));
        }
        map.put("deviceFaultChartsX",deviceFaultChartsX);
        map.put("deviceFaultChartsY",deviceFaultChartsY);
        map.put("serverAlarmChartsY",serverAlarmChartsY);
        return new ResultUtil<Object>().setData(map);
    }

    @RequestMapping(value = "/getFloorFaultCharts", method = RequestMethod.POST)
    @ApiOperation(value = "获取故障设备折线图信息")
    public Result<Object> getFloorFaultCharts(){
        Map<String,Object> map= new HashMap<String,Object>();
        return new ResultUtil<Object>().setData(map);
    }

    @RequestMapping(value = "/deleteByIds/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过ids删除")
    public Result<Object> deleteByIds(@PathVariable String[] ids){

        for(String id:ids){
            DeviceFault deviceFault = deviceFaultService.get(id);
            deviceFault.setDelFlag(1);
            deviceFault.setUpdateTime(new Date());
            deviceFaultService.update(deviceFault);
        }
        return new ResultUtil<Object>().setSuccessMsg("批量通过id删除数据成功");
    }

    @RequestMapping(value = "/batchProcess/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过ids处理故障")
    public Result<Object> batchProcess(@PathVariable String[] ids){

        for(String id:ids){
            DeviceFault deviceFault = deviceFaultService.get(id);
            deviceFault.setStatus("1");
            deviceFault.setUpdateTime(new Date());
            deviceFaultService.update(deviceFault);
        }
        return new ResultUtil<Object>().setSuccessMsg("批量通过id删除数据成功");
    }


}
