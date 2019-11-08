package com.tianbo.smartcity.modules.your.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.tianbo.smartcity.base.SmartCityBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tianbo.smartcity.common.constant.CommonConstant;
import com.tianbo.smartcity.common.utils.SnowFlakeUtil;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Pangzy
 */
@Data
@Entity
@Table(name = "t_server_alarm")
@TableName("t_server_alarm")
@ApiModel(value = "报警")
public class ServerAlarm {

    private static final long serialVersionUID = 1L;
    @Id
    @TableId
    @ApiModelProperty(value = "唯一标识")
    private String id = String.valueOf(SnowFlakeUtil.getFlowIdInstance().nextId());

    @ApiModelProperty(value = "设备id")
    private String deviceId;

    @ApiModelProperty(value = "主机名称")
    private String deviceName;

    @ApiModelProperty(value = "主机编码")
    private String deviceCode;

    @ApiModelProperty(value = "负责人")
    private String deviceChargeName;

    @ApiModelProperty(value = "版本")
    private String deviceVersion;

    @ApiModelProperty(value = "系统类型 10消控联网系统 11消防用水系统 12用电安全系统 13动态监测系统 14燃气监测系统")
    private Integer systemType;

    @ApiModelProperty(value = "设备类型 1001无线烟感主机")
    private Integer deviceType;

    @ApiModelProperty(value = "地理/报警位置")
    private String position;

    @ApiModelProperty(value = "报警等级，0为故障,1,2,3,低中高")
    private String level;

    @ApiModelProperty(value = "处理类型")
    private String status;

    @ApiModelProperty(value = "处理类型：0测试 1维保检修 2误报 3 有效处理 4 其它")
    private String dealType;

    @ApiModelProperty(value = "处理人id")
    private String dealUserId;

    @ApiModelProperty(value = "报警值")
    private String dealDetail;

    @CreatedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "报警时间")
    private Date addTime;

    @CreatedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @CreatedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "处理时间")
    private String dealTime;

    @ApiModelProperty(value = "报警次数")
    private Integer alarmCount;

    @ApiModelProperty(value = "告警数据")
    private String  alarmData;

    @ApiModelProperty(value = "上限阈值")
    private String limitUp;

    @ApiModelProperty(value = "下限阈值")
    private String limitDown;

    @ApiModelProperty(value = "数据单位")
    private String unit;

    @ApiModelProperty(value = "传感器类型id")
    private String sensorTypeId;

    @ApiModelProperty(value = "告警类型名称")
    private String alarmTypeName;


    @ApiModelProperty(value = "部门id")
    private String departmentId;

    @ApiModelProperty(value = "公司Id")
    private String companyId;

    @ApiModelProperty(value = "删除标志 默认0")
    private Integer delFlag = CommonConstant.STATUS_NORMAL;

}