package com.west.lake.blog.dao;

import com.west.lake.blog.model.entity.Review;

import java.util.List;

/**
 * 评论Dao层
 *
 * @author futao
 * Created on 2019-03-28.
 */
public interface ReviewDao {

    /**
     * 新增评论
     *
     * @param review 评论
     * @return 评论
     */
    void add(Review review);

    /**
     * 删除评论
     *
     * @param id 要删除的评论主键
     * @return 评论
     */
    void delete(String id);


    /**
     * 修改评论
     *
     * @param review 要修改的评论
     * @return 评论
     */
    void update(Review review);

    /**
     * 查询评论列表
     *
     * @return 评论列表
     */
    List<Review> list();

    /**
     * 获取评论详情
     *
     * @param id 要查询的评论主键
     * @return 评论
     */
    Review byId(String id);

    /**
     * 通过articleId列出一级评论
     *
     * @param articleId 文章id
     * @return
     */
    List<Review> listByArticleId(String articleId);
}
