package com.tianbo.smartcity.modules.patrol.vo;

import lombok.Data;

@Data
public class PatrolReportVo {
    
    private  Integer siteCount;//巡查点总数
    private Integer unSiteCount ;//未巡查点总数
    private  Integer alSiteCount;//已巡查点总数
    private Integer deviceCount ;//设备总数
    private int alSite; //已巡查点
    private int unSite; //未巡查点
    private int intactDevice; //设备完好
    private String floorName;
    private String floorId;
    private int planCount;//巡查计划类型数量
    private String typeName; //巡查计划类型名称
    private int deviceStatusCount;//巡查设备状态数量
    private String statusName; //巡查设备状态名称

}
