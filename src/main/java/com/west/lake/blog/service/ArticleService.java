package com.west.lake.blog.service;

import com.west.lake.blog.model.entity.Article;

import java.util.List;

/**
 * 文章接口定义
 *
 * @author futao
 * Created on 2019-03-23.
 */
public interface ArticleService {

    /**
     * 新增文章
     *
     * @return 文章
     */
    Article add();

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
     * @param id 要修改的文章主键
     * @return 文章
     */
    Article update(String id);

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
