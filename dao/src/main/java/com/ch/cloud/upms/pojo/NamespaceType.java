package com.ch.cloud.upms.pojo;

/**
 * desc:命名空间类型
 *
 * @author zhimin
 * @since 2022/4/26 00:11
 */
public enum NamespaceType {

    NACOS("1"), ROCKET_MQ("2"), KAFKA("3");

    private final String code;

    NamespaceType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
