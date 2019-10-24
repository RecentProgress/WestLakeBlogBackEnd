package com.west.lake.blog.configuration;

import com.west.lake.blog.model.SystemConfig;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import javax.annotation.Resource;

/**
 * Redis配置类
 *
 * @author futao
 * Created on 2019-03-22.
 */
@Configuration("rc")
public class RedisConfig extends CachingConfigurerSupport {

    @Resource
    private SystemConfig systemConfig;


    @Resource
    private RedisConnectionFactory connectionFactory;

    public RedisConfig() {
        super();
    }


    /**
     * 缓存管理器
     *
     * @return
     */
//    @Bean
//    @Override
//    public CacheManager cacheManager() {
//        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofSeconds(systemConfig.getCacheExpiredSecond()));
////                .disableCachingNullValues();
//        return RedisCacheManager.builder(RedisCacheWriter.lockingRedisCacheWriter(connectionFactory))
//                .cacheDefaults(configuration)
//                .build();
//    }


//    @Bean
//    @Override
//    public CacheResolver cacheResolver() {
//        return super.cacheResolver();
//    }

    /**
     * key生成规则
     *
     * @return key
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getSimpleName())
                    .append(method.getName());
            for (Object param : params) {
                sb.append(param);
            }
            return sb.toString();
        };
    }

//    @Override
//    public CacheErrorHandler errorHandler() {
//        return super.errorHandler();
//    }
}
