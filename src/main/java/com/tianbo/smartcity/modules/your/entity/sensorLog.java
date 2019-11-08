package com.tianbo.smartcity.modules.your.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.tianbo.smartcity.base.SmartCityBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tianbo.smartcity.common.utils.SnowFlakeUtil;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Pangzy
 */
@Data
@Entity
@Table(name = "t_sensor_log")
@TableName("t_sensor_log")
@ApiModel(value = "日志表")
public class sensorLog {

    private static final long serialVersionUID = 1L;
    @Id
    @TableId
    @ApiModelProperty(value = "唯一标识")
    private String id = String.valueOf(SnowFlakeUtil.getFlowIdInstance().nextId());

    @ApiModelProperty(value = "设备Id")
    @Column(length = 32)
    private String deviceId;

    @ApiModelProperty(value = "传感器类型ID")
    @Column(length = 32)
    private String sensorTypeId;

    @LastModifiedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "日志时间")
    private Date logTime;

    @ApiModelProperty(value = "日志类型")
    @Column(length = 32)
    private String logType;

    @ApiModelProperty(value = "日志内容类型")
    @Column(length = 32)
    private String dataType;

    @ApiModelProperty(value = "日志内容")
    @Column(length = 32)
    private String logData;



}