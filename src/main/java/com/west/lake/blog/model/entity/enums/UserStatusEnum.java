package com.west.lake.blog.model.entity.enums;

/**
 * 用户状态
 *
 * @author futao
 * Created on 2018-12-11.
 */
public enum UserStatusEnum {
    /**
     * 0=预注册
     */
    PRE_REGISTER(0, "预注册"),
    /**
     * 1=正常
     */
    NORMAL(1, "正常"),
    /**
     * -1=禁用，禁止登陆
     */
    DISABLE(-1, "禁用");

    /**
     * 状态码
     */
    private int code;
    /**
     * 描述
     */
    private String description;

    UserStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return String.valueOf(this.code);
    }
}
