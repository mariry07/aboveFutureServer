package com.tianbo.smartcity.modules.activiti.service;

import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.activiti.entity.ActBusiness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 业务申请接口
 *
 * @author Exrick
 */
public interface ActBusinessService extends SmartCityBaseService<ActBusiness, String> {

    /**
     * 多条件分页获取申请列表
     *
     * @param actBusiness
     * @param searchVo
     * @param pageable
     * @return
     */
    Page<ActBusiness> findByCondition(ActBusiness actBusiness, SearchVo searchVo, Pageable pageable);

    /**
     * 通过流程定义id获取
     *
     * @param procDefId
     * @return
     */
    List<ActBusiness> findByProcDefId(String procDefId);
}