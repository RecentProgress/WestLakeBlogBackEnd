package com.west.lake.blog.model.entity;

import lombok.Data;

/**
 * 文章
 *
 * @author futao
 * Created on 2019-03-23.
 */
@Data
public class Article extends BaseEntity {

    /**
     * 创建者
     */
    private User user;
    /**
     * 标题
     */
    private String title;
    /**
     * 简介
     */
    private String desc;
    /**
     * 内容
     */
    private String content;
    /**
     * 访问次数
     */
    private int visitTimes;
    //TODO("标签")

    /**
     * 点赞次数
     */
    private int likeTimes;

}
