package com.west.lake.blog.service.impl;

import com.lazy.foundation.configuration.HibernateValidatorConfig;
import com.west.lake.blog.dao.ArticleDao;
import com.west.lake.blog.model.SystemConfig;
import com.west.lake.blog.model.entity.Article;
import com.west.lake.blog.model.entity.User;
import com.west.lake.blog.service.ArticleService;
import com.west.lake.blog.service.UserService;
import com.west.lake.blog.tools.CommonTools;
import com.west.lake.blog.tools.ServiceTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * 文章
 *
 * @author futao
 * Created on 2019-03-23.
 */
@Slf4j
@Transactional(isolation = Isolation.DEFAULT, timeout = SystemConfig.SERVICE_TRANSACTION_TIMEOUT_SECOND, rollbackFor = Exception.class)
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;
    @Resource
    private UserService userService;

    /**
     * 新增文章
     *
     * @return 文章
     */
    @Override
    public Article add(String title, String desc, String content, int type, String thirdLink) {
        //参数封装成对象
        Article article = new Article();
        article.setUser(new User(userService.currentUserId()));
        article.setTitle(title);
        article.setDesc(desc);
        article.setContent(content);
        article.setId(CommonTools.uuid());
        article.setType(type);
        article.setThirdLink(thirdLink);
        ServiceTools.setCreateAndLastModiftTimeNow(article);
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
        articleDao.delete(id);
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
