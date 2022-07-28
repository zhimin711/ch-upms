package com.ch.cloud.upms.conf;

import com.ch.utils.CommonUtils;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * <p>
 * desc: CacheConfig
 * </p>
 *
 * @author Zhimin.Ma
 * @since 2022/7/28
 */
@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

    @Bean
    @Primary
    public RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties) {
        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();

        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        if (CommonUtils.isNotEmpty(cacheProperties.getRedis().getKeyPrefix())) {
            configuration = configuration.prefixCacheNameWith(cacheProperties.getRedis().getKeyPrefix());
            configuration.usePrefix();
        }
        Duration ttl = Duration.ofDays(30);
        if (cacheProperties.getRedis().getTimeToLive() != null) {
            ttl = cacheProperties.getRedis().getTimeToLive();
        }
        return configuration.serializeValuesWith
                        (RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                .entryTtl(ttl);
//        return configuration;
    }

}
