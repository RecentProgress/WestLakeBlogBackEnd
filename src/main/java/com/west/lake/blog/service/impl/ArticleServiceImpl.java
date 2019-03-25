package com.west.lake.blog.service.impl;

import com.west.lake.blog.controller.HibernateValidatorConfig;
import com.west.lake.blog.dao.ArticleDao;
import com.west.lake.blog.model.SystemConfig;
import com.west.lake.blog.model.entity.Article;
import com.west.lake.blog.service.ArticleService;
import com.west.lake.blog.tools.ServiceTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 文章
 *
 * @author futao
 * Created on 2019-03-23.
 */
@Transactional(isolation = Isolation.DEFAULT, timeout = SystemConfig.SERVICE_TRANSACTION_TIMEOUT_SECOND, rollbackFor = Exception.class)
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    /**
     * 新增文章
     *
     * @return 文章
     */
    @Override
    public Article add() {
        //参数封装成对象
        Article article = new Article();
        //TODO("赋值")

        //参数合法性校验
        HibernateValidatorConfig.validate(article);
        //调用dao层
        articleDao.add(article);
        return article;
    }

    /**
     * 删除文章
     *
     * @param id 要删除的文章主键
     * @return 文章
     */
    @Override
    public void delete(String id) {
        //调用dao层
        articleDao.delete(articleDao.byId(id).getId());
    }

    /**
     * 修改文章
     *
     * @param id 要修改的文章主键
     * @return 文章
     */
    @Override
    public Article update(String id) {
        //先查询数据是否存在
        Article article = ServiceTools.checkResultNullAndThrow(articleDao.byId(id), "文章");
        //TODO("赋用户修改之后的值")

        //参数合法性校验
        HibernateValidatorConfig.validate(article);
        //调用dao层
        articleDao.update(article);
        return article;
    }

    /**
     * 查询文章列表
     *
     * @return 文章列表
     */
    @Override
    public List<Article> list() {
        //调用dao层
        return articleDao.list();
    }


    /**
     * 获取文章详情
     *
     * @param id 要查询的文章主键
     * @return 文章
     */
    @Override
    public Article byId(String id) {
        //调用dao层
        return articleDao.byId(id);
    }


}
