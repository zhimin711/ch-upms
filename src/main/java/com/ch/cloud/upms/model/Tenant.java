package com.ch.cloud.upms.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 业务-租户表
 * </p>
 *
 * @author zhimin.ma
 * @since 2021-09-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bt_tenant")
public class Tenant extends Model<Tenant> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 租户部门ID
     */
    private String departmentId;

    /**
     * 租户部门名称
     */
    private String departmentName;

    /**
     * 租户别名
     */
    private String name;

    /**
     * 负责人
     */
    private String manager;

    /**
     * 排序
     */
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


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
