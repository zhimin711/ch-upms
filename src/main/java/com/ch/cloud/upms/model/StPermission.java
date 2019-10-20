package com.ch.cloud.upms.model;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "st_permission")
public class StPermission {
    /**
     * 主键ID
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 代码
     */
    @Column(name = "CODE")
    private String code;

    /**
     * 链接地址
     */
    @Column(name = "URL")
    private String url;

    /**
     * 类型(1.目录 2.菜单页 3.按钮)
     */
    @Column(name = "TYPE")
    private String type;

    /**
     * 图标
     */
    @Column(name = "ICON")
    private String icon;

    /**
     * 菜单顺序
     */
    @Column(name = "SORT")
    private Integer sort;

    /**
     * 是否显示(0.否 1.是)
     */
    @Column(name = "IS_SHOW")
    private String isShow;

    /**
     * 是否为系统权限(0.否 1.是)
     */
    @Column(name = "IS_SYS")
    private String isSys;

    /**
     * 上级菜单ID
     */
    @Column(name = "PARENT_ID")
    private String parentId;

    @Column(name = "PARENT_NAME")
    private String parentName;

    /**
     * 描述
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * 状态: 0.禁用 1.启用
     */
    @Column(name = "STATUS")
    private String status;

    /**
     * 创建人
     */
    @Column(name = "CREATE_BY")
    private String createBy;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_AT")
    private Date createAt;

    /**
     * 更新人
     */
    @Column(name = "UPDATE_BY")
    private String updateBy;

    /**
     * 更新时间
     */
    @Column(name = "UPDATE_AT")
    private Date updateAt;

    /**
     * 获取主键ID
     *
     * @return ID - 主键ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取名称
     *
     * @return NAME - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取代码
     *
     * @return CODE - 代码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置代码
     *
     * @param code 代码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取链接地址
     *
     * @return URL - 链接地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置链接地址
     *
     * @param url 链接地址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取类型(1.目录 2.菜单页 3.按钮)
     *
     * @return TYPE - 类型(1.目录 2.菜单页 3.按钮)
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型(1.目录 2.菜单页 3.按钮)
     *
     * @param type 类型(1.目录 2.菜单页 3.按钮)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取图标
     *
     * @return ICON - 图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置图标
     *
     * @param icon 图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 获取菜单顺序
     *
     * @return SORT - 菜单顺序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置菜单顺序
     *
     * @param sort 菜单顺序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取是否显示(0.否 1.是)
     *
     * @return IS_SHOW - 是否显示(0.否 1.是)
     */
    public String getIsShow() {
        return isShow;
    }

    /**
     * 设置是否显示(0.否 1.是)
     *
     * @param isShow 是否显示(0.否 1.是)
     */
    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    /**
     * 获取是否为系统权限(0.否 1.是)
     *
     * @return IS_SYS - 是否为系统权限(0.否 1.是)
     */
    public String getIsSys() {
        return isSys;
    }

    /**
     * 设置是否为系统权限(0.否 1.是)
     *
     * @param isSys 是否为系统权限(0.否 1.是)
     */
    public void setIsSys(String isSys) {
        this.isSys = isSys;
    }

    /**
     * 获取上级菜单ID
     *
     * @return PARENT_ID - 上级菜单ID
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置上级菜单ID
     *
     * @param parentId 上级菜单ID
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * @return PARENT_NAME
     */
    public String getParentName() {
        return parentName;
    }

    /**
     * @param parentName
     */
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    /**
     * 获取描述
     *
     * @return DESCRIPTION - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取状态: 0.禁用 1.启用
     *
     * @return STATUS - 状态: 0.禁用 1.启用
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态: 0.禁用 1.启用
     *
     * @param status 状态: 0.禁用 1.启用
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取创建人
     *
     * @return CREATE_BY - 创建人
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建人
     *
     * @param createBy 创建人
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取创建时间
     *
     * @return CREATE_AT - 创建时间
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * 设置创建时间
     *
     * @param createAt 创建时间
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * 获取更新人
     *
     * @return UPDATE_BY - 更新人
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置更新人
     *
     * @param updateBy 更新人
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取更新时间
     *
     * @return UPDATE_AT - 更新时间
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * 设置更新时间
     *
     * @param updateAt 更新时间
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }


    @Transient
    private List<StPermission> children;

    public List<StPermission> getChildren() {
        return children;
    }

    public void setChildren(List<StPermission> children) {
        this.children = children;
    }
}