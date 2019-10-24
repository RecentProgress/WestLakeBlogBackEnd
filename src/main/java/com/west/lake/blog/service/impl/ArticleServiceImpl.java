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
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 文章
 * * `@CacheConfig`定义该类下的一些公用配置
 *
 * @author futao
 * Created on 2019-03-23.
 */
@CacheConfig(
        cacheNames = "article",
        cacheManager = "cacheManager",
        keyGenerator = "keyGenerator"
)
@Slf4j
@Transactional(isolation = Isolation.DEFAULT, timeout = SystemConfig.SERVICE_TRANSACTION_TIMEOUT_SECOND, rollbackFor = Exception.class)
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private UserService userService;

    /**
     * 新增文章
     * * `@CachePut`会在方法执行完毕之后直接将结果放到缓存中
     *
     * @return 文章
     */
    @CachePut(cacheNames = "article", keyGenerator = "keyGenerator")
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
     * * `allEntries`是否`cacheNames`删除文件夹下所有的缓存缓存
     * * `beforeInvocation`是否在方法之前执行删除缓存
     *
     * @param id 要删除的文章主键
     * @return 文章
     */
    @CacheEvict(cacheNames = "article", keyGenerator = "keyGenerator", allEntries = false, beforeInvocation = false)
    @Override
    public void delete(String id) {
        //调用dao层
        articleDao.delete(id);
    }

    /**
     * 修改文章
     * * `@CacheEvict`删除缓存中的值
     *
     * @param id 要修改的文章主键
     * @return 文章
     */
    @CachePut(cacheNames = "article", keyGenerator = "keyGenerator")
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
     * * `@Cacheable`如何缓存存在则从缓存中取到直接返回，如果不存在则执行方法，将方法的返回值存入缓存
     * * `cacheNames`相当于是缓存的文件夹
     * *  `keyGenerator`和'key'二选一
     * * `condition`满足条件才缓存
     * * `unless`满足条件就不缓存
     *
     * @param id 要查询的文章主键
     * @return 文章
     */
    @Cacheable(cacheNames = "article", keyGenerator = "keyGenerator", condition = "#id!=null && #id!=''", unless = "#id==1")
    @Override
    public Article byId(String id) {
        log.info("查找id是{}的文章", id);
        //调用dao层
        return articleDao.byId(id);
    }

}
