package com.ch.cloud.upms.sharding;

import com.ch.utils.CommonUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;
import java.util.List;

@Slf4j
public class LogShardingStrategy implements PreciseShardingAlgorithm<String> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        List<String> list = Lists.newArrayList(availableTargetNames);
        log.info("doSharding value: {}", shardingValue.getValue());
        if (CommonUtils.isEmpty(shardingValue.getValue())) {
            return list.get(1);
        }
        if (shardingValue.getValue().startsWith("LOGIN_")) {
            return list.get(0);
        }
        return list.get(1);
    }
}
