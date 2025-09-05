package com.ch.cloud.upms.sharding;

import com.ch.core.data.rule.Week;
import com.ch.e.Assert;
import com.ch.e.PubError;
import com.ch.utils.DateUtils;
import com.google.common.collect.Range;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * <p>
 * desc: LogComplexShardingStrategy
 * </p>
 *
 * @author zhimin
 * @since 2023/5/12
 */
public class LongDateMonthShardingStrategy implements StandardShardingAlgorithm<Long> {
    
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        Long timestamp = shardingValue.getValue();
        Date date = DateUtils.parseTimestamp(timestamp);
        if (date == null) {
            return shardingValue.getLogicTableName();
        }
        String tableSuffix = DateUtils.format(date, DateUtils.Pattern.DATE_MONTH_SHORT);
        String tableName = shardingValue.getLogicTableName() + "_" + tableSuffix;
        if (availableTargetNames.contains(tableName)) {
            return tableName;
        }
        return shardingValue.getLogicTableName();
    }
    
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames,
            RangeShardingValue<Long> shardingValue) {
        // 得到每个分片健对应的值
        Range<Long> range = shardingValue.getValueRange();
        
        Set<String> tableNames = new HashSet<>();
        if (!range.hasLowerBound()) {
            // <= 只查最大的表
            Date date = DateUtils.parseTimestamp(range.upperEndpoint());
            String tableSuffix = DateUtils.format(date, DateUtils.Pattern.DATE_MONTH_SHORT);
            String tableName = shardingValue.getLogicTableName() + "_" + tableSuffix;
            if (availableTargetNames.contains(tableName)) {
                tableNames.add(tableName);
            }
        } else if (!range.hasUpperBound()) {
            // >= 只查最小的表
            Date date = DateUtils.parseTimestamp(range.lowerEndpoint());
            String tableSuffix = DateUtils.format(date, DateUtils.Pattern.DATE_MONTH_SHORT);
            String tableName = shardingValue.getLogicTableName() + "_" + tableSuffix;
            if (availableTargetNames.contains(tableName)) {
                tableNames.add(tableName);
            }
        } else {
            // 区间查询,只支持最多查询两个表
            Date start = DateUtils.parseTimestamp(range.lowerEndpoint());
            Date end = DateUtils.parseTimestamp(range.upperEndpoint());
            List<Date> dates = DateUtils.workDate(start, end, Week.ALL);
            Assert.isFalse(dates.size() > 180, PubError.OUT_OF_LIMIT, "时间区间查询最多支持180天");
            dates.forEach(e -> {
                String tableSuffix = DateUtils.format(e, DateUtils.Pattern.DATE_MONTH_SHORT);
                String tableName = shardingValue.getLogicTableName() + "_" + tableSuffix;
                if (availableTargetNames.contains(tableName)) {
                    tableNames.add(tableName);
                }
            });
        }
        return tableNames;
    }
    
    @Override
    public Properties getProps() {
        return null;
    }
    
    @Override
    public void init(Properties props) {
    
    }
    
    @Override
    public String getType() {
        return "TIMESTAMP_DATE_MONTH_ALGORITHM";
    }
}
