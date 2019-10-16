package com.west.lake.blog.model.entity.enums;

import com.lazy.validator.annotations.enums.EnumDesc;
import com.lazy.validator.annotations.enums.IEnum;

/**
 * 用户性别
 *
 * @author futao
 * Created on 2018-12-11.
 */
@EnumDesc("用户性别")
public enum UserSexEnum implements IEnum<Integer> {
    /**
     * 0=未知
     */
    UNKNOWN(0, "未知"),
    /**
     * 1=男
     */
    MEN(1, "男"),
    /**
     * 2=女
     */
    WOMEN(2, "女");

    private int code;
    private String description;

    UserSexEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public Integer getType() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}

