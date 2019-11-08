package com.tianbo.smartcity.modules.your.dao;

import com.tianbo.smartcity.base.SmartCityBaseDao;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.your.entity.DeviceFault;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

/**
 * 主机故障数据处理层
 * @author Pangzy
 */
public interface DeviceFaultDao extends SmartCityBaseDao<DeviceFault,String> {

    @Query(value = "select a.* from t_device_fault a left join  t_device b on a.device_id = b.id", countQuery = "select count(*) from t_device_fault", nativeQuery = true)
    Page<DeviceFault> getDeviceFaultList(@Param("deviceFault") DeviceFault deviceFault, @Param("searchVo")SearchVo searchVo,@Param("initPage")Pageable initPage);
}