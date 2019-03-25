package com.west.lake.blog.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 系统配置类
 *
 * @author futao
 * Created on 2019-03-22.
 */
@Component
@ConfigurationProperties(prefix = "west.lake.blog.system.config")
public class SystemConfig {
    /**
     * 允许的域名
     */
    public static final String[] ALLOW_ORIGINS = new String[]{"http://localhost:8080", "http://localhost:8081"};

    /**
     * Service层事物超时时间
     */
    public static final int SERVICE_TRANSACTION_TIMEOUT_SECOND = 2;

    /**
     * SESSIONKey
     */
    public static final String SESSION_KEY = "wlbsk";


    /**
     * 下一次啊检查跨域的间隔时间
     */
    private int originMaxAge;

    /**
     * 缓存过期时间
     */
    private int cacheExpiredSecond;

    /**
     * 是否开启swagger
     */
    private boolean isEnableSwagger;

    /**
     * Session有效期
     */
    private int sessionExpiredSecond;


    public int getOriginMaxAge() {
        return originMaxAge;
    }

    public void setOriginMaxAge(int originMaxAge) {
        this.originMaxAge = originMaxAge;
    }

    public int getCacheExpiredSecond() {
        return cacheExpiredSecond;
    }

    public void setCacheExpiredSecond(int cacheExpiredSecond) {
        this.cacheExpiredSecond = cacheExpiredSecond;
    }

    public boolean isEnableSwagger() {
        return isEnableSwagger;
    }

    public void setEnableSwagger(boolean enableSwagger) {
        isEnableSwagger = enableSwagger;
    }

    public int getSessionExpiredSecond() {
        return sessionExpiredSecond;
    }

    public void setSessionExpiredSecond(int sessionExpiredSecond) {
        this.sessionExpiredSecond = sessionExpiredSecond;
    }
}
