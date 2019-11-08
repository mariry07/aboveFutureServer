package com.tianbo.smartcity.modules.your.controller;

import com.tianbo.smartcity.base.SmartCityBaseController;
import com.tianbo.smartcity.common.utils.PageUtil;
import com.tianbo.smartcity.common.utils.ResultUtil;
import com.tianbo.smartcity.common.vo.PageVo;
import com.tianbo.smartcity.common.vo.Result;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.entity.ServerAlarm;
import com.tianbo.smartcity.modules.your.service.IServerAlarmService;
import com.tianbo.smartcity.modules.your.service.ServerAlarmService;
import com.tianbo.smartcity.modules.your.vo.ServerAlarmVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Pangzy
 */
@Slf4j
@RestController
@Api(description = "报警管理接口")
@RequestMapping("/smartcity/serverAlarm")
@Transactional
public class ServerAlarmController extends SmartCityBaseController<ServerAlarm, String> {

    @Autowired
    private ServerAlarmService serverAlarmService;

    @Autowired
    private IServerAlarmService iserverAlarmService;

    @Override
    public ServerAlarmService getService() {
        return serverAlarmService;
    }


    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<ServerAlarm>> getByCondition(@ModelAttribute ServerAlarm serverAlarm,
                                                            @ModelAttribute SearchVo searchVo,
                                                            @ModelAttribute PageVo pageVo){

        Page<ServerAlarm> page = serverAlarmService.findByCondition(serverAlarm, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<ServerAlarm>>().setData(page);
    }

    @RequestMapping(value = "/getDeviceAlarmCharts", method = RequestMethod.POST)
    @ApiOperation(value = "获取楼层抱紧信息汇总")
    public Result<Object> getDeviceAlarmCharts(@ModelAttribute ServerAlarm serverAlarm,@ModelAttribute SearchVo searchVo){
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
        List<ServerAlarmVo> u = iserverAlarmService.getDeviceAlarmCharts(serverAlarm,searchVo);
        List untreatedList = new ArrayList();
        List processedList = new ArrayList();
        List<String> serverAlarmList = new ArrayList();
        if(u.size() > 0){
            for (ServerAlarmVo serverAlarmVo:u) {
                untreatedList.add(serverAlarmVo.getUntreated());
                processedList.add(serverAlarmVo.getProcessed());
                serverAlarmList.add(serverAlarmVo.getFloorName());
            }
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("untreatedList",untreatedList);
        map.put("processedList",processedList);
        map.put("serverAlarmList",serverAlarmList);
        return new ResultUtil<Object>().setData(map);
    }


    @RequestMapping(value = "/getAlarmTypeCharts", method = RequestMethod.POST)
    @ApiOperation(value = "获取报警类型")
    public Result<Object> getAlarmTypeCharts(@ModelAttribute ServerAlarm serverAlarm,@ModelAttribute SearchVo searchVo){
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
        List<ServerAlarmVo> u = iserverAlarmService.getAlarmTypeCharts(serverAlarm,searchVo);
        List countList = new ArrayList();
        List<String> alarmTypeList = new ArrayList();
        if(u.size() > 0){
            for (ServerAlarmVo serverAlarmVo:u) {
                countList.add(serverAlarmVo.getCount());
                alarmTypeList.add(serverAlarmVo.getAlarmName());
            }
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("countList",countList);
        map.put("alarmTypeList",alarmTypeList);
        return new ResultUtil<Object>().setData(map);
    }

    @RequestMapping(value = "/getDealTypeCharts", method = RequestMethod.POST)
    @ApiOperation(value = "获取处理类型")
    public Result<Object> getDealTypeCharts(@ModelAttribute ServerAlarm serverAlarm,@ModelAttribute SearchVo searchVo){
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
        List<ServerAlarmVo> u = iserverAlarmService.getDealTypeCharts(serverAlarm,searchVo);
        List countList = new ArrayList();
        List<String> dealTypeList = new ArrayList();
        if(u.size() > 0){
            for (ServerAlarmVo serverAlarmVo:u) {
                countList.add(serverAlarmVo.getCount());
                //0测试 1维保检修 2误报 3 有效处理 4 其它
                if("0".equals(serverAlarmVo.getDealType())){
                    dealTypeList.add("测试");
                }
                if("1".equals(serverAlarmVo.getDealType())){
                    dealTypeList.add("维保检修");
                }
                if("2".equals(serverAlarmVo.getDealType())){
                    dealTypeList.add("误报");
                }
                if("3".equals(serverAlarmVo.getDealType())){
                    dealTypeList.add("有效处理");
                }
                if("4".equals(serverAlarmVo.getDealType())){
                    dealTypeList.add("其它");
                }
            }
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("countList",countList);
        map.put("dealTypeList",dealTypeList);
        return new ResultUtil<Object>().setData(map);
    }


    @RequestMapping(value = "/getAlamCharts", method = RequestMethod.POST)
    @ApiOperation(value = "获取处理类型")
    public Result<Object> getAlamCharts(@ModelAttribute ServerAlarm serverAlarm,@ModelAttribute SearchVo searchVo){
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
        List<String> serverAlarmChartsX = new ArrayList();
        List serverAlarmChartsY = new ArrayList();
        Date start = null;
        Date dEnd = null;
        try {
            start = formatter.parse(searchVo.getStartDate());
            dEnd = formatter.parse(searchVo.getEndDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        serverAlarmChartsX.add(searchVo.getStartDate());
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
            serverAlarmChartsX.add(formatter1.format(calBegin.getTime())
            );
        }
        List<ServerAlarmVo> u = iserverAlarmService.getAlamCharts(serverAlarm,searchVo);
        Map<String,Object> uMap = new HashMap<String,Object>();
        for (ServerAlarmVo serverAlarmVo:u) {
            uMap.put(serverAlarmVo.getCtime(),serverAlarmVo.getCount());
        }
        for (String x: serverAlarmChartsX) {
            if(uMap.get(x) != null){
                serverAlarmChartsY.add(uMap.get(x));
            }else{
                serverAlarmChartsY.add(0);
            }
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("max",Collections.max(serverAlarmChartsY));
        map.put("countList",serverAlarmChartsY);
        map.put("alamNameList",serverAlarmChartsX);
        return new ResultUtil<Object>().setData(map);
    }


    @RequestMapping(value = "/getAlarmSummaryCountAll", method = RequestMethod.POST)
    @ApiOperation(value = "获取报警类型")
    public Result<Object> getAlarmSummaryCountAll(@ModelAttribute ServerAlarm serverAlarm,@ModelAttribute SearchVo searchVo){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        if("".equals(searchVo.getStartDate()) && "".equals(searchVo.getEndDate())) {
            Date endDate = new Date();
            Calendar c = Calendar.getInstance();
            c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
            Date startDate = c.getTime();
            searchVo.setStartDate(formatter.format(startDate));
            searchVo.setEndDate(formatter.format(endDate));

        }
        ServerAlarmVo  alarmSummaryList = iserverAlarmService.getAlarmSummaryCountAll(searchVo);
        return new ResultUtil<Object>().setData(alarmSummaryList);
    }

    @RequestMapping(value = "/batchProcess/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过ids处理报警")
    public Result<Object> batchProcess(@PathVariable String[] ids){

        for(String id:ids){
            ServerAlarm serverAlarm = serverAlarmService.get(id);
            serverAlarm.setStatus("1");
            serverAlarm.setUpdateTime(new Date());
            serverAlarmService.update(serverAlarm);
        }
        return new ResultUtil<Object>().setSuccessMsg("批量通过id删除数据成功");
    }

    @RequestMapping(value = "/deleteByIds/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过ids删除")
    public Result<Object> deleteByIds(@PathVariable String[] ids){

        for(String id:ids){
            ServerAlarm serverAlarm = serverAlarmService.get(id);
            serverAlarm.setDelFlag(1);
            serverAlarm.setUpdateTime(new Date());
            serverAlarmService.update(serverAlarm);
        }
        return new ResultUtil<Object>().setSuccessMsg("批量通过id删除数据成功");
    }
}
