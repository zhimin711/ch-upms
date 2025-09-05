package com.ch.cloud.upms.user.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 后台角色表
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-07-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("st_role")
@Schema(description = "后台角色表")
public class Role extends Model<Role> {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "代码")
    private String code;

    @Schema(description = "类型: 1.系统 2.自定义)")
    private String type;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "状态: 0.禁用 1.启动")
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

}