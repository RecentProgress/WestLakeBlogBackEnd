package com.west.lake.blog.dao;

import com.west.lake.blog.model.entity.Article;

import java.util.List;

/**
 * 文章Dao层
 *
 * @author futao
 * Created on 2019-03-23.
 */
public interface ArticleDao {

    /**
     * 新增文章
     *
     * @param article 文章
     * @return 文章
     */
    void add(Article article);

    /**
     * 删除文章
     *
     * @param id 要删除的文章主键
     * @return 文章
     */
    void delete(String id);

    /**
     * 修改文章
     *
     * @param article 要修改的文章
     * @return 文章
     */
    void update(Article article);

    /**
     * 查询文章列表
     *
     * @return 文章列表
     */
    List<Article> list();

    /**
     * 获取文章详情
     *
     * @param id 要查询的文章主键
     * @return 文章
     */
    Article byId(String id);
}
