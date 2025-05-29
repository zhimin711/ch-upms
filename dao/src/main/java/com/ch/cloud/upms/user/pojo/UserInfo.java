package com.ch.cloud.upms.user.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * desc:用户信息
 *
 * @author zhimin
 * @since 2018/12/21 11:11 PM
 */
@Data
public class UserInfo implements Serializable {

    /**
     * 账号
     */
    private String userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 姓名(实名)
     */
    private String realName;
    /**
     * 部门
     */
    private String department;
    /**
     * 职位
     */
    private String duty;

}
