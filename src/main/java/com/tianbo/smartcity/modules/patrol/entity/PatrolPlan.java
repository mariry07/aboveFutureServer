package com.tianbo.smartcity.modules.patrol.entity;

import com.tianbo.smartcity.base.SmartCityBaseEntity;
import com.tianbo.smartcity.common.constant.CommonConstant;
import com.tianbo.smartcity.common.utils.IdWorker;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tianbo.smartcity.modules.base.entity.User;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


import javax.persistence.*;

/**
 * @author fanshaohai
 */
@Data
@Entity
@Table(name = "tb_patrol_plan")
@TableName("tb_patrol_plan")
@ApiModel(value = "巡查/巡更计划")
@Where(clause = "del_flag = 0")
@SQLDelete(sql = "update tb_patrol_plan set del_flag = 1 where id = ?")
public class PatrolPlan extends SmartCityBaseEntity {

    private static final long serialVersionUID = 1L;
    
    @Id
    @TableId
    @ApiModelProperty(value = "ID")
    private String id = IdWorker.generateShortUuid();
	
	@ApiModelProperty(value="做任务的用户id")
    private String doUserId;
	
	@ApiModelProperty(value="名称")
    private String name;
	
	@ApiModelProperty(value="说明")
    private String description;
	
	@ApiModelProperty(value="类型:0日检,1周检,2月检,3季检,4半年检,5年检,6临时")
    private Integer type;
	
	@ApiModelProperty(value="计划是否正在执行，0否，1是")
    private Integer isDoing= CommonConstant.STATUS_NORMAL;
	
	@ApiModelProperty(value="计划下任务的有效天数")
    private Integer limitDays;
	
	@ApiModelProperty(value="计划完成率")
    private Float finishedRate;
	
	@ApiModelProperty(value="是巡查计划还是巡更计划,0(false)巡更,1(true)巡查")
    private Integer isPatrol;
	
	@ApiModelProperty(value="公司id")
    private String companyId;
	
	@ApiModelProperty(value="部门id")
    private String departmentId;
	
	@ApiModelProperty(value="创建计划的用户id")
    private Long createUserId;
	
	@Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "类型名称")
    private String typeName;

    @OneToOne
    @JoinColumn(name = "doUserId",referencedColumnName = "id", insertable=false, updatable=false)
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;

}