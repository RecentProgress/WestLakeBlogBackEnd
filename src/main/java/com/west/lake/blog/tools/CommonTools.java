package com.west.lake.blog.tools;

import java.util.UUID;

/**
 * 通用帮助类
 *
 * @author futao
 * Created on 2019-03-20.
 */
public class CommonTools {

    /**
     * 获取32位的UUID
     *
     * @return UUID
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
