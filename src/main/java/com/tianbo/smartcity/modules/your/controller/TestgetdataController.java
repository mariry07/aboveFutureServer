package com.tianbo.smartcity.modules.your.controller;


import com.tianbo.smartcity.base.SmartCityBaseController;
import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.common.utils.PageUtil;
import com.tianbo.smartcity.common.utils.ResultUtil;
import com.tianbo.smartcity.common.vo.PageVo;
import com.tianbo.smartcity.common.vo.Result;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.entity.Device;
import com.tianbo.smartcity.modules.your.service.DeviceService;
import com.tianbo.smartcity.modules.your.service.TestService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Transactional
@Slf4j
public class TestgetdataController extends SmartCityBaseController<Device, String> {


    @Autowired
    private TestService testService;
    @Override
    public TestService getService() {
        return testService;
    }


    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<Device>> getByCondition(@ModelAttribute Device device,
                                               @ModelAttribute SearchVo searchVo,
                                               @ModelAttribute PageVo pageVo){

        Page<Device> page = testService.findByCondition(device, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<Device>>().setData(page);
    }
}
