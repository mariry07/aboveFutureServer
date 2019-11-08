package com.tianbo.smartcity.modules.patrol.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tianbo.smartcity.base.SmartCityBaseController;
import com.tianbo.smartcity.common.constant.CommonConstant;
import com.tianbo.smartcity.common.utils.PageUtil;
import com.tianbo.smartcity.common.utils.QrCodeUtil;
import com.tianbo.smartcity.common.utils.ResultUtil;
import com.tianbo.smartcity.common.vo.PageVo;
import com.tianbo.smartcity.common.vo.Result;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.base.entity.User;
import com.tianbo.smartcity.modules.patrol.entity.PatrolCheckItem;
import com.tianbo.smartcity.modules.patrol.entity.PatrolDevice;
import com.tianbo.smartcity.modules.patrol.entity.PatrolDeviceType;
import com.tianbo.smartcity.modules.patrol.entity.PatrolMark;
import com.tianbo.smartcity.modules.patrol.entity.PatrolPlanTaskResult;
import com.tianbo.smartcity.modules.patrol.entity.PatrolSite;
import com.tianbo.smartcity.modules.patrol.service.PatrolCheckItemService;
import com.tianbo.smartcity.modules.patrol.service.PatrolDeviceService;
import com.tianbo.smartcity.modules.patrol.service.PatrolDeviceTypeService;
import com.tianbo.smartcity.modules.patrol.service.PatrolPlanTaskResultService;
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
@Api(description = "巡查点管理接口")
@RequestMapping("/smartcity/patrolSite")
@Transactional
public class PatrolSiteController extends SmartCityBaseController<PatrolSite, String> {

    @Autowired
    private PatrolSiteService patrolSiteService;

    @Autowired
    private PatrolDeviceService patrolDeviceService;
    
    @Autowired
    private PatrolDeviceTypeService patrolDeviceTypeService;
    
    @Autowired
    private PatrolCheckItemService patrolCheckItemService;
    
    @Autowired
    private PatrolPlanTaskResultService patrolPlanTaskResultService;
    
    @Autowired
    private FloorService floorService;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public PatrolSiteService getService() {
        return patrolSiteService;
    }

 
    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<PatrolSite>> getByCondition(@ModelAttribute PatrolSite patrolSite,
                                                            @ModelAttribute SearchVo searchVo,
                                                            @ModelAttribute PageVo pageVo){

        Page<PatrolSite> page = patrolSiteService.findByCondition(patrolSite, searchVo, PageUtil.initPage(pageVo));
        for(PatrolSite site :page.getContent()) {
        	//查询巡查任务结果
        	PatrolPlanTaskResult patrolPlanTaskResult = new PatrolPlanTaskResult();
        	patrolPlanTaskResult.setSiteId(site.getId());
        	List<PatrolPlanTaskResult> resultList = patrolPlanTaskResultService.findAllByCondition(patrolPlanTaskResult);
        	
        	//查询设备
        	PatrolDevice patrolDevice = new PatrolDevice();
        	patrolDevice.setSiteId(site.getId());
        	List<PatrolDevice> deviceList = patrolDeviceService.findAllByCondition(patrolDevice);
        	if(deviceList.size()>0) {
        		String deviceTypeId = deviceList.get(0).getDeviceTypeId();
        		if(StrUtil.isNotBlank(deviceTypeId)) {
        			//设备类型查询名称
        			PatrolDeviceType deviceType = patrolDeviceTypeService.get(deviceTypeId);
        			site.setDeviceTypeName(deviceType.getName());
        			//设备类型查询检查项
        			PatrolCheckItem patrolCheckItem = new PatrolCheckItem();
        			patrolCheckItem.setDeviceTypeId(deviceTypeId);
        			List<PatrolCheckItem> checkItemList = patrolCheckItemService.findAllByCondition(patrolCheckItem);
        			site.setCheckItemCount(checkItemList.size());
        		}
        		site.setDeviceCount(deviceList.size());
        		int goodCount = site.getDeviceCount();
        		for(PatrolDevice d :deviceList) {
        			boolean flag = true;
        			for(PatrolPlanTaskResult re :resultList ) {
        				if(d.getId().equals(re.getDeviceId())&&re.getCheckResult()!=null &&re.getCheckResult()==2) {
        					flag =false;
        					break;
        				}
        			}
        			if(flag) {
        				goodCount--;
        			}
        		}
        		//设备完好率
        		DecimalFormat df=new DecimalFormat("0.00");
        		site.setAvailRate(df.format((double)goodCount/site.getDeviceCount()));
        		
        	}

        	//查询楼层名称
        	if(StrUtil.isNotBlank(site.getFloorId())) {
        		try {
        			Floor floor = floorService.get(site.getFloorId());
        			if(floor!=null) {
        				site.setFloorName(floor.getName());
        			}
        		}catch(Exception e) {
        			site.setFloorName("");
        		}
        	}
        	// 清除持久上下文环境 避免后面语句导致持久化
        	entityManager.clear();
        }
        return new ResultUtil<Page<PatrolSite>>().setData(page);
    }
    
    @RequestMapping(value = "/getByParentId/{parentId}", method = RequestMethod.GET)
    @ApiOperation(value = "通过parentId获取楼层")
    public Result<List<Floor>> getByParentId(@PathVariable String parentId){

        List<Floor> list = new ArrayList<>();
        list = floorService.findByParentIdAndDelFlag(parentId,CommonConstant.STATUS_NORMAL);
        list = setInfo(list);
        return new ResultUtil<List<Floor>>().setData(list);
    }
    
    public List<Floor> setInfo(List<Floor> list){

        // lambda表达式
        list.forEach(item -> {
        	item.setTitle(item.getName());
            if(!CommonConstant.PARENT_ID.equals(item.getParentId())){
            	Floor parent = floorService.get(item.getParentId());
                item.setParentTitle(parent.getName());
            }else{
                item.setParentTitle("");
            }
        });
        return list;
    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "新增巡更点")
    public Result<PatrolSite> save(@ModelAttribute PatrolSite site) {

        ResultUtil<PatrolSite> res =new ResultUtil<PatrolSite>();
        PatrolSite condition = new PatrolSite();
    	condition.setName(site.getName());
    	List<PatrolSite> list = patrolSiteService.findAllByCondition(condition);
    	if(list.size()>0) {
    		return res.setErrorMsg("此巡查点名称已存在,请更换巡查点名称");
    	}
    	PatrolSite e = patrolSiteService.add(site);
        return res.setData(e);
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "更新数据")
    public Result<PatrolSite> update(@ModelAttribute PatrolSite entity) {
    	ResultUtil<PatrolSite> res =new ResultUtil<PatrolSite>();
    	PatrolSite condition = new PatrolSite();
    	condition.setName(entity.getName());
    	List<PatrolSite> list = patrolSiteService.findAllByCondition(condition);
    	if(list.size()>0) {
    		for(PatrolSite p:list) {
    			if(!p.getId().equals(entity.getId())&&p.getName().equals(entity.getName())) {
    				return res.setErrorMsg("检查项名称在该设备类型下已经存在！");
    			}
    		}
    	}
    	PatrolSite e = patrolSiteService.update(entity);
        return new ResultUtil<PatrolSite>().setData(e);
    }
    

}
