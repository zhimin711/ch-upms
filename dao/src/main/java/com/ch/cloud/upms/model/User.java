package com.ch.cloud.upms.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ch.cloud.upms.pojo.DepartmentDuty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@Schema(description = "后台用户表")
public class User {
    
    @Schema(description = "主键ID")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    
    @Schema(description = "用户编号")
    private String userId;
    
    @Schema(description = "用户帐号")
    private String username;
    
    @Schema(description = "密码")
    private String password;
    
    @Schema(description = "真实姓名")
    private String realName;
    
    @Schema(description = "出生日期")
    private Date birth;
    
    @Schema(description = "性别: 0.女 1.男")
    private Boolean sex;
    
    @Schema(description = "电子邮箱")
    private String email;
    
    @Schema(description = "手机号码")
    private String mobile;
    
    @Schema(description = "用户是否锁定: 0.否 1.是")
    private String locked;
    
    @Schema(description = "过期日期")
    private Date expired;
    
    @Schema(description = "类型: 0.系统 1.普通")
    private String type;
    
    @Schema(description = "状态: 0.禁用 1.启动")
    private String status;
    
    @Schema(hidden = true)
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Boolean deleted;
    
    @Schema(description = "登录时间")
    private Date lastLoginAt;
    
    @Schema(description = "用户登录IP地址")
    private String lastLoginIp;
    
    @Schema(description = "当天登录错误次数")
    private Integer errorCount;
    
    @Schema(description = "备注")
    private String remark;
    
    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createAt;
    
    @Schema(description = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    
    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateAt;
    
    @Schema(description = "更新人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
    
    //    @Schema(hidden = true)
    @TableField(exist = false)
    private String department;
    
    @Schema(hidden = true)
    @TableField(exist = false)
    private List<DepartmentDuty> dutyList;
    
    @Schema(description = "默认角色Id")
    private Long roleId;
    
    @Schema(description = "所属部门Id")
    private String departmentId;
    
    @Schema(description = "所属部门名称")
    private String departmentName;
    
    @Schema(description = "租户ID")
    private Long tenantId;
    
    @Schema(description = "租户名称")
    private String tenantName;
    
    @Schema(description = "所属部门职位ID")
    private Long positionId;
    
    @Schema(description = "所属部门职位名称")
    private String positionName;
}
