package com.west.lake.blog.service.impl;

import com.west.lake.blog.dao.ArticleDao;
import com.west.lake.blog.dao.ReviewDao;
import com.west.lake.blog.model.SystemConfig;
import com.west.lake.blog.model.entity.User;
import com.west.lake.blog.service.ReviewService;
import com.west.lake.blog.model.entity.Review;
import com.west.lake.blog.service.UserService;
import com.west.lake.blog.tools.CommonTools;
import com.west.lake.blog.tools.ServiceTools;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import com.west.lake.blog.configuration.HibernateValidatorConfig;


/**
 * 评论
 *
 * @author futao
 * Created on 2019-03-28.
 */
@Transactional(isolation = Isolation.DEFAULT, timeout = SystemConfig.SERVICE_TRANSACTION_TIMEOUT_SECOND, rollbackFor = Exception.class)
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleDao articleDao;

    /**
     * 新增评论
     *
     * @param articleId      文章id
     * @param parentReviewId 回复的评论id
     * @param content        评论内容
     * @return
     */
    @Override
    public Review add(String articleId, String parentReviewId, String content) {
        //参数封装成对象
        Review review = new Review();
        User user = new User();
        user.setId(userService.currentUserId());
        review.setUser(user);
        review.setArticle(ServiceTools.checkResultNullAndThrow(articleDao.byId(articleId), "文章"));
        if (StringUtils.isNotEmpty(parentReviewId)) {
            review.setParentReview(ServiceTools.checkResultNullAndThrow(reviewDao.byId(parentReviewId), "评论"));
        }
        review.setId(CommonTools.uuid());
        review.setContent(content);
        ServiceTools.setCreateAndLastModiftTimeNow(review);
        //参数合法性校验
        HibernateValidatorConfig.validate(review);
        //调用dao层
        reviewDao.add(review);
        return review;
    }

    /**
     * 删除评论
     *
     * @param id 要删除的评论主键
     * @return 评论
     */
    @Override
    public void delete(String id) {
        //调用dao层
        reviewDao.delete(reviewDao.byId(id).getId());
    }

    /**
     * 修改评论
     *
     * @param id 要修改的评论主键
     * @return 评论
     */
    @Override
    public Review update(String id) {
        //先查询数据是否存在
        Review review = ServiceTools.checkResultNullAndThrow(reviewDao.byId(id), "评论");
        //TODO("赋用户修改之后的值")

        //参数合法性校验
        HibernateValidatorConfig.validate(review);
        //调用dao层
        reviewDao.update(review);
        return review;
    }

    /**
     * 查询评论列表
     *
     * @return 评论列表
     */
    @Override
    public List<Review> list() {
        //调用dao层
        return reviewDao.list();
    }


    /**
     * 获取评论详情
     *
     * @param id 要查询的评论主键
     * @return 评论
     */
    @Override
    public Review byId(String id) {
        //调用dao层
        return reviewDao.byId(id);
    }


    /**
     * 通过articleId列出一级评论
     *
     * @param articleId 文章id
     * @return
     */
    @Override
    public List<Review> listByArticleId(String articleId) {
        List<Review> reviews = reviewDao.listByArticleId(articleId);
//        reviews.forEach(review ->);
        return reviews;
    }


    public void review(String parentReviewId) {
        listChild(parentReviewId).forEach(child -> listChild(parentReviewId));
    }

    public ArrayList<Review> listChild(String parentReviewId) {
        return new ArrayList<>();
    }

}
