/*
package com.ch.cloud.upms.model;

import com.ch.t.ConditionType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "st_menu")
public class StMenu implements Serializable {
    */
/**
     * 主键ID
     *//*

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    */
/**
     * 上级菜单ID
     *//*

    @Column(name = "PID")
    private String pid;

    */
/**
     * 菜单名称
     *//*

    @Column(name = "NAME")
    private String name;

    */
/**
     * 菜单代码
     *//*

    @Column(name = "CODE")
    private String code;

    */
/**
     * 图标代码
     *//*

    @Column(name = "ICON")
    private String icon;

    */
/**
     * 地址
     *//*

    @Column(name = "URL")
    private String url;

    */
/**
     * 菜单顺序
     *//*

    @Column(name = "SORT")
    private Integer sort;

    */
/**
     * 类型: 1.系统 2.自定义)
     *//*

    @Column(name = "TYPE")
    private String type;

    */
/**
     * 描述
     *//*

    @Column(name = "DESCRIPTION")
    private String description;

    */
/**
     * 状态: 0.禁用 1.启动
     *//*

    @Column(name = "STATUS")
    private String status;

    */
/**
     * 创建人
     *//*

    @Column(name = "CREATE_BY")
    private String createBy;

    */
/**
     * 创建时间
     *//*

    @Column(name = "CREATE_AT")
    private Date createAt;

    */
/**
     * 更新人
     *//*

    @Column(name = "UPDATE_BY")
    private String updateBy;

    */
/**
     * 更新时间
     *//*

    @Column(name = "UPDATE_AT")
    private Date updateAt;

    private static final long serialVersionUID = 1L;

    */
/**
     * 获取主键ID
     *
     * @return ID - 主键ID
     *//*

    public Long getId() {
        return id;
    }

    */
/**
     * 设置主键ID
     *
     * @param id 主键ID
     *//*

    public void setId(Long id) {
        this.id = id;
    }

    */
/**
     * 获取上级菜单ID
     *
     * @return PID - 上级菜单ID
     *//*

    public String getPid() {
        return pid;
    }

    */
/**
     * 设置上级菜单ID
     *
     * @param pid 上级菜单ID
     *//*

    public void setPid(String pid) {
        this.pid = pid;
    }

    */
/**
     * 获取菜单名称
     *
     * @return NAME - 菜单名称
     *//*

    public String getName() {
        return name;
    }

    */
/**
     * 设置菜单名称
     *
     * @param name 菜单名称
     *//*

    public void setName(String name) {
        this.name = name;
    }

    */
/**
     * 获取菜单代码
     *
     * @return CODE - 菜单代码
     *//*

    public String getCode() {
        return code;
    }

    */
/**
     * 设置菜单代码
     *
     * @param code 菜单代码
     *//*

    public void setCode(String code) {
        this.code = code;
    }

    */
/**
     * 获取图标代码
     *
     * @return ICON - 图标代码
     *//*

    public String getIcon() {
        return icon;
    }

    */
/**
     * 设置图标代码
     *
     * @param icon 图标代码
     *//*

    public void setIcon(String icon) {
        this.icon = icon;
    }

    */
/**
     * 获取地址
     *
     * @return URL - 地址
     *//*

    public String getUrl() {
        return url;
    }

    */
/**
     * 设置地址
     *
     * @param url 地址
     *//*

    public void setUrl(String url) {
        this.url = url;
    }

    */
/**
     * 获取菜单顺序
     *
     * @return SORT - 菜单顺序
     *//*

    public Integer getSort() {
        return sort;
    }

    */
/**
     * 设置菜单顺序
     *
     * @param sort 菜单顺序
     *//*

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    */
/**
     * 获取类型: 1.系统 2.自定义)
     *
     * @return TYPE - 类型: 1.系统 2.自定义)
     *//*

    public String getType() {
        return type;
    }

    */
/**
     * 设置类型: 1.系统 2.自定义)
     *
     * @param type 类型: 1.系统 2.自定义)
     *//*

    public void setType(String type) {
        this.type = type;
    }

    */
/**
     * 获取描述
     *
     * @return DESCRIPTION - 描述
     *//*

    public String getDescription() {
        return description;
    }

    */
/**
     * 设置描述
     *
     * @param description 描述
     *//*

    public void setDescription(String description) {
        this.description = description;
    }

    */
/**
     * 获取状态: 0.禁用 1.启动
     *
     * @return STATUS - 状态: 0.禁用 1.启动
     *//*

    public String getStatus() {
        return status;
    }

    */
/**
     * 设置状态: 0.禁用 1.启动
     *
     * @param status 状态: 0.禁用 1.启动
     *//*

    public void setStatus(String status) {
        this.status = status;
    }

    */
/**
     * 获取创建人
     *
     * @return CREATE_BY - 创建人
     *//*

    public String getCreateBy() {
        return createBy;
    }

    */
/**
     * 设置创建人
     *
     * @param createBy 创建人
     *//*

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    */
/**
     * 获取创建时间
     *
     * @return CREATE_AT - 创建时间
     *//*

    public Date getCreateAt() {
        return createAt;
    }

    */
/**
     * 设置创建时间
     *
     * @param createAt 创建时间
     *//*

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    */
/**
     * 获取更新人
     *
     * @return UPDATE_BY - 更新人
     *//*

    public String getUpdateBy() {
        return updateBy;
    }

    */
/**
     * 设置更新人
     *
     * @param updateBy 更新人
     *//*

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    */
/**
     * 获取更新时间
     *
     * @return UPDATE_AT - 更新时间
     *//*

    public Date getUpdateAt() {
        return updateAt;
    }

    */
/**
     * 设置更新时间
     *
     * @param updateAt 更新时间
     *//*

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    @Transient
    private List<StMenu> children;

    @Transient
    public ConditionType pidCond = ConditionType.EQUAL_TO;

    public List<StMenu> getChildren() {
        return children;
    }

    public void setChildren(List<StMenu> children) {
        this.children = children;
    }

}*/
