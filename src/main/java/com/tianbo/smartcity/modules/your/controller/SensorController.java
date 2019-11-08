package com.tianbo.smartcity.modules.your.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tianbo.smartcity.base.SmartCityBaseController;
import com.tianbo.smartcity.common.utils.PageUtil;
import com.tianbo.smartcity.common.utils.ResultUtil;
import com.tianbo.smartcity.common.vo.PageVo;
import com.tianbo.smartcity.common.vo.Result;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.entity.Sensor;
import com.tianbo.smartcity.modules.your.entity.ServerAlarm;
import com.tianbo.smartcity.modules.your.service.ISensorService;
import com.tianbo.smartcity.modules.your.service.SensorService;
import com.tianbo.smartcity.modules.your.vo.SensorVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Pangzy
 */
@Slf4j
@RestController
@Api(description = "传感器表管理接口")
@RequestMapping("/smartcity/sensor")
@Transactional
public class SensorController extends SmartCityBaseController<Sensor, String> {

    @Autowired
    private SensorService sensorService;

    @Autowired
    private ISensorService isensorService;

    @Override
    public SensorService getService() {
        return sensorService;
    }


    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<Sensor>> getByCondition(@ModelAttribute Sensor sensor,
                                                            @ModelAttribute SearchVo searchVo,
                                                            @ModelAttribute PageVo pageVo){

        Page<Sensor> page = sensorService.findByCondition(sensor, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<Sensor>>().setData(page);
    }

    @RequestMapping(value = "/getByConditionList", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Object> getByCondition2(@ModelAttribute SensorVo sensorVo,
                                               @ModelAttribute SearchVo searchVo,
                                               @ModelAttribute PageVo pageVo){

        pageVo.setStartRow((pageVo.getPageNumber() - 1) * pageVo.getPageSize());
        List<SensorVo> sensorList = isensorService.findPage(sensorVo, searchVo, pageVo);
        int sensorListCount = isensorService.findPageCount(sensorVo);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("sensorList",sensorList);
        map.put("sensorListCount",sensorListCount);
        return new ResultUtil<Object>().setData(map);
    }

    @RequestMapping(value = "/getByCondition3", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<IPage<Sensor>> save(@ModelAttribute Sensor sensor,
            @ModelAttribute PageVo page){

        return new ResultUtil<IPage<Sensor>>().setData(isensorService.findPage2(page,new QueryWrapper<Sensor>().lambda().like(Sensor::getDeviceId,"2")));
    }

    @RequestMapping(value = "/batchEnable/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过ids启用")
    public Result<Object> batchEnable(@PathVariable String[] ids){

        for(String id:ids){
            Sensor sensor = sensorService.get(id);
            sensor.setDisable(0);
            sensorService.update(sensor);
        }
        return new ResultUtil<Object>().setSuccessMsg("批量通过id启用传感器成功");
    }

    @RequestMapping(value = "/batchDisabling/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过ids禁用")
    public Result<Object> batchDisabling(@PathVariable String[] ids){

        for(String id:ids){
            Sensor sensor = sensorService.get(id);
            sensor.setDisable(1);
            sensorService.update(sensor);
        }
        return new ResultUtil<Object>().setSuccessMsg("批量通过id禁用传感器成功");
    }


}
