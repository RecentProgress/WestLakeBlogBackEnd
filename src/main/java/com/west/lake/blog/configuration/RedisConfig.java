package com.west.lake.blog.configuration;

import com.alibaba.fastjson.parser.ParserConfig;
import com.west.lake.blog.model.SystemConfig;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * Redis配置类
 *
 * @author futao
 * Created on 2019-03-22.
 */
@Configuration
@EnableCaching
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


    /**
     * 自定义序列化
     * 这里的FastJsonRedisSerializer引用的自己定义的
     * 不自定义的话redisTemplate会乱码
     */
//    @Bean("redisTemplate")
    @Bean
    public <T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory factory) {
        //redis反序列化 开启fastJson反序列化的autoType
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer<T>();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setDefaultSerializer(fastJsonRedisSerializer);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        return redisTemplate;
    }


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
