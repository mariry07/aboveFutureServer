package com.tianbo.smartcity.modules.patrol.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@Table(name = "tb_patrol_site")
@TableName("tb_patrol_site")
@ApiModel(value = "巡查点")
@SQLDelete(sql = "update tb_patrol_site set del_flag = 1 where id = ?")
@Where(clause = "del_flag = 0")
public class PatrolSite extends SmartCityBaseEntity {

    private static final long serialVersionUID = 1L;
    
    @Id
    @TableId
    @ApiModelProperty(value = "ID，同时为巡查点二维码值，前缀：ST")
    private String id = IdWorker.generateShortUuid();


    @ApiModelProperty(value="名称")
    private String name;

    @ApiModelProperty(value="说明")
    private String description;

    @ApiModelProperty(value="纬度坐标")
    private String posX;

    @ApiModelProperty(value="经度坐标")
    private String posY;

    @ApiModelProperty(value="楼层id")
    private String floorId;

    @ApiModelProperty(value="图片文件ID")
    private String photoId;

    @ApiModelProperty(value="公司id")
    private String companyId;

    @ApiModelProperty(value="部门id")
    private String departmentId;
    
    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "楼层名称")
    private String floorName;
    
    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "设备类型名称")
    private String deviceTypeName;
    
    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "设备总数")
    private Integer deviceCount;
    
    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "检查项总数")
    private Integer checkItemCount;
    
    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "设备完好率")
    private String availRate;
    
    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "已选择")
    private Integer selected = 0;

}