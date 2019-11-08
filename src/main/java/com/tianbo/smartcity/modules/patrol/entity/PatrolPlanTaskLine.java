package com.tianbo.smartcity.modules.patrol.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.tianbo.smartcity.base.SmartCityBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 巡更任务记录
 * @author WangCH
 */
@Data
@Entity
@Table(name = "tb_patrol_plan_task_line")
@TableName("tb_patrol_plan_task_line")
@ApiModel(value = "巡更任务记录")
public class PatrolPlanTaskLine extends SmartCityBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="巡更任务id")
    private String taskId;

    @ApiModelProperty(value="巡更点id")
    private String markId;

    @ApiModelProperty(value = "纬度坐标")
    @Column(name="pos_x")
    private String posX;

    @ApiModelProperty(value = "经度坐标")
    @Column(name="pos_y")
    private String posY;

    @ApiModelProperty(value="图片id")
    private String photoId;

    @ApiModelProperty(value="提交用户id")
    private String doUserId;

    @CreatedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="提交时间")
    private Date addTime;

}
