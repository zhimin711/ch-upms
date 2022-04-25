package com.ch.cloud.nacos.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.cloud.nacos.domain.NacosCluster;
import com.ch.cloud.nacos.mapper.NacosClusterMapper;
import com.ch.cloud.nacos.service.INacosClusterService;
import org.springframework.stereotype.Service;

/**
 * desc:
 *
 * @author zhimin
 * @date 2022/4/23 22:42
 */
@Service
public class INacosClusterServiceImpl extends ServiceImpl<NacosClusterMapper, NacosCluster> implements INacosClusterService {
}
