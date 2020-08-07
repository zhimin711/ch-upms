package com.ch.cloud.upms.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 后台权限表
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-07-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("st_permission")
@ApiModel(value="Permission对象", description="后台权限表")
public class Permission extends Model<Permission> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "类型(1.目录 2.菜单页 3.按钮)")
    private String type;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "代码")
    private String code;

    @ApiModelProperty(value = "链接地址")
    private String url;

    @ApiModelProperty(value = "转发地址")
    private String redirect;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "菜单顺序")
    private Integer sort;

    @ApiModelProperty(value = "是否隐藏(0.否 1.是)")
    private Boolean hidden;

    @ApiModelProperty(value = "是否为系统权限(0.否 1.是)")
    @TableField("IS_SYS")
    private Boolean sys;

    @ApiModelProperty(value = "上级菜单ID")
    private String parentId;

    private String parentName;

    @ApiModelProperty(value = "请求方法")
    private String method;

    @ApiModelProperty(value = "状态: 0.禁用 1.启用")
    private String status;

    @ApiModelProperty(value = "删除标志（0代表存在 1代表删除）")
//    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateAt;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @TableField(exist = false)
    List<Permission> children;
}
