package com.tianbo.smartcity.modules.patrol.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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
@Table(name = "tb_patrol_check_item")
@TableName("tb_patrol_check_item")
@ApiModel(value = "设备类型巡查项")
@Where(clause = "del_flag = 0")
@SQLDelete(sql = "update tb_patrol_check_item set del_flag = 1 where id = ?")
public class PatrolCheckItem extends SmartCityBaseEntity {

    private static final long serialVersionUID = 1L;
    
    @Id
    @TableId
    @ApiModelProperty(value = "ID")
    private String id = IdWorker.generateShortUuid();
	
	@ApiModelProperty(value="名称")
    private String name;

	@ApiModelProperty(value="设备类型ID")
    private String deviceTypeId;

	@ApiModelProperty(value="说明")
    private String description;

	@ApiModelProperty(value="公司id")
    private String companyId;

}