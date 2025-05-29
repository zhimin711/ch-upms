package com.ch.cloud.upms.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.OrderBy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 业务-项目表
 * </p>
 *
 * @author zhimin.ma
 * @since 2021-09-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bt_project")
public class Project extends Model<Project> {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.AUTO)
    @OrderBy(sort = 3, asc = true)
    private Long id;
    
    /**
     * 租户ID
     */
    @NotNull
    @OrderBy(sort = 1, asc = true)
    private Long tenantId;
    
    /**
     * 租户名称
     */
    private String tenantName;
    
    /**
     * 名称
     */
    private String name;
    
    /**
     * 代码
     */
    @NotNull
    private String code;
    
    /**
     * 所属部门(控制在两层)
     */
    @NotNull
    private String department;
    
    /**
     * 部门名称
     */
    private String departmentName;
    
    /**
     * 负责人
     */
    private String manager;
    
    /**
     * 排序
     */
    @OrderBy(sort = 2, asc = true)
    private Integer sort;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 状态：0.失效 1.生效
     */
    private String status;
    
    /**
     * 逻辑删除：0.否 1.是
     */
    private Boolean deleted;
    
    /**
     * 创建时间
     */
    private Date createAt;
    
    /**
     * 创建人
     */
    private String createBy;
    
    /**
     * 更新时间
     */
    private Date updateAt;
    
    /**
     * 更新人
     */
    private String updateBy;
    
    @TableField(exist = false)
    private List<String> devUserIds;
    
    @TableField(exist = false)
    private List<String> testUserIds;
}
