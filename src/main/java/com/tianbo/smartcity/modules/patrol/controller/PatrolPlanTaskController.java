package com.tianbo.smartcity.modules.patrol.controller;

import com.tianbo.smartcity.base.SmartCityBaseController;
import com.tianbo.smartcity.common.enums.PatrolPlanType;
import com.tianbo.smartcity.common.utils.PageUtil;
import com.tianbo.smartcity.common.utils.ResultUtil;
import com.tianbo.smartcity.common.vo.PageVo;
import com.tianbo.smartcity.common.vo.Result;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.patrol.entity.PatrolPlan;
import com.tianbo.smartcity.modules.patrol.entity.PatrolVo;
import com.tianbo.smartcity.modules.patrol.entity.PatrolPlanTask;
import com.tianbo.smartcity.modules.patrol.service.PatrolPlanTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author fanshaohai
 */
@Slf4j
@RestController
@Api(description = "巡查/巡更任务管理接口")
@RequestMapping("/smartcity/patrolPlanTask")
@Transactional
public class PatrolPlanTaskController extends SmartCityBaseController<PatrolPlanTask, String> {

    @Autowired
    private PatrolPlanTaskService patrolPlanTaskService;

    @Override
    public PatrolPlanTaskService getService() {
        return patrolPlanTaskService;
    }


    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<PatrolPlanTask>> getByCondition(@ModelAttribute PatrolPlanTask patrolPlanTask,
                                                            @ModelAttribute SearchVo searchVo,
                                                            @ModelAttribute PageVo pageVo){

        Page<PatrolPlanTask> page = patrolPlanTaskService.findByCondition(patrolPlanTask, searchVo, PageUtil.initPage(pageVo));
        for(PatrolPlanTask item: page.getContent()) {
            if (item.getType() != null ) {
                //巡更/巡查类型名称
            	try {
                    String typeName = PatrolPlanType.getNameByCode(item.getType());
                    item.setTypeName(typeName);
        		}catch(Exception e) {
                    item.setTypeName("");
        		}
                
                //有效时间
                /*Calendar calendar = Calendar.getInstance();
                calendar.setTime(item.getStartTime());
                int day1 = calendar.get(Calendar.DAY_OF_YEAR);
                calendar.setTime(item.getEndTime());
                int day2 = calendar.get(Calendar.DAY_OF_YEAR);
                int diff = Math.abs(day2 - day1);
                item.setEffDateStr(diff);*/
                SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
                item.setEffDateStr(sm.format(item.getStartTime())+"至"+sm.format(item.getEndTime()));
                Date date = new Date();
                if(date.after(item.getEndTime())) {
                	//状态改为未完成
                	item.setStatus(3);
                	patrolPlanTaskService.update(item);
                }
}
        }
        return new ResultUtil<Page<PatrolPlanTask>>().setData(page);
    }

    @RequestMapping(value = "/getPatrol/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "通过id获取任务")
    public Result<PatrolVo> getPatrol(@PathVariable String id) {

        PatrolVo patrol = getService().getPatrol(id);
        return new ResultUtil<PatrolVo>().setData(patrol);
    }

}
