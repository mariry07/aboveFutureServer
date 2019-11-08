package com.tianbo.smartcity.modules.third.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.tianbo.smartcity.base.SmartCityBaseEntity;
import com.tianbo.smartcity.common.constant.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 第三方平台标识实体类
 *
 * @author yxk
 */
@Data
@Entity
@Table(name = "t_user_third")
@TableName("t_user_third")
@ApiModel(value = "第三方平台标识")
public class ThirdPlatform extends SmartCityBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @Column(name = "user_id", length = 50, nullable = false)
    private String userId;

    @ApiModelProperty(value = "用户名称")
    @Column(name = "user_name", length = 50)
    private String userName;

    @ApiModelProperty(value = "第三方平台名称")
    @Column(name = "third_name", length = 50, nullable = false)
    private String thirdName;

    @ApiModelProperty(value = "第三方平台标识")
    @Column(name = "access_key", length = 100, nullable = false)
    private String accessKey;

    @ApiModelProperty(value = "冻结状态 默认0正常 -1冻结")
    @Column(name = "freeze_status")
    private Integer freezeStatus = CommonConstant.THIRD_STATUS_NORMAL;

}
