package com.tianbo.smartcity.modules.your.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.tianbo.smartcity.common.utils.SnowFlakeUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Pangzy
 */
@Data
@Entity
@Table(name = "t_sensor")
@TableName("t_sensor")
@ApiModel(value = "传感器表")
public class SensorVo {

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
    @ApiModelProperty(value = "最后数据传输时间")
    private Date lastTime;

    @ApiModelProperty(value = "状态，1正常，2故障")
    @Column(length = 4)
    private Integer status;

    @ApiModelProperty(value = "0启用，1禁用")
    @Column(length = 4)
    private Integer disable;

    @Transient
    @ApiModelProperty(value = "监控点名称")
    @Column(length = 100)
    private String deviceName;

    @Transient
    @ApiModelProperty(value = "关联摄像头")
    @Column(length = 100)
    private String cameraId;

    @Transient
    @ApiModelProperty(value = "地理位置")
    @Column(length = 100)
    private String position;

    @Transient
    @ApiModelProperty(value = "传感器名称")
    @Column(length = 100)
    private String sensorTypeName;


}