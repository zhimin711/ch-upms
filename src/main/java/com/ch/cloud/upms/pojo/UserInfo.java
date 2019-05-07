package com.ch.cloud.upms.pojo;

import java.io.Serializable;

/**
 * desc:
 *
 * @author zhimin
 * @date 2018/12/21 11:11 PM
 */
public class UserInfo implements Serializable {

    private String username;

    private String token;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
