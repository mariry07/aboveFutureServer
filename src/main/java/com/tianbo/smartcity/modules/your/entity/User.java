package com.tianbo.smartcity.modules.your.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tianbo.smartcity.common.utils.SnowFlakeUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="t_user")
@TableName("t_user")
@ApiModel(value = "用户表")
public class User {
    private static final long serialVersionUID = 1L;
    @Id
    @TableId
    @ApiModelProperty(value = "唯一标识")
    //不知道后面这一串是啥东西
    private String id =String.valueOf(SnowFlakeUtil.getFlowIdInstance().nextId());

    private String createBy;

    private String createTime;

    private String updateBy;

    private String updateTime;

    private String adress;
}
