package com.tianbo.smartcity.modules.patrol.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.tianbo.smartcity.common.utils.PageUtil;
import com.tianbo.smartcity.common.utils.ResultUtil;
import com.tianbo.smartcity.common.vo.PageVo;
import com.tianbo.smartcity.common.vo.Result;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.patrol.entity.PatrolCheckItem;
import com.tianbo.smartcity.modules.patrol.entity.PatrolDevice;
import com.tianbo.smartcity.modules.patrol.entity.PatrolDeviceType;
import com.tianbo.smartcity.modules.patrol.service.PatrolCheckItemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author fanshaohai
 */
@Slf4j
@RestController
@Api(description = "设备类型巡查项管理接口")
@RequestMapping("/smartcity/patrolCheckItem")
@Transactional
public class PatrolCheckItemController extends SmartCityBaseController<PatrolCheckItem, String> {

    @Autowired
    private PatrolCheckItemService patrolCheckItemService;

    @Override
    public PatrolCheckItemService getService() {
        return patrolCheckItemService;
    }


    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<PatrolCheckItem>> getByCondition(@ModelAttribute PatrolCheckItem patrolCheckItem,
                                                            @ModelAttribute SearchVo searchVo,
                                                            @ModelAttribute PageVo pageVo){

        Page<PatrolCheckItem> page = patrolCheckItemService.findByCondition(patrolCheckItem, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<PatrolCheckItem>>().setData(page);
    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存数据")
    public Result<PatrolCheckItem> save(@ModelAttribute PatrolCheckItem entity) {
    	ResultUtil<PatrolCheckItem> res =new ResultUtil<PatrolCheckItem>();
    	PatrolCheckItem condition = new PatrolCheckItem();
    	condition.setName(entity.getName());
    	condition.setDeviceTypeId(entity.getDeviceTypeId());
    	List<PatrolCheckItem> list = patrolCheckItemService.findAllByCondition(condition);
    	if(list.size()>0) {
    		return res.setErrorMsg("检查项名称在该设备类型下已经存在！");
    	}
    	PatrolCheckItem e = patrolCheckItemService.save(entity);
        return res.setData(e);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "更新数据")
    public Result<PatrolCheckItem> update(@ModelAttribute PatrolCheckItem entity) {
    	ResultUtil<PatrolCheckItem> res =new ResultUtil<PatrolCheckItem>();
    	PatrolCheckItem condition = new PatrolCheckItem();
    	condition.setName(entity.getName());
    	condition.setDeviceTypeId(entity.getDeviceTypeId());
    	List<PatrolCheckItem> list = patrolCheckItemService.findAllByCondition(condition);
    	if(list.size()>0) {
    		for(PatrolCheckItem p:list) {
    			if(!p.getId().equals(entity.getId())&&p.getName().equals(entity.getName())) {
    				return res.setErrorMsg("检查项名称在该设备类型下已经存在！");
    			}
    		}
    	}
    	PatrolCheckItem e = patrolCheckItemService.update(entity);
        return new ResultUtil<PatrolCheckItem>().setData(e);
    }


}
