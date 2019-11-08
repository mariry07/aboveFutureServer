package com.tianbo.smartcity.modules.patrol.controller;

import com.tianbo.smartcity.base.SmartCityBaseController;
import com.tianbo.smartcity.common.utils.PageUtil;
import com.tianbo.smartcity.common.utils.ResultUtil;
import com.tianbo.smartcity.common.vo.PageVo;
import com.tianbo.smartcity.common.vo.Result;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.patrol.entity.PatrolDevice;
import com.tianbo.smartcity.modules.patrol.entity.PatrolDeviceRepair;
import com.tianbo.smartcity.modules.patrol.entity.PatrolDeviceType;
import com.tianbo.smartcity.modules.patrol.entity.PatrolSite;
import com.tianbo.smartcity.modules.patrol.service.PatrolDeviceRepairService;
import com.tianbo.smartcity.modules.patrol.service.PatrolDeviceService;
import com.tianbo.smartcity.modules.patrol.service.PatrolSiteService;
import com.tianbo.smartcity.modules.your.entity.Floor;
import com.tianbo.smartcity.modules.your.service.FloorService;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fanshaohai
 */
@Slf4j
@RestController
@Api(description = "巡查设备隐患记录管理接口")
@RequestMapping("/smartcity/patrolDeviceRepair")
@Transactional
public class PatrolDeviceRepairController extends SmartCityBaseController<PatrolDeviceRepair, String> {

    @Autowired
    private PatrolDeviceRepairService patrolDeviceRepairService;
    
    @Autowired
    private PatrolDeviceService patrolDeviceService;
    
    @Autowired
    private PatrolSiteService patrolSiteService;
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PatrolDeviceRepairService getService() {
        return patrolDeviceRepairService;
    }


    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<PatrolDeviceRepair>> getByCondition(@ModelAttribute PatrolDeviceRepair patrolDeviceRepair,
                                                            @ModelAttribute SearchVo searchVo,
                                                            @ModelAttribute PageVo pageVo){

        Page<PatrolDeviceRepair> page = patrolDeviceRepairService.findByCondition(patrolDeviceRepair, searchVo, PageUtil.initPage(pageVo));
        for(PatrolDeviceRepair deviceRepair :page.getContent()) {
        	if(StrUtil.isNotBlank(deviceRepair.getDeviceId())) {
        		//查询巡查点名称
        		try {
        			PatrolDevice device = patrolDeviceService.get(deviceRepair.getDeviceId());
            		if(device !=null && device.getSiteId()!=null) {
            			PatrolSite site = patrolSiteService.get(device.getSiteId());
            			if(site !=null) {
            				deviceRepair.setSiteName(site.getName());
            			}
            		}
        		}catch(Exception e) {
        			deviceRepair.setSiteName("");
        		}
        		
        	}
        	// 清除持久上下文环境 避免后面语句导致持久化
        	entityManager.clear();
        }
        return new ResultUtil<Page<PatrolDeviceRepair>>().setData(page);
        
        
    }

}
