package com.west.lake.blog.model;

/**
 * Redis缓存key定义
 *
 * @author futao
 * Created on 2019-03-22.
 */
public class RedisKeySet {

    public static class User {
        private static final String PREFIX = User.class.getSimpleName();

        /**
         * 注册邮件
         *
         * @param email 邮件
         * @return
         */
        public static String registerEmailKey(String email) {
            return PREFIX + ":register:email:" + email;
        }

        /**
         * 注册验证码key
         *
         * @param mobile
         * @return
         */
        public static String registerMessageKey(String mobile) {
            return PREFIX + ":register:mobile:" + mobile;
        }

        /**
         * 用户登录的sessionKey
         *
         * @param sessionKey
         * @return
         */
        public static String userSessionKey(String sessionKey) {
            return PREFIX + ":sessionKey:" + sessionKey;
        }
    }


    /**
     * Set key
     */
    public static class Set {
        /**
         * 登录用户idSet
         */
        public static final String LOGIN_USER_ID_SET = User.PREFIX + ":loginUserIdSet";
    }
}
