package com.tianbo.smartcity.modules.patrol.entity;

import com.tianbo.smartcity.base.SmartCityBaseEntity;
import com.tianbo.smartcity.common.constant.CommonConstant;
import com.tianbo.smartcity.common.utils.IdWorker;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.tianbo.smartcity.modules.base.entity.User;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

import javax.persistence.*;

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
@Table(name = "tb_patrol_plan_task")
@TableName("tb_patrol_plan_task")
@ApiModel(value = "巡查/巡更任务")
@SQLDelete(sql = "update tb_patrol_plan_task set del_flag = 1 where id = ?")
@Where(clause = "del_flag = 0")
public class PatrolPlanTask extends SmartCityBaseEntity {

    private static final long serialVersionUID = 1L;
    
    @Id
    @TableId
    @ApiModelProperty(value = "ID")
    private String id = IdWorker.generateShortUuid();

	@ApiModelProperty(value="巡查计划id")
    private String planId;

	@ApiModelProperty(value="名称")
    private String name;

	@ApiModelProperty(value="类型:0日检,1周检,2月检,3季检,4半年检,5年检,6临时")
    private Integer type;

	@ApiModelProperty(value="做任务的用户id")
    private String doUserId;
	
	@CreatedDate
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value="任务开始时间")
    private Date startTime;
	
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value="任务截止时间")
    private Date endTime;

	@ApiModelProperty(value="任务完成率")
    private Float finishedRate;
	
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value="任务完成时间")
    private Date finishedTime;

	@ApiModelProperty(value="任务状态：1进行中，2完成，3未完成")
    private Integer status;

	@ApiModelProperty(value="公司id")
    private String companyId;

	@ApiModelProperty(value="部门id")
    private String departmentId;

	@ApiModelProperty(value="是巡查计划还是巡更计划,0(false)巡更,1(true)巡查")
    private Integer isPatrol;

	@Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "类型名称")
    private String typeName;
	
	@Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "有效时间")
    private String effDateStr;

    @OneToOne
    @JoinColumn(name = "doUserId",referencedColumnName = "id", insertable=false, updatable=false)
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;
}