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
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Pangzy
 */
@Data
@Entity
@Table(name = "t_device")
@TableName("t_device")
@ApiModel(value = "设备表")
public class Device {

    private static final long serialVersionUID = 1L;
    @Id
    @TableId
    @ApiModelProperty(value = "唯一标识")
    private String id = String.valueOf(SnowFlakeUtil.getFlowIdInstance().nextId());

    @ApiModelProperty(value = "设备名称")
    private String name;

    @ApiModelProperty(value = "设备编号")
    private String code;

    @ApiModelProperty(value = "版本")
    private String version;

    @ApiModelProperty(value = "系统类型 10消控联网系统 11消防用水系统 12用电安全系统 13动态监测系统 14燃气监测系统")
    private Integer systemType;

    @ApiModelProperty(value = "设备类型 1001无线烟感主机 1002消防用水主机")
    private Integer deviceType;

    @ApiModelProperty(value = "0未激活，1离线，2正常，3故障，4报警，5禁用")
    private String runningState;

    @ApiModelProperty(value = "负责人")
    private String chargeName;

    @ApiModelProperty(value = "负责人电话")
    private String chargePhone;

    @ApiModelProperty(value = "SIM卡ID")
    private String sim;

    @ApiModelProperty(value = "移动设备识别码")
    private String imei;

    @ApiModelProperty(value = "图片文件ID")
    private String photoId;

    @ApiModelProperty(value = "是否室外0 室内，1室外")
    private Integer outdoor;

    @ApiModelProperty(value = "设备位置信息")
    private String position;

    @ApiModelProperty(value = "纬度坐标")
    private String posX;

    @ApiModelProperty(value = "经度坐标")
    private String posY;

    @ApiModelProperty(value = "设备所属公司地理位置ID")
    private String floorId;

    @ApiModelProperty(value = "公司id")
    private String companyId;

    @ApiModelProperty(value = "部门id")
    private String departmentId;

    @ApiModelProperty(value = "关联摄像头ID")
    private String cameraId;

    @ApiModelProperty(value = "删除标志 默认0")
    private Integer delFlag = CommonConstant.STATUS_NORMAL;

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

    /*
        前端vue Tree组件相关定义
     */
    @Transient
    @ApiModelProperty(value = "父节点名称")
    private String parentTitle;

    /*
        前端vue Tree组件相关定义
     */
    @Transient
    @ApiModelProperty(value = "父节点名称")
    private String parentTitleId;

    /*
        前端vue Tree组件相关定义
     */
    @Transient
    @ApiModelProperty(value = "Tree树状图显示名称")
    @Column(length = 100)
    private String title;

    /*
       前端vue Tree组件相关定义
    */
    @Transient
    @ApiModelProperty(value = "Tree树状图显示名称")
    @Column(length = 100)
    private String titleId;

    /*
      前端vue Tree组件相关定义
   */
    @Transient
    @ApiModelProperty(value = "图片")
    @Column(length = 100)
    private String img;



}