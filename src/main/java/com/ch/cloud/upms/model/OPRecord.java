package com.ch.cloud.upms.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "lt_op_record")
public class OPRecord {
    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 请求地址
     */
    @Column(name = "URL")
    private String url;

    /**
     * 请求方法
     */
    @Column(name = "METHOD")
    private String method;

    /**
     * 请求权限
     */
    @Column(name = "AUTH_CODE")
    private String authCode;

    /**
     * 响应状态
     */
    @Column(name = "STATUS")
    private Integer status;

    /**
     * 请求用户
     */
    @Column(name = "OPERATOR")
    private String operator;

    /**
     * 请求时间
     */
    @Column(name = "REQUEST_TIME")
    @OrderBy("desc")
    private Long requestTime;

    /**
     * 响应时间
     */
    @Column(name = "RESPONSE_TIME")
    private Long responseTime;

    /**
     * 请求信息
     */
    @Column(name = "REQUEST")
    private String request;

    /**
     * 代理信息
     */
    @Column(name = "PROXY")
    private String proxy;

    /**
     * 响应信息
     */
    @Column(name = "RESPONSE")
    private String response;

    /**
     * 获取主键
     *
     * @return ID - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取请求地址
     *
     * @return URL - 请求地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置请求地址
     *
     * @param url 请求地址
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取请求方法
     *
     * @return METHOD - 请求方法
     */
    public String getMethod() {
        return method;
    }

    /**
     * 设置请求方法
     *
     * @param method 请求方法
     */
    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    /**
     * 获取请求权限
     *
     * @return AUTH_CODE - 请求权限
     */
    public String getAuthCode() {
        return authCode;
    }

    /**
     * 设置请求权限
     *
     * @param authCode 请求权限
     */
    public void setAuthCode(String authCode) {
        this.authCode = authCode == null ? null : authCode.trim();
    }

    /**
     * 获取响应状态
     *
     * @return STATUS - 响应状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置响应状态
     *
     * @param status 响应状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取请求用户
     *
     * @return OPERATOR - 请求用户
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 设置请求用户
     *
     * @param operator 请求用户
     */
    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    /**
     * 获取请求时间
     *
     * @return REQUEST_TIME - 请求时间
     */
    public Long getRequestTime() {
        return requestTime;
    }

    /**
     * 设置请求时间
     *
     * @param requestTime 请求时间
     */
    public void setRequestTime(Long requestTime) {
        this.requestTime = requestTime;
    }

    /**
     * 获取响应时间
     *
     * @return RESPONSE_TIME - 响应时间
     */
    public Long getResponseTime() {
        return responseTime;
    }

    /**
     * 设置响应时间
     *
     * @param responseTime 响应时间
     */
    public void setResponseTime(Long responseTime) {
        this.responseTime = responseTime;
    }

    /**
     * 获取请求信息
     *
     * @return REQUEST - 请求信息
     */
    public String getRequest() {
        return request;
    }

    /**
     * 设置请求信息
     *
     * @param request 请求信息
     */
    public void setRequest(String request) {
        this.request = request == null ? null : request.trim();
    }

    /**
     * 获取代理信息
     *
     * @return PROXY - 代理信息
     */
    public String getProxy() {
        return proxy;
    }

    /**
     * 设置代理信息
     *
     * @param proxy 代理信息
     */
    public void setProxy(String proxy) {
        this.proxy = proxy == null ? null : proxy.trim();
    }

    /**
     * 获取响应信息
     *
     * @return RESPONSE - 响应信息
     */
    public String getResponse() {
        return response;
    }

    /**
     * 设置响应信息
     *
     * @param response 响应信息
     */
    public void setResponse(String response) {
        this.response = response == null ? null : response.trim();
    }
}