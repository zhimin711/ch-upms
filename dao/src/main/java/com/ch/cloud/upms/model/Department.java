package com.ch.cloud.upms.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-07-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("st_department")
@Schema(description = "部门表")
public class Department extends Model<Department> {

    private static final long serialVersionUID = 1L;

    @Schema(description = "部门id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "上级部门id")
    private Long pid;

    @Schema(description = "上级部门ID")
    private String parentId;

    @Schema(description = "上级部门名称")
    private String parentName;

    @Schema(description = "部门名称")
    private String name;

    @Schema(description = "部门类型：0集团 1公司 2部门 3团队")
    private Integer deptType;

    @Schema(description = "显示顺序")
    private Integer sort;

    @Schema(description = "负责人")
    private String leader;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "部门状态（0停用 1正常）")
    private String status;

    @JsonIgnore  //json忽略
    @Schema(description = "已删除（0否 1是）")
    @TableLogic(value = "0", delval = "1")  //逻辑删除注解
//    @TableField(fill = FieldFill.INSERT)  //新增的时候自动插入，MetaObjectHandlerConfig配置文件
    private Boolean deleted;

    @Schema(description = "创建者")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createAt;

    @Schema(description = "更新者")
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateAt;
    
    @Schema(hidden = true)
    @TableField(exist = false)
    List<Department> children;
}
