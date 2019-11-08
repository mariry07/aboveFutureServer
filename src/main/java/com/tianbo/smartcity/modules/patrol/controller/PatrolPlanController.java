package com.tianbo.smartcity.modules.patrol.controller;

import com.tianbo.smartcity.base.SmartCityBaseController;
import com.tianbo.smartcity.common.enums.PatrolPlanType;
import com.tianbo.smartcity.common.exception.SmartCityException;
import com.tianbo.smartcity.common.utils.PageUtil;
import com.tianbo.smartcity.common.utils.ResultUtil;
import com.tianbo.smartcity.common.vo.PageVo;
import com.tianbo.smartcity.common.vo.Result;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.patrol.entity.PatrolPlan;
import com.tianbo.smartcity.modules.patrol.entity.PatrolVo;
import com.tianbo.smartcity.modules.patrol.service.PatrolPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author fanshaohai
 */
@Slf4j
@RestController
@Api(description = "巡查/巡更计划管理接口")
@RequestMapping("/smartcity/patrolPlan")
@Transactional
public class PatrolPlanController extends SmartCityBaseController<PatrolPlan, String> {

    @Autowired
    private PatrolPlanService patrolPlanService;

    @Override
    public PatrolPlanService getService() {
        return patrolPlanService;
    }


    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<PatrolPlan>> getByCondition(@ModelAttribute PatrolPlan patrolPlan,
                                                            @ModelAttribute SearchVo searchVo,
                                                            @ModelAttribute PageVo pageVo){
        Page<PatrolPlan> page = patrolPlanService.findByCondition(patrolPlan, searchVo, PageUtil.initPage(pageVo));
        for(PatrolPlan item: page.getContent()) {
            if (item.getType() != null ) {
                //巡查/巡更类型名称
            	try {
            		String typeName = PatrolPlanType.getNameByCode(item.getType());
                    item.setTypeName(typeName);
        		}catch(Exception e) {
                    item.setTypeName("");
        		}
            }
        }
        return new ResultUtil<Page<PatrolPlan>>().setData(page);
    }

    @RequestMapping(value = "/getPatrol/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "通过id获取计划")
    public Result<PatrolVo> getPatrol(@PathVariable String id) {

        PatrolVo patrolVo = getService().getPatrol(id);
        return new ResultUtil<PatrolVo>().setData(patrolVo);
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "新增计划")
    public Result<PatrolPlan> add(@ModelAttribute PatrolPlan patrolPlan, @RequestParam List<String> list) {
        PatrolPlan result;
        try{
             result = patrolPlanService.add(patrolPlan,list);
        } catch (Exception e) {
            log.warn(e.toString());
            throw new SmartCityException("添加计划失败");
        }
        return new ResultUtil<PatrolPlan>().setData(result);
    }


    @RequestMapping(value = "/pause", method = RequestMethod.PUT)
    @ApiOperation(value = "注销计划")
    public Result<Object> pause(@RequestParam String id) {
        String result;
        try{
             result = patrolPlanService.pause(id);
        } catch (Exception e) {
            log.warn(e.toString());
            throw new SmartCityException("注销计划失败");
        }
        return new ResultUtil<>().setSuccessMsg(result);
    }

    @RequestMapping(value = "/resume", method = RequestMethod.PUT)
    @ApiOperation(value = "恢复计划")
    public Result<Object> resume(@RequestParam String id) {
        String result;
        try{
            result = patrolPlanService.resume(id);
        } catch (Exception e) {
            log.warn(e.toString());
            throw new SmartCityException("恢复计划失败");
        }
        return new ResultUtil<>().setSuccessMsg(result);
    }


    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除计划")
    public Result<Object> delete(@PathVariable String[] ids) {
        String result;
        try{
             result = patrolPlanService.delete(ids);
        } catch (Exception e) {
            log.warn(e.toString());
            throw new SmartCityException("删除计划失败");
        }
        return new ResultUtil<>().setSuccessMsg(result);
    }

}
