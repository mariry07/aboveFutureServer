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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tianbo.smartcity.base.SmartCityBaseController;
import com.tianbo.smartcity.common.constant.CommonConstant;
import com.tianbo.smartcity.common.utils.PageUtil;
import com.tianbo.smartcity.common.utils.ResultUtil;
import com.tianbo.smartcity.common.vo.PageVo;
import com.tianbo.smartcity.common.vo.Result;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.patrol.entity.PatrolCheckItem;
import com.tianbo.smartcity.modules.patrol.entity.PatrolDevice;
import com.tianbo.smartcity.modules.patrol.entity.PatrolDeviceType;
import com.tianbo.smartcity.modules.patrol.service.PatrolCheckItemService;
import com.tianbo.smartcity.modules.patrol.service.PatrolDeviceTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author fanshaohai
 */
@Slf4j
@RestController
@Api(description = "设备类型管理接口")
@RequestMapping("/smartcity/patrolDeviceType")
@Transactional
public class PatrolDeviceTypeController extends SmartCityBaseController<PatrolDeviceType, String> {

    @Autowired
    private PatrolDeviceTypeService patrolDeviceTypeService;
    
    @Autowired
    private PatrolCheckItemService patrolCheckItemService;

    @Override
    public PatrolDeviceTypeService getService() {
        return patrolDeviceTypeService;
    }
    
    @RequestMapping(value = "/getByParentId/{parentId}", method = RequestMethod.GET)
    @ApiOperation(value = "通过parentId获取")
    public Result<List<PatrolDeviceType>> getByParentId(@PathVariable String parentId){

        List<PatrolDeviceType> list = new ArrayList<>();
        list = patrolDeviceTypeService.findByParentId(parentId);
        list = setInfo(list);
        return new ResultUtil<List<PatrolDeviceType>>().setData(list);
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加")
    public Result<PatrolDeviceType> add(@ModelAttribute PatrolDeviceType deviceType){
        ResultUtil<PatrolDeviceType> res =new ResultUtil<PatrolDeviceType>();

    	deviceType.setName(deviceType.getTitle());
    	if(deviceType.getLevel()!=null&&deviceType.getLevel()>1) {
    		deviceType.setId(deviceType.getParentId()+deviceType.getId());
    	}
    	PatrolDeviceType condition = new PatrolDeviceType();
    	condition.setParentId(deviceType.getParentId());
    	condition.setName(deviceType.getName());
    	condition.setLevel(deviceType.getLevel());
    	List<PatrolDeviceType> list = patrolDeviceTypeService.findAllByCondition(condition);
    	if(list.size()>0) {
    		return res.setErrorMsg("名称重复!");
    	}
    	PatrolDeviceType t = patrolDeviceTypeService.save(deviceType);
    	res.setSuccessMsg("添加成功!");
        return res.setData(t);
   
    }
    
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "编辑")
    public Result<Object> edit(@ModelAttribute PatrolDeviceType deviceType){

    	PatrolDeviceType t = patrolDeviceTypeService.update(deviceType);
        return new ResultUtil<Object>().setSuccessMsg("编辑成功");
    }
    
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ApiOperation(value = "部门名模糊搜索")
    public Result<List<PatrolDeviceType>> searchByTitle(@RequestParam String title){

        List<PatrolDeviceType> list = patrolDeviceTypeService.findByTitleLike("%"+title+"%");
        list = setInfo(list);
        return new ResultUtil<List<PatrolDeviceType>>().setData(list);
    }


    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<PatrolDeviceType>> getByCondition(@ModelAttribute PatrolDeviceType patrolDeviceType,
                                                            @ModelAttribute SearchVo searchVo,
                                                            @ModelAttribute PageVo pageVo){

        Page<PatrolDeviceType> page = patrolDeviceTypeService.findByCondition(patrolDeviceType, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<PatrolDeviceType>>().setData(page);
    }
    
    public List<PatrolDeviceType> setInfo(List<PatrolDeviceType> list){

        // lambda表达式
        list.forEach(item -> {
        	item.setTitle(item.getName());
            if(!CommonConstant.PARENT_ID.equals(item.getParentId())){
            	PatrolDeviceType parent = patrolDeviceTypeService.get(item.getParentId());
                item.setParentTitle(parent.getName());
            }else{
                item.setParentTitle("消防设备");
            }
        });
        return list;
    }
}
