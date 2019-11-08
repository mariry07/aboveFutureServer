package com.tianbo.smartcity.modules.patrol.entity;

import com.tianbo.smartcity.base.SmartCityBaseEntity;
import com.tianbo.smartcity.common.utils.IdWorker;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tianbo.smartcity.modules.base.entity.User;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * @author fanshaohai
 */
@Data
@Entity
@Table(name = "tb_patrol_plan_site")
@TableName("tb_patrol_plan_site")
@ApiModel(value = "巡查计划巡查点/巡更任务巡更点")
@SQLDelete(sql = "update tb_patrol_plan_site set del_flag = 1 where id = ?")
@Where(clause = "del_flag = 0")
public class PatrolPlanSite extends SmartCityBaseEntity {

    private static final long serialVersionUID = 1L;
    
    @Id
    @TableId
    @ApiModelProperty(value = "ID")
    private String id = IdWorker.generateShortUuid();
	
	@ApiModelProperty(value="任务id")
    private String planId;
	
	@ApiModelProperty(value="巡查/巡更点id")
    private String siteId;

    @ApiModelProperty(value="巡更/巡查顺序")
    private Integer siteIndex;



}