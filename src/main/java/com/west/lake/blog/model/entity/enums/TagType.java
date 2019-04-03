package com.west.lake.blog.model.entity.enums;

import com.west.lake.blog.model.entity.enums.face.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 标签类型
 *
 * @author futao
 * Created on 2019-04-03.
 */
@AllArgsConstructor
@Getter
public enum TagType implements IEnum {

    /**
     * 1=系统级标签
     */
    SYSTEM(1, "系统级"),

    /**
     * 2=普通
     */
    NORMAL(2, "普通");
    private int type;
    private String desc;

    @Override
    public String getStatus() {
        return String.valueOf(type);
    }}
