package com.tianbo.smartcity.modules.patrol.service;

import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.patrol.entity.PatrolMark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * 巡更点接口
 * @author WangCH
 */
public interface PatrolMarkService extends SmartCityBaseService<PatrolMark,String> {

    /**
     *  多条件分页获取
     * @param patrolMark
     * @param searchVo
     * @param pageable
     * @return
     */
     Page<PatrolMark> findByCondition(PatrolMark patrolMark, SearchVo searchVo, Pageable pageable);

    /**
     * 新增巡更点
     * @param patrolMark
     * @return
     */
     PatrolMark add(PatrolMark patrolMark);


    /**
     * 根据名字获取巡更点数目
     * @param name
     * @return
     */
     Integer countByNameAndIdNot(String name,String id);
}