package com.tianbo.smartcity.modules.patrol.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.tianbo.smartcity.base.SmartCityBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用户轨迹记录
 * @author WangCH
 */
@Data
@Entity
@Table(name = "tb_patrol_user_routes")
@TableName("tb_patrol_user_routes")
@ApiModel(value = "用户轨迹记录")
public class PatrolUserRoutes extends SmartCityBaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @TableId
    @ApiModelProperty(value = "ID，前缀日期yyyyMMddhhmmss")
    private String id;

    @TableId
    @ApiModelProperty(value = "用户Id")
    private String userId;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间")
    private Date addTime;

    @ApiModelProperty(value = "纬度坐标")
    @Column(name="pos_x")
    private String posX;

    @ApiModelProperty(value = "经度坐标")
    @Column(name="pos_y")
    private String posY;

}
