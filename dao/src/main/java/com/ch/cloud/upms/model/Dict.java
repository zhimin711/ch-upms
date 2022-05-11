package com.ch.cloud.upms.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="Dict对象", description="数据字典表")
public class Dict extends Model<Dict> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "上级id")
    private Long pid;

    @ApiModelProperty(value = "字典名称")
    private String name;

    @ApiModelProperty(value = "字典类型")
    private String code;

    @ApiModelProperty(value = "显示顺序")
    private Integer sort;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态（0停用 1正常）")
    private String status;

    @ApiModelProperty(value = "删除标志（0代表存在 1代表删除）")
//    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    @JsonIgnore
    private Boolean deleted;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateAt;

    @TableField(exist = false)
    private List<Dict> children;
}
