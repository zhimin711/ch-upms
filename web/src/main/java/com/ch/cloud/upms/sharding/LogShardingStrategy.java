package com.ch.cloud.upms.sharding;

import com.ch.utils.CommonUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Slf4j
public class LogShardingStrategy implements StandardShardingAlgorithm<String> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        List<String> list = Lists.newArrayList(availableTargetNames);
        log.debug("doSharding value: {}", shardingValue.getValue());
        if (CommonUtils.isEmpty(shardingValue.getValue())) {
            return list.get(1);
        }
        if (shardingValue.getValue().startsWith("LOGIN_")) {
            return list.get(0);
        }
        return list.get(1);
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<String> rangeShardingValue) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Properties getProps() {
        return null;
    }

    @Override
    public void init(Properties properties) {

    }
}
