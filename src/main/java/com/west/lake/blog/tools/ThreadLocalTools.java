package com.west.lake.blog.tools;

/**
 * ThreadLocal工具类
 * TODO("原理")
 *
 * @author futao
 * Created on 2019-03-25.
 */
public class ThreadLocalTools {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    /**
     * 设置
     *
     * @param userId
     */
    public static void set(String userId) {
        threadLocal.set(userId);
    }

    /**
     * 获取
     */
    public static String get() {
        return threadLocal.get();
    }


    /**
     * 回收
     */
    public static void remove() {
        threadLocal.remove();
    }
}
