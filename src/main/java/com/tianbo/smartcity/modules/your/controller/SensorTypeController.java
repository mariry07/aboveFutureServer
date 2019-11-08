package com.tianbo.smartcity.modules.your.controller;

import com.tianbo.smartcity.base.SmartCityBaseController;
import com.tianbo.smartcity.common.constant.CommonConstant;
import com.tianbo.smartcity.common.utils.PageUtil;
import com.tianbo.smartcity.common.utils.ResultUtil;
import com.tianbo.smartcity.common.vo.PageVo;
import com.tianbo.smartcity.common.vo.Result;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.entity.DeviceFault;
import com.tianbo.smartcity.modules.your.entity.SensorType;
import com.tianbo.smartcity.modules.your.service.ISensorTypeService;
import com.tianbo.smartcity.modules.your.service.SensorTypeService;
import com.tianbo.smartcity.modules.your.vo.DeviceFaultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * @author Pangzy
 */
@Slf4j
@RestController
@Api(description = "传感器故障管理接口")
@RequestMapping("/smartcity/sensorType")
@Transactional
public class SensorTypeController extends SmartCityBaseController<SensorType, String> {

    @Autowired
    private SensorTypeService sensorTypeService;

    @Autowired
    private ISensorTypeService isensorTypeService;

    @Override
    public SensorTypeService getService() {
        return sensorTypeService;
    }


    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<SensorType>> getByCondition(@ModelAttribute SensorType sensorType,
                                                            @ModelAttribute SearchVo searchVo,
                                                            @ModelAttribute PageVo pageVo){

        Page<SensorType> page = sensorTypeService.findByCondition(sensorType, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<SensorType>>().setData(page);
    }

//    @RequestMapping(value = "/getDeviceFaultAll", method = RequestMethod.GET)
//    @ApiOperation(value = "获取故障设备汇总")
//    public Result<List<SensorType>> getDeviceFaultAll(@ModelAttribute SensorType sensorType) throws UnsupportedEncodingException {
//
//       // List<SensorType> list = sensorTypeService.getDeviceFaultAll(sensorType);
//        return new ResultUtil<List<SensorType>>().setData(list);
//    }

}
