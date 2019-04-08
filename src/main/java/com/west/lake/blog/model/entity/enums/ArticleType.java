package com.west.lake.blog.model.entity.enums;

import com.west.lake.blog.model.entity.enums.face.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文章类型
 *
 * @author futao
 * Created on 2019-04-08.
 */

@Getter
@AllArgsConstructor
public enum ArticleType implements IEnum {

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
    public String getStatus() {
        return String.valueOf(type);
    }
}
