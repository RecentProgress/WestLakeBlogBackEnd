package com.west.lake.blog.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
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
     * 邮件主题前缀
     */
    public static final String EMAIL_SUBJECT_PREFIX = SystemConfig.SITE_NAME + " | ";

    /**
     * 注册短信与邮件验证码有效期分钟数
     */
    public static final int REGISTER_MESSAGE_AND_EMAIL_EXPIRED_MINUTES = 5;
    /**
     * 站点名称
     */
    public static final String SITE_NAME = "WestLakeBlog";

    /**
     * 图灵机器人
     */
    public static final String TULING = "http://openapi.tuling123.com/openapi/api/v2";

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


    /**
     * 腾讯短信配置
     */
    @Component
    @ConfigurationProperties(prefix = "tencent.message")
    public static class TencentMessage {
        /**
         * 短信应用SDK AppID
         */
        private int appId;
        /**
         * 短信应用SDK AppKey
         */
        private String appKey;
        /**
         * 签名 签名参数使用的是`签名内容`，而不是`签名ID`。这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台申请。
         */
        private String smsSign;

        public int getAppId() {
            return appId;
        }

        public void setAppId(int appId) {
            this.appId = appId;
        }

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getSmsSign() {
            return smsSign;
        }

        public void setSmsSign(String smsSign) {
            this.smsSign = smsSign;
        }
    }

    public static class RequestConst {
        public static final int CONNECTION_REQUEST_TIMEOUT = 5000;
        public static final int CONNECT_TIMEOUT = 5000;
        public static final int SOCKET_TIMEOUT = 5000;
    }

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
