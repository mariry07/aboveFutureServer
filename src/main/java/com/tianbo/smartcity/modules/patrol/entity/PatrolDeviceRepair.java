package com.tianbo.smartcity.modules.patrol.entity;

import com.tianbo.smartcity.base.SmartCityBaseEntity;
import com.tianbo.smartcity.common.utils.IdWorker;
import com.tianbo.smartcity.modules.base.entity.User;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author fanshaohai
 */
@Data
@Entity
@Table(name = "tb_patrol_device_repair")
@TableName("tb_patrol_device_repair")
@ApiModel(value = "巡查设备隐患记录")
@Where(clause = "del_flag = 0")
@SQLDelete(sql = "update tb_patrol_device_repair set del_flag = 1 where id = ?")
public class PatrolDeviceRepair extends SmartCityBaseEntity {

    private static final long serialVersionUID = 1L;
    
    @Id
    @TableId
    @ApiModelProperty(value = "ID，同时为巡查设备二维码值，前缀：DV")
    private String id = IdWorker.generateShortUuid();
	
	@ApiModelProperty(value="任务的id")
    private String taskId;
	
	@ApiModelProperty(value="设备的ID")
    private String deviceId;
	
	@ApiModelProperty(value="设备名称")
    private String deviceName;
	
	@ApiModelProperty(value="所在位置")
    private String address;
	
	@ApiModelProperty(value="维修人员的id")
    private String repairUserId;
	
	@ApiModelProperty(value="设备巡查人员的id")
    private String patrolUserId;
	
	@ApiModelProperty(value="设备检查人员id")
    private String checkUserId;
	
	@ApiModelProperty(value="维修之前的图片ID")
    private String beforePhotoId;
	
	@ApiModelProperty(value="故障隐患的描述")
    private String beforeDescription;
	
	@ApiModelProperty(value="维修之后的图片ID")
    private String afterPhotoId;
	
	@ApiModelProperty(value="维修结果的描述")
    private String afterDescription;
	
	@CreatedDate
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value="维修的开始时间")
    private Date startTime;
	
	@CreatedDate
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value="规定维修的结束时间")
    private Date endTime;
	
	@ApiModelProperty(value="0未派发1未完成2未审核3完成4超时完成")
    private Integer status;
	
	@CreatedDate
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value="完成的维修设备时间")
    private Date finishedTime;
	
	@CreatedDate
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value="周期时间(有效时间)")
    private Date periodTime;
	
	@ApiModelProperty(value="修理任务的名称")
    private String name;

	@ApiModelProperty(value="0:未审核，1:未通过，2:已通过")
    private Integer isBack;
	
	@ApiModelProperty(value="检测人员检测后的评语")
    private String checkDescription;
	
	@CreatedDate
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value="管理员检测维修的时间")
    private Date checkTime;
	
	@ApiModelProperty(value="是否需要维修(0需要维修1不需要维修)维修后不改变")
    private Integer isRepair;
	
	@ApiModelProperty(value="公司id")
    private String companyId;
	
	@ApiModelProperty(value="部门id")
    private String departmentId;
	
	@Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "所属巡查点名称")
    private String siteName;
	
	@OneToOne
    @JoinColumn(name = "repairUserId",referencedColumnName = "id", insertable=false, updatable=false)
    @NotFound(action=NotFoundAction.IGNORE)
    private User repairUser;
	
	@OneToOne
    @JoinColumn(name = "patrolUserId",referencedColumnName = "id", insertable=false, updatable=false)
    @NotFound(action=NotFoundAction.IGNORE)
    private User patrolUser;
	
	@OneToOne
    @JoinColumn(name = "checkUserId",referencedColumnName = "id", insertable=false, updatable=false)
    @NotFound(action=NotFoundAction.IGNORE)
    private User checkUser;

}