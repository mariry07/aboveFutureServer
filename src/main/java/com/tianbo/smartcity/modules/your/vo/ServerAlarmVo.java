package com.tianbo.smartcity.modules.your.vo;

import lombok.Data;

@Data
public class ServerAlarmVo {
    private String deviceAll;
    private String untreatedAll;
    private String handleAll;
    private String monthAll;
    private String ctime;
    private int count;
    private int untreated; //未处理
    private int processed; //已处理
    private String floorName;
    private String floorId;
    private String alarmName; //报警类型名称
    private String dealType; //故障处理名称
    private  Integer alarmCount;
    private Integer deviceStatusAll ;

}
