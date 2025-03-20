package com.ch.cloud.upms.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@Schema(description = "权限对象")
public class Permission extends Model<Permission> {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @Schema(description = "类型(1.目录 2.菜单页 3.按钮 4. 5.)")
    private String type;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "代码")
    private String code;

    @Schema(description = "链接地址")
    private String url;

    @Schema(description = "转发地址")
    private String redirect;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "菜单顺序")
    private Integer sort;

    @Schema(description = "是否隐藏(0.否 1.是)")
    private Boolean hidden;

    @Schema(description = "是否开始Cookie(0.否 1.是)")
    private Boolean enableCookie;

    @Schema(description = "是否为系统权限(0.否 1.是)")
    @TableField("IS_SYS")
    private Boolean sys;

    @Schema(description = "上级菜单ID")
    private String parentId;

    private String parentName;

    @Schema(description = "请求方法")
    private String method;

    @Schema(description = "状态: 0.禁用 1.启用")
    private String status;

    @Schema(description = "删除标志（0代表存在 1代表删除）")
//    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Boolean deleted;

    @Schema(description = "创建人")
    private String createBy;

    @Schema(description = "创建时间")
    private Date createAt;

    @Schema(description = "更新人")
    private String updateBy;

    @Schema(description = "更新时间")
    private Date updateAt;

    @TableField(exist = false)
    private Boolean hasChildren;
    @TableField(exist = false)
    private List<Permission> children;
}
