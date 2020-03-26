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
 * 后台用户表
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-03-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("st_user")
public class User extends Model<User> {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
      @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 用户帐号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 出生日期
     */
    private Date birth;

    /**
     * 性别: 0.女 1.男
     */
    private Boolean sex;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 用户是否锁定: 0.否 1.是
     */
    private String locked;

    /**
     * 过期日期
     */
    private Date expired;

    /**
     * 类型: 0.系统 1.普通
     */
    private String type;

    /**
     * 状态: 0.禁用 1.启动
     */
    private String status;

    /**
     * 登录时间
     */
    private Date lastLoginAt;

    /**
     * 用户登录IP地址
     */
    private String lastLoginIp;

    /**
     * 当天登录错误次数
     */
    private Integer errorCount;

    /**
     * 备注
     */
    private String remark;

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
    protected Serializable pkVal() {
        return this.id;
    }

}
