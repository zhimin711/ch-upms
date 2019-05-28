package com.ch.cloud.upms.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * desc:
 *
 * @author zhimin
 * @date 2018/12/21 11:11 PM
 */
@Data
public class UserInfo implements Serializable {

    private String username;

    private String token;

}
