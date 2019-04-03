package com.west.lake.blog.model.entity;

import com.west.lake.blog.foundation.exception.ErrorMessage;
import lombok.*;

import javax.validation.constraints.Size;

/**
 * 评论
 *
 * @author futao
 * Created on 2019-03-28.
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseEntity {
    /**
     * 评论的文章
     */
    private Article article;

    /**
     * 发表评论的用户
     */
    private User user;

    /**
     * 评论的内容
     */
    @Size(min = 1, max = 500, message = ErrorMessage.LogicErrorMessage.REVIEW_CONTENT_MIN_MAX)
    private String content;

    /**
     * 对于评论的评论(评论的回复)
     */
    private Review parentReview;

}
