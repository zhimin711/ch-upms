package com.ch.cloud.upms.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 后台权限表
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("st_permission")
public class Permission extends Model<Permission> {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 类型(1.目录 2.菜单页 3.按钮)
     */
    private String type;

    /**
     * 名称
     */
    private String name;

    /**
     * 代码
     */
    private String code;

    /**
     * 链接地址
     */
    private String url;

    /**
     * 转发地址
     */
    private String redirect;

    /**
     * 图标
     */
    private String icon;

    /**
     * 菜单顺序
     */
    private Integer sort;

    /**
     * 是否显示(0.否 1.是)
     */
    private String isShow;

    /**
     * 是否为系统权限(0.否 1.是)
     */
    private String isSys;

    /**
     * 上级菜单ID
     */
    private String parentId;

    private String parentName;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 状态: 0.禁用 1.启用
     */
    private String status;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateAt;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @TableField(exist = false)
    List<Permission> children;
}
