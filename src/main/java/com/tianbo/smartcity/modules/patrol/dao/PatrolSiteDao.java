package com.tianbo.smartcity.modules.patrol.dao;

import com.tianbo.smartcity.base.SmartCityBaseDao;
import com.tianbo.smartcity.modules.patrol.entity.PatrolMark;
import com.tianbo.smartcity.modules.patrol.entity.PatrolSite;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 巡查点数据处理层
 * @author fanshaohai
 */
public interface PatrolSiteDao extends SmartCityBaseDao<PatrolSite,String> {

    @Query(value = "select s.* from tb_patrol_site s where s.id IN :ids",nativeQuery = true)
    List<PatrolSite> getListById(List<String> ids);
}