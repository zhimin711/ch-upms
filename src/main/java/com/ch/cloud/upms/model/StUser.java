package com.ch.cloud.upms.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "st_user")
public class StUser implements Serializable {
    /**
     * 主键ID
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户编号
     */
    @Column(name = "USER_ID")
    private String userId;

    /**
     * 用户帐号
     */
    @Column(name = "USERNAME")
    private String username;

    /**
     * 密码
     */
    @Column(name = "PASSWORD")
    private String password;

    /**
     * 真实姓名
     */
    @Column(name = "REAL_NAME")
    private String realName;

    /**
     * 出生日期
     */
    @Column(name = "BIRTH")
    private Date birth;

    /**
     * 性别: 0.女 1.男
     */
    @Column(name = "SEX")
    private Boolean sex;

    /**
     * 电子邮箱
     */
    @Column(name = "EMAIL")
    private String email;

    /**
     * 手机号码
     */
    @Column(name = "MOBILE")
    private String mobile;

    /**
     * 用户是否锁定: 0.否 1.是
     */
    @Column(name = "LOCKED")
    private String locked;

    /**
     * 过期日期
     */
    @Column(name = "EXPIRED")
    private Date expired;

    /**
     * 类型: 0.系统 1.普通
     */
    @Column(name = "TYPE")
    private String type;

    /**
     * 状态: 0.禁用 1.启动
     */
    @Column(name = "STATUS")
    private String status;

    /**
     * 登录时间
     */
    @Column(name = "LAST_LOGIN_AT")
    private Date lastLoginAt;

    /**
     * 用户登录IP地址
     */
    @Column(name = "LAST_LOGIN_IP")
    private String lastLoginIp;

    /**
     * 当天登录错误次数
     */
    @Column(name = "ERROR_COUNT")
    private Integer errorCount;

    /**
     * 备注
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_AT")
    private Date createAt;

    /**
     * 创建人
     */
    @Column(name = "CREATE_BY")
    private String createBy;

    /**
     * 更新时间
     */
    @Column(name = "UPDATE_AT")
    private Date updateAt;

    /**
     * 更新人
     */
    @Column(name = "UPDATE_BY")
    private String updateBy;

    private static final long serialVersionUID = 1L;

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
     * 获取用户编号
     *
     * @return USER_ID - 用户编号
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户编号
     *
     * @param userId 用户编号
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取用户帐号
     *
     * @return USERNAME - 用户帐号
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户帐号
     *
     * @param username 用户帐号
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码
     *
     * @return PASSWORD - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取真实姓名
     *
     * @return REAL_NAME - 真实姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 设置真实姓名
     *
     * @param realName 真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * 获取出生日期
     *
     * @return BIRTH - 出生日期
     */
    public Date getBirth() {
        return birth;
    }

    /**
     * 设置出生日期
     *
     * @param birth 出生日期
     */
    public void setBirth(Date birth) {
        this.birth = birth;
    }

    /**
     * 获取性别: 0.女 1.男
     *
     * @return SEX - 性别: 0.女 1.男
     */
    public Boolean getSex() {
        return sex;
    }

    /**
     * 设置性别: 0.女 1.男
     *
     * @param sex 性别: 0.女 1.男
     */
    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    /**
     * 获取电子邮箱
     *
     * @return EMAIL - 电子邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置电子邮箱
     *
     * @param email 电子邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取手机号码
     *
     * @return MOBILE - 手机号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号码
     *
     * @param mobile 手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取用户是否锁定: 0.否 1.是
     *
     * @return LOCKED - 用户是否锁定: 0.否 1.是
     */
    public String getLocked() {
        return locked;
    }

    /**
     * 设置用户是否锁定: 0.否 1.是
     *
     * @param locked 用户是否锁定: 0.否 1.是
     */
    public void setLocked(String locked) {
        this.locked = locked;
    }

    /**
     * 获取过期日期
     *
     * @return EXPIRED - 过期日期
     */
    public Date getExpired() {
        return expired;
    }

    /**
     * 设置过期日期
     *
     * @param expired 过期日期
     */
    public void setExpired(Date expired) {
        this.expired = expired;
    }

    /**
     * 获取类型: 0.系统 1.普通
     *
     * @return TYPE - 类型: 0.系统 1.普通
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型: 0.系统 1.普通
     *
     * @param type 类型: 0.系统 1.普通
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取状态: 0.禁用 1.启动
     *
     * @return STATUS - 状态: 0.禁用 1.启动
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态: 0.禁用 1.启动
     *
     * @param status 状态: 0.禁用 1.启动
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取登录时间
     *
     * @return LAST_LOGIN_AT - 登录时间
     */
    public Date getLastLoginAt() {
        return lastLoginAt;
    }

    /**
     * 设置登录时间
     *
     * @param lastLoginAt 登录时间
     */
    public void setLastLoginAt(Date lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    /**
     * 获取用户登录IP地址
     *
     * @return LAST_LOGIN_IP - 用户登录IP地址
     */
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    /**
     * 设置用户登录IP地址
     *
     * @param lastLoginIp 用户登录IP地址
     */
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    /**
     * 获取当天登录错误次数
     *
     * @return ERROR_COUNT - 当天登录错误次数
     */
    public Integer getErrorCount() {
        return errorCount;
    }

    /**
     * 设置当天登录错误次数
     *
     * @param errorCount 当天登录错误次数
     */
    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    /**
     * 获取备注
     *
     * @return REMARK - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", realName=").append(realName);
        sb.append(", birth=").append(birth);
        sb.append(", sex=").append(sex);
        sb.append(", email=").append(email);
        sb.append(", mobile=").append(mobile);
        sb.append(", locked=").append(locked);
        sb.append(", expired=").append(expired);
        sb.append(", type=").append(type);
        sb.append(", status=").append(status);
        sb.append(", lastLoginAt=").append(lastLoginAt);
        sb.append(", lastLoginIp=").append(lastLoginIp);
        sb.append(", errorCount=").append(errorCount);
        sb.append(", remark=").append(remark);
        sb.append(", createAt=").append(createAt);
        sb.append(", createBy=").append(createBy);
        sb.append(", updateAt=").append(updateAt);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}