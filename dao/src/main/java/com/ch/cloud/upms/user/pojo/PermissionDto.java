package com.ch.cloud.upms.user.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * PermissionDto 扩展对象
 * 
 * @author 01370603
 * @since Fri Oct 18 17:54:42 CST 2019
 */
public class PermissionDto implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

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
     * 重定向地址
     */
    private String redirect;

    /**
     * 类型(1.目录 2.菜单页 3.按钮)
     */
    private String type;

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

    /**
     * 
     */
    private String parentName;

    /**
     * 描述
     */
    private String description;

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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getSort() {
        return this.sort;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getIsShow() {
        return this.isShow;
    }

    public void setIsSys(String isSys) {
        this.isSys = isSys;
    }

    public String getIsSys() {
        return this.isSys;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentName() {
        return this.parentName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getCreateAt() {
        return this.createAt;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Date getUpdateAt() {
        return this.updateAt;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }
}