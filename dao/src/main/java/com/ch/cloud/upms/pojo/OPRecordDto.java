package com.ch.cloud.upms.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * OPRecordDto 扩展对象
 * 
 * @author zhimi
 * @date Sat Mar 21 16:54:49 CST 2020
 */
@Data
public class OPRecordDto implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求权限
     */
    private String authCode;

    /**
     * 响应状态
     */
    private Integer status;

    /**
     * 请求用户
     */
    private String operator;

    /**
     * 请求时间
     */
    private Date requestTime;

    /**
     * 响应时间
     */
    private Date responseTime;

    /**
     * 请求信息
     */
    private String request;

    /**
     * 代理信息
     */
    private String proxy;

    /**
     * 响应信息
     */
    private String response;
}