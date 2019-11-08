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
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author Pangzy
 */
@Data
@Entity
@Table(name = "t_device_fault")
@TableName("t_device_fault")
@ApiModel(value = "主机故障")
public class DeviceFault{

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

    @ApiModelProperty(value = "处理类型")
    private String status;

    @ApiModelProperty(value = "处理人id")
    private String dealUserId;

    @ApiModelProperty(value = "处理详情")
    private String dealDetail;

    @CreatedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "故障时间")
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

    @ApiModelProperty(value = "故障次数")
    private Integer faultCount;

    @ApiModelProperty(value = "传感器类型id")
    private String  sensorTypeId;

    @ApiModelProperty(value = "故障类型名称")
    private String faultTypeName;

    @ApiModelProperty(value = "公司id")
    private String companyId;

    @ApiModelProperty(value = "部门id")
    private String departmentId;

    @ApiModelProperty(value = "删除标志 默认0")
    private Integer delFlag = CommonConstant.STATUS_NORMAL;

}