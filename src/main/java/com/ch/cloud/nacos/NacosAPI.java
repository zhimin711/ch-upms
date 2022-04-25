package com.ch.cloud.nacos;

/**
 * desc:
 *
 * @author zhimin
 * @date 2022/4/23 16:04
 */
public interface NacosAPI {

    String CLUSTER = "/nacos/v1/core/cluster/nodes";
    String NAMESPACES = "/nacos/v1/console/namespaces";
    String CONFIGS = "/nacos/v1/cs/configs";
}
