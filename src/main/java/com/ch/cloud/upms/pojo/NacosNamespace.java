package com.ch.cloud.upms.pojo;

import lombok.Data;

/**
 * <p>
 * desc:
 * </p>
 *
 * @author zhimin.ma
 * @since 2021/9/28
 */
@Data
public class NacosNamespace {

    /**
     * 当前配置数
     */
    private Integer configCount;
    private String  namespace;
    private String  namespaceShowName;
    /**
     * 配额
     */
    private Integer quota;
    /**
     * the enum of namespace.
     * 0 : Global configuration， 1 : Default private namespace ，2 : Custom namespace.
     */
    private Integer type;
}
