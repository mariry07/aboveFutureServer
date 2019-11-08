package com.tianbo.smartcity.modules.patrol.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.tianbo.smartcity.base.SmartCityBaseEntity;
import com.tianbo.smartcity.common.constant.CommonConstant;
import com.tianbo.smartcity.common.utils.IdWorker;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author fanshaohai
 */
@Data
@Entity
@Table(name = "tb_patrol_device")
@TableName("tb_patrol_device")
@ApiModel(value = "巡查设备")
@Where(clause = "del_flag = 0")
@SQLDelete(sql = "update tb_patrol_device set del_flag = 1 where id = ?")
public class PatrolDevice extends SmartCityBaseEntity {
	
    private static final long serialVersionUID = 1L;
	
	@Id
    @TableId
    @ApiModelProperty(value = "ID，同时为巡查设备二维码值，前缀：DV")
    private String id = IdWorker.generateShortUuid();
	
	@ApiModelProperty(value="名称")
    private String name;

	@ApiModelProperty(value="设备类型ID")
    private String deviceTypeId;

	@ApiModelProperty(value="巡查点ID")
    private String siteId;

	@ApiModelProperty(value="设备型号")
    private String model;

	@ApiModelProperty(value="规格")
    private String spec;

	@ApiModelProperty(value="设备厂商")
    private String manufactor;

	@ApiModelProperty(value="供货单位")
    private String provider;

	@ApiModelProperty(value="品牌")
    private String brand;
	
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value="出厂时间")
    private Date productTime;
	
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value="保修时间")
    private Date repaireTime;
	
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value="过期时间")
    private Date expireTime;

	@ApiModelProperty(value="过期预警天数")
    private Integer alarmDays;

	@ApiModelProperty(value="说明")
    private String description;

	@ApiModelProperty(value="状态0未使用1使用中2出现问题3维修4报废5到期6即将到期")
    private Integer status = 1;
	
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value="报废时间")
    private Date stopTime;

	@ApiModelProperty(value="公司id")
    private String companyId;

	@ApiModelProperty(value="部门id")
    private String departmentId;

	@ApiModelProperty(value="设备编码")
    private String code;

	@ApiModelProperty(value="楼层id")
    private String floorId;
	
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value="上次巡查时间，默认1970")
    private Date patrolTime;

	@Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "设备类型名称")
    private String deviceTypeName;
	
	@Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "巡查点名称")
    private String siteName;
	
	@Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "楼层名称")
    private String floorName;

}