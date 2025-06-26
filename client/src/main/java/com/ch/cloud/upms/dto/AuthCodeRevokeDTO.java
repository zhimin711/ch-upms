package com.ch.cloud.upms.dto;

import lombok.Data;

@Data
public class AuthCodeRevokeDTO {
    /** 授权码 */
    private String code;
} 