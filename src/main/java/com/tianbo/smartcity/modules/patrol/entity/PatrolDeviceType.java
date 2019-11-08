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
@Table(name = "tb_patrol_device_type")
@TableName("tb_patrol_device_type")
@ApiModel(value = "设备类型")
@Where(clause = "del_flag = 0")
@SQLDelete(sql = "update tb_patrol_device_type set del_flag = 1 where id = ?")
public class PatrolDeviceType extends SmartCityBaseEntity {

    private static final long serialVersionUID = 1L;
    
    @Id
    @TableId
    @ApiModelProperty(value = "以父ID为前缀，每级4位，3级")
    private String id =IdWorker.generateShortUuid(4);

    @ApiModelProperty(value="父ID，一级父ID：0，不可修改")
    private String parentId;
    
    @ApiModelProperty(value="级别,1，2，3")
    private Integer level;
    
    @ApiModelProperty(value="名称")
    private String name;
    
    @ApiModelProperty(value="说明")
    private String description;
    
    @ApiModelProperty(value="拼接名称")
    private String allName;
    
    @ApiModelProperty(value="公司id")
    private String companyId;
    
	@Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "父节点名称")
    private String parentTitle;
	
	@Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "节点名称")
    private String title;

}