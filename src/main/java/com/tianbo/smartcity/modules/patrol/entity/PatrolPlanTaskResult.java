package com.tianbo.smartcity.modules.patrol.entity;

import com.tianbo.smartcity.base.SmartCityBaseEntity;
import com.tianbo.smartcity.common.utils.IdWorker;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * @author fanshaohai
 */
@Data
@Entity
@Table(name = "tb_patrol_plan_task_result")
@TableName("tb_patrol_plan_task_result")
@ApiModel(value = "巡查结果")
@SQLDelete(sql = "update tb_patrol_plan_task_result set del_flag = 1 where id = ?")
@Where(clause = "del_flag = 0")
public class PatrolPlanTaskResult extends SmartCityBaseEntity {

    private static final long serialVersionUID = 1L;
    
    @Id
    @TableId
    @ApiModelProperty(value = "ID")
    private String id = IdWorker.generateShortUuid();

    @ApiModelProperty(value="任务id")
    private String taskId;

    @ApiModelProperty(value="巡查点id")
    private String siteId;

    @ApiModelProperty(value="巡查设备id")
    private String deviceId;

    @ApiModelProperty(value="检查项id")
    private String checkItemId;

    @ApiModelProperty(value="任务提交的备注")
    private String remark;

    @ApiModelProperty(value="图片ID")
    private String photoId;

    @ApiModelProperty(value="结果提交时间")
    private Date addTime;

    @ApiModelProperty(value="提交结果用户id")
    private String doUserId;

    @ApiModelProperty(value="检查结果：1正常，2异常")
    private Integer checkResult;

}