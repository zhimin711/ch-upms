package com.ch.cloud.upms.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.ch.cloud.upms.pojo.DepartmentDuty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 后台用户表
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-03-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("st_user")
@ApiModel(value = "User对象", description = "后台用户表")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户编号")
    private String userId;

    @ApiModelProperty(value = "用户帐号")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "出生日期")
    private Date birth;

    @ApiModelProperty(value = "性别: 0.女 1.男")
    private Boolean sex;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "用户是否锁定: 0.否 1.是")
    private String locked;

    @ApiModelProperty(value = "过期日期")
    private Date expired;

    @ApiModelProperty(value = "类型: 0.系统 1.普通")
    private String type;

    @ApiModelProperty(value = "状态: 0.禁用 1.启动")
    private String status;

    @ApiModelProperty(hidden = true)
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty(value = "登录时间")
    private Date lastLoginAt;

    @ApiModelProperty(value = "用户登录IP地址")
    private String lastLoginIp;

    @ApiModelProperty(value = "当天登录错误次数")
    private Integer errorCount;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createAt;

    @ApiModelProperty(value = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateAt;

    @ApiModelProperty(value = "更新人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private String               department;
    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private List<DepartmentDuty> dutyList;

    @ApiModelProperty(value = "默认角色Id")
    private Long   roleId;
    @ApiModelProperty(value = "所属部门Id")
    private String departmentId;
    @ApiModelProperty(value = "所属部门名称")
    private String departmentName;
    @ApiModelProperty(value = "租户ID")
    private Long   tenantId;
    @ApiModelProperty(value = "租户名称")
    private String tenantName;
    @ApiModelProperty(value = "所属部门职位ID")
    private Long   positionId;
}
