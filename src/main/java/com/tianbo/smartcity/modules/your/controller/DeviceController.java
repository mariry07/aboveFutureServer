package com.tianbo.smartcity.modules.your.controller;

import com.qiniu.util.StringUtils;
import com.tianbo.smartcity.base.SmartCityBaseController;
import com.tianbo.smartcity.common.utils.PageUtil;
import com.tianbo.smartcity.common.utils.ResultUtil;
import com.tianbo.smartcity.common.vo.PageVo;
import com.tianbo.smartcity.common.vo.Result;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.entity.*;
import com.tianbo.smartcity.modules.your.service.*;
import com.tianbo.smartcity.modules.your.vo.DeviceFaultVo;
import com.tianbo.smartcity.modules.your.vo.DeviceVo;
import com.tianbo.smartcity.modules.your.vo.ServerAlarmVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.beetl.ext.fn.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Pangzy
 */
@Slf4j
@RestController
@Api(description = "设备表管理接口")
@RequestMapping("/smartcity/device")
@Transactional
public class DeviceController extends SmartCityBaseController<Device, String> {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private IDeviceService ideviceService;

    @Autowired
    private SensorService sensorService;

    @Autowired
    private ISensorTypeService isensorTypeService;

    @Autowired
    private FloorService floorService;

    @Override
    public DeviceService getService() {
        return deviceService;
    }


    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<Device>> getByCondition(@ModelAttribute Device device,
                                                            @ModelAttribute SearchVo searchVo,
                                                            @ModelAttribute PageVo pageVo){

        Page<Device> page = deviceService.findByCondition(device, searchVo, PageUtil.initPage(pageVo));
        for (Device d:page.getContent()
             ) {
            Floor floor = floorService.get(d.getFloorId());
            if(floor != null){
                d.setImg(floor.getImg());
            }

        }
        return new ResultUtil<Page<Device>>().setData(page);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加设备")
    public Result<Object> addDevice(@ModelAttribute Device device){
        Device device1 = ideviceService.selectByCode(device);
        if(device1 != null){
            return new ResultUtil<Object>().setErrorMsg("添加失败,主机编号已存在");
        }
        if(device.getOutdoor()==0){
            device.setPosition(device.getPosition() + " -- " + device.getTitle());
        }
        device.setAddTime(new Date());
        device.setUpdateTime(new Date());
        Device device2 = deviceService.save(device);
        SensorType sensorType = new SensorType();
        sensorType.setSystemType(device.getSystemType());
        List<SensorType> sensorTypeList = isensorTypeService.getListBySystemType(sensorType);
        for (SensorType sensorType1: sensorTypeList ) {
            Sensor sensor = new Sensor();
            sensor.setDeviceId(device2.getId());
            sensor.setLastTime(new Date());
            sensor.setSensorTypeId(sensorType1.getId());
            sensor.setStatus(1);
            sensor.setDisable(0);
            sensorService.save(sensor);
        }
        return new ResultUtil<Object>().setSuccessMsg("添加成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "修改主机")
    public Result<Object> editOwn(@ModelAttribute Device u){
        if(u.getOutdoor()==0){
            u.setPosition(u.getPosition() + " -- " + u.getTitle());
        }
        Device d = deviceService.findByCodeAndDelFlagAndIdNot(u.getCode(),u.getId());
        if(d !=null){
            return new ResultUtil<Object>().setErrorMsg("修改失败，主机编号已存在");
        }
        u.setUpdateTime(new Date());
        Device device = deviceService.update(u);
        if(device==null){
            return new ResultUtil<Object>().setErrorMsg("修改失败");
        }
        return new ResultUtil<Object>().setSuccessMsg("修改成功");
    }

    @RequestMapping(value = "/getDeviceTypeCountCharts", method = RequestMethod.POST)
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
        Device device = new Device();
        List<DeviceVo> deviceList = ideviceService.getDeviceTypeList(device,searchVo);
        Map<String,Object> deviceMap= new HashMap<String,Object>();

        for (DeviceVo DeviceVo: deviceList) {
            deviceMap.put(DeviceVo.getRunningState(),DeviceVo.getCount());
        }
        Map<String,Object> map = new HashMap<String,Object>();
        List deviceTypeCount = new ArrayList();
        List<String> deviceType = new ArrayList(Arrays.asList("0", "1", "2", "3", "4", "5"));
        List deviceTypeName = new ArrayList(Arrays.asList("未激活", "离线", "正常", "故障", "报警", "禁用"));
        //0未激活，1离线，2正常，3故障，4报警，5禁用
        for (String x: deviceType) {
            if (deviceMap.get(x) != null){
                deviceTypeCount.add(deviceMap.get(x));
            }else{
                deviceTypeCount.add(0);
            }
        }
        map.put("deviceTypeCount",deviceTypeCount);
        map.put("deviceTypeName",deviceTypeName);
        map.put("max",Collections.max(deviceTypeCount));
        return new ResultUtil<Object>().setData(map);
    }

    @RequestMapping(value = "/deleteByIds/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过ids删除")
    public Result<Object> deleteByIds(@PathVariable String[] ids){

        for(String id:ids){
            Device device = deviceService.get(id);
            device.setDelFlag(1);
            device.setUpdateTime(new Date());
            deviceService.update(device);
        }
        return new ResultUtil<Object>().setSuccessMsg("批量通过id删除数据成功");
    }

    @RequestMapping(value = "/batchEnable/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过ids启用")
    public Result<Object> batchEnable(@PathVariable String[] ids){

        for(String id:ids){
            Device device = deviceService.get(id);
            device.setRunningState("2");
            deviceService.update(device);
        }
        return new ResultUtil<Object>().setSuccessMsg("批量通过id启用设备成功");
    }

    @RequestMapping(value = "/batchDisabling/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过ids禁用")
    public Result<Object> batchDisabling(@PathVariable String[] ids){

        for(String id:ids){
            Device device = deviceService.get(id);
            device.setRunningState("5");
            deviceService.update(device);
        }
        return new ResultUtil<Object>().setSuccessMsg("批量通过id禁用设备成功");
    }

}
