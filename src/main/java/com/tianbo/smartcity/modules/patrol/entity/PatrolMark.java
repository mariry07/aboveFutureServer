package com.tianbo.smartcity.modules.patrol.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tianbo.smartcity.base.SmartCityBaseEntity;
import com.tianbo.smartcity.modules.base.entity.File;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * 巡更点
 * Id 同时为巡更点二维码值，前缀：LO
 * @author WangCH
 */
@Data
@Entity
@Table(name = "tb_patrol_mark")
@TableName("tb_patrol_mark")
@ApiModel(value = "巡更点")
@SQLDelete(sql = "update tb_patrol_mark set del_flag = 1 where id = ?")
@Where(clause = "del_flag = 0")
public class PatrolMark extends SmartCityBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "纬度坐标")
    @Column(name="pos_x")
    private String posX;

    @ApiModelProperty(value = "经度坐标")
    @Column(name="pos_y")
    private String posY;

    @ApiModelProperty(value = "公司Id")
    private String companyId;

    @ApiModelProperty(value = "部门Id")
    private String departmentId;

    @ApiModelProperty(value = "二维码")
    private String qrCode;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "已选择")
    private Integer selected = 0;
}