package com.tianbo.smartcity.modules.patrol.controller;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tianbo.smartcity.base.SmartCityBaseController;
import com.tianbo.smartcity.common.enums.ThirdConstant;
import com.tianbo.smartcity.common.utils.PageUtil;
import com.tianbo.smartcity.common.utils.ResultUtil;
import com.tianbo.smartcity.common.vo.PageVo;
import com.tianbo.smartcity.common.vo.Result;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.base.entity.User;
import com.tianbo.smartcity.modules.patrol.entity.PatrolDevice;
import com.tianbo.smartcity.modules.patrol.entity.PatrolDeviceType;
import com.tianbo.smartcity.modules.patrol.entity.PatrolSite;
import com.tianbo.smartcity.modules.patrol.service.PatrolDeviceService;
import com.tianbo.smartcity.modules.patrol.service.PatrolDeviceTypeService;
import com.tianbo.smartcity.modules.patrol.service.PatrolSiteService;
import com.tianbo.smartcity.modules.your.entity.Floor;
import com.tianbo.smartcity.modules.your.service.FloorService;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author fanshaohai
 */
@Slf4j
@RestController
@Api(description = "巡查设备管理接口")
@RequestMapping("/smartcity/patrolDevice")
@Transactional
public class PatrolDeviceController extends SmartCityBaseController<PatrolDevice, String> {

    @Autowired
    private PatrolDeviceService patrolDeviceService;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private PatrolDeviceTypeService patrolDeviceTypeService;
    
    @Autowired
    private PatrolSiteService patrolSiteService;
    
    @Autowired
    private FloorService floorService;

    @Override
    public PatrolDeviceService getService() {
        return patrolDeviceService;
    }


    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<PatrolDevice>> getByCondition(@ModelAttribute PatrolDevice patrolDevice,
                                                            @ModelAttribute SearchVo searchVo,
                                                            @ModelAttribute PageVo pageVo){

        Page<PatrolDevice> page = patrolDeviceService.findByCondition(patrolDevice, searchVo, PageUtil.initPage(pageVo));
        for(PatrolDevice device :page.getContent()) {
        	if(StrUtil.isNotBlank(device.getDeviceTypeId())) {
        		//查询设备类型名称
        		try {
        			PatrolDeviceType deviceType = patrolDeviceTypeService.get(device.getDeviceTypeId());
        			device.setDeviceTypeName(deviceType.getName());
        		}catch(Exception e) {
        			device.setDeviceTypeName("");
        		}
        	}
        	if(StrUtil.isNotBlank(device.getSiteId())) {
        		//查询巡查点名称
        		try {
            		PatrolSite site = patrolSiteService.get(device.getSiteId());
            		device.setSiteName(site.getName());
        		}catch(Exception e) {
            		device.setSiteName("");
        		}
        	}
        	if(StrUtil.isNotBlank(device.getFloorId())) {
        		//查询楼层名称
        		try {
            		Floor floor = floorService.get(device.getFloorId());
            		device.setFloorName(floor.getName());
        		}catch(Exception e) {
            		device.setFloorName("");
        		}
        	}
        	// 清除持久上下文环境 避免后面语句导致持久化
        	entityManager.clear();
        }
        return new ResultUtil<Page<PatrolDevice>>().setData(page);
    }
    
}
