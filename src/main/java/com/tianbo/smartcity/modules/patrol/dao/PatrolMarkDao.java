package com.tianbo.smartcity.modules.patrol.dao;

import com.tianbo.smartcity.base.SmartCityBaseDao;
import com.tianbo.smartcity.modules.patrol.entity.PatrolMark;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 巡更点数据处理层
 * @author Pangzy
 */
public interface PatrolMarkDao extends SmartCityBaseDao<PatrolMark,String> {


    @Query(value = "select m.* from tb_patrol_mark m where m.id IN :ids",nativeQuery = true)
    List<PatrolMark> getListById(List<String> ids);


    /**
     * 根据名字获取巡更点数目
     * @param name
     * @return
     */
    Integer countByNameAndIdNot(@Param("name") String name, @Param("id") String id);

}