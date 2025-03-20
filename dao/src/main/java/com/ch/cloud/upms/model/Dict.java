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
 * 数据字典表
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-07-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("mt_dict")
@Schema(description = "数据字典表")
public class Dict extends Model<Dict> {

    private static final long serialVersionUID = 1L;

    @Schema(description = "字典主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "上级id")
    private Long pid;

    @Schema(description = "字典名称")
    private String name;

    @Schema(description = "字典类型")
    private String code;

    @Schema(description = "显示顺序")
    private Integer sort;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "状态（0停用 1正常）")
    private String status;

    @Schema(description = "删除标志（0代表存在 1代表删除）")
//    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    @JsonIgnore
    private Boolean deleted;

    @Schema(description = "创建者")
    private String createBy;

    @Schema(description = "创建时间")
    private Date createAt;

    @Schema(description = "更新者")
    private String updateBy;

    @Schema(description = "更新时间")
    private Date updateAt;

    @TableField(exist = false)
    private List<Dict> children;
}
