package com.tianbo.smartcity.modules.your.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.tianbo.smartcity.base.SmartCityBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tianbo.smartcity.common.utils.IdWorker;
import com.tianbo.smartcity.common.utils.SnowFlakeUtil;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Pangzy
 */
@Data
@Entity
@Table(name = "t_sensor_type")
@TableName("t_sensor_type")
@ApiModel(value = "传感器故障")
public class SensorType {

    private static final long serialVersionUID = 1L;

    @Id
    @TableId
    @ApiModelProperty(value = "唯一标识")
    private String id = String.valueOf(SnowFlakeUtil.getFlowIdInstance().nextId());

    @ApiModelProperty(value = "系统类型 10消控联网系统 11消防用水系统 12用电安全系统 13动态监测系统 14燃气监测系统")
    @Column(length = 2)
    private Integer systemType;

    @ApiModelProperty(value = "设备类型 1001无线烟感主机")
    @Column(length = 4)
    private Integer deviceType;

    @ApiModelProperty(value = "传感器名称")
    @Column(length = 50)
    private String name;

    @ApiModelProperty(value = "故障名称")
    @Column(length = 64)
    private String faultName;

    @ApiModelProperty(value = "告警名称")
    @Column(length = 64)
    private String alarmName;

    @ApiModelProperty(value = "单位")
    @Column(length = 10)
    private String unit;

    @ApiModelProperty(value = "报警等级，0为故障,1,2,3,低中高")
    @Column(length = 4)
    private Integer alarmLevel;

    @ApiModelProperty(value = "上限阈值")
    @Column(length = 10)
    private Boolean limitUp;

    @ApiModelProperty(value = "下限阈值")
    @Column(length = 10)
    private Boolean limitDown;

    @ApiModelProperty(value = "预警上限（%）")
    @Column(length = 2)
    private Integer preLimitUp;

    @ApiModelProperty(value = "预警下限（%）")
    @Column(length = 2)
    private Integer preLimitDown;
}