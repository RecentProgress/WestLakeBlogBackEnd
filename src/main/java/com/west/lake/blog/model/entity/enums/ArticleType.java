package com.west.lake.blog.model.entity.enums;

import com.lazy.validator.annotations.enums.EnumDesc;
import com.lazy.validator.annotations.enums.IEnum;
import lombok.AllArgsConstructor;

/**
 * 文章类型
 *
 * @author futao
 * Created on 2019-04-08.
 */
@EnumDesc("文章类型")
@AllArgsConstructor
public enum ArticleType implements IEnum<Integer> {

    /**
     * 1=原创
     */
    ORIGINAL(1, "原创"),

    /**
     * 从简书同步
     */
    SYNC_JIANSHU(11, "从简书同步"),

    /**
     * 2=转载
     */
    REPRINT(2, "转载");


    /**
     * 类型
     */
    private int type;
    /**
     * 描述
     */
    private String desc;

    @Override
    public Integer getType() {
        return type;
    }

    @Override
    public String getDescription() {
        return desc;
    }
}
