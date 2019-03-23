package com.west.lake.blog.model;

/**
 * Redis缓存key定义
 *
 * @author futao
 * Created on 2019-03-22.
 */
public class RedisKeySet {
    /**
     * 注册邮件
     *
     * @param email 邮件
     * @return
     */
    public static String registerEmailKey(String email) {
        return "register:email:" + email;
    }
}
