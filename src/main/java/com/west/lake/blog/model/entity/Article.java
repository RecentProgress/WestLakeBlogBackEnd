package com.west.lake.blog.model.entity;

import lombok.*;

import javax.validation.constraints.Size;

/**
 * 文章
 *
 * @author futao
 * Created on 2019-03-23.
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Article extends BaseEntity {

    /**
     * 创建者
     */
    private User user;
    /**
     * 标题
     */
    @Size(min = 1, max = 30)
    private String title;
    /**
     * 简介
     */
    @Size(min = 1, max = 225)
    private String desc;
    /**
     * 内容
     */
    @Size(min = 1, max = 5000)
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
