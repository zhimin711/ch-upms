package com.ch.cloud.client.dto;

import lombok.Data;

/**
 * decs:
 *
 * @author 01370603
 * @date 2019/5/28
 */
@Data
public class UserDto {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
}
