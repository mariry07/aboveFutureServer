package com.tianbo.smartcity.modules.your.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.tianbo.smartcity.base.SmartCityBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tianbo.smartcity.common.constant.CommonConstant;
import com.tianbo.smartcity.common.utils.IdWorker;
import com.tianbo.smartcity.common.utils.SnowFlakeUtil;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * @author Pangzy
 */
@Data
@Entity
@Table(name = "t_floor")
@TableName("t_floor")
@ApiModel(value = "楼层信息")
public class Floor{

    @ApiModelProperty(value = "以父ID为前缀，每级4位，2级")
    private static final long serialVersionUID = 1L;

    @Id
    @TableId
    @ApiModelProperty(value = "唯一标识")
    private String id = IdWorker.generateShortUuid();

    @ApiModelProperty(value = "一级父ID：0，不可修改")
    @Column(length = 32)
    private String parentId;

    @ApiModelProperty(value = "公司Id")
    @Column(length = 32)
    private String companyId;

    @ApiModelProperty(value = "建筑物名称")
    @Column(length = 100)
    private String name;

    @ApiModelProperty(value = "拼接名称")
    @Column(length = 300)
    private String allName;

    @ApiModelProperty(value = "经度坐标")
    @Column(length = 20)
    private String posX;

    @ApiModelProperty(value = "纬度坐标")
    @Column(length = 20)
    private String posY;

    @ApiModelProperty(value = "图片文件ID")
    @Column(length = 32)
    private String photoId;

    @ApiModelProperty(value = "显示顺序,楼层显示")
    @Column(length = 5)
    private Integer sort;

    @ApiModelProperty(value = "1，2")
    @Column(length = 2)
    private Integer  level;

    @ApiModelProperty(value = "部门ID")
    @Column(length = 32)
    private String  departmentId;

    @ApiModelProperty(value = "楼层平面图")
    @Column(length = 100)
    private String  img;

    @ApiModelProperty(value = "创建者")
    @CreatedBy
    private String createBy;

    @CreatedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    @LastModifiedBy
    private String updateBy;

    @LastModifiedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "删除标志 默认0")
    private Integer delFlag = CommonConstant.STATUS_NORMAL;


    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "子菜单/权限")
    private List<Floor> children;
    /*
        前端vue Tree组件相关定义
     */
    @Transient
    @ApiModelProperty(value = "父节点名称")
    private String parentTitle;

    /*
        前端vue Tree组件相关定义
     */
    @Transient
    @ApiModelProperty(value = "Tree树状图显示名称")
    @Column(length = 100)
    private String title;

    /*
       前端vue Tree组件相关定义
    */
    @Transient
    @ApiModelProperty(value = "所属科室名称")
    private String departmentTitle;

}