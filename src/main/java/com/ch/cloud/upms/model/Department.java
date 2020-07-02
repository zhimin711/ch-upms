package com.ch.cloud.upms.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

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
@ApiModel(value="Department对象", description="部门表")
public class Department extends Model<Department> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "上级部门id")
    private String pid;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "显示顺序")
    private Integer sort;

    @ApiModelProperty(value = "负责人")
    private String leader;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "部门状态（0正常 1停用）")
    private String status;

    @ApiModelProperty(value = "删除标志（0代表存在 1代表删除）")
    @TableLogic  //逻辑删除注解
    @JsonIgnore  //忽略
    @TableField(fill = FieldFill.INSERT)  //新增的时候自动插入，MetaObjectHandlerConfig配置文件
    private Boolean delFlag;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateAt;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
