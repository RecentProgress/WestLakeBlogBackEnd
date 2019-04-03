package com.west.lake.blog.controller;

import com.west.lake.blog.annotation.LoginUser;
import com.west.lake.blog.model.SingleValueResult;
import com.west.lake.blog.model.entity.Review;
import com.west.lake.blog.service.ReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 评论操作接口
 *
 * @author futao
 * Created on 2019-03-28.
 */
@Api("评论")
@RestController
@RequestMapping(path = "review", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ReviewController {

    @Resource
    private ReviewService reviewService;

    /**
     * 新增评论
     *
     * @return
     */
    @LoginUser
    @ApiOperation("新增评论")
    @PostMapping(path = "add")
    public Review add(
            @RequestParam("articleId") String articleId,
            @RequestParam("content") String content,
            @RequestParam(value = "parentReviewId", required = false) String parentReviewId
    ) {
        return reviewService.add(articleId, parentReviewId, content);
    }

    /**
     * 删除评论
     *
     * @param id 要删除的评论的id
     * @return
     */
    @DeleteMapping("{id}")
    public SingleValueResult<String> delete(
            @PathVariable("id") String id
    ) {
        reviewService.delete(id);
        return new SingleValueResult<>("delete success");
    }

    /**
     * 更新评论
     *
     * @param id 要更新的评论的id
     * @return
     */
    @PutMapping("{id}")
    public Review update(
            @PathVariable("id") String id
    ) {
        //TODO("not implement")
        return reviewService.update(id);
    }


    /**
     * 查询评论列表
     *
     * @return
     */
    @GetMapping("list")
    public List<Review> list() {
        return reviewService.list();
    }

    /**
     * 获取评论详情
     *
     * @param id 评论id
     * @return
     */
    @GetMapping("{id}")
    public Review byId(@PathVariable("id") String id) {
        return reviewService.byId(id);
    }


    /**
     * 通过articleId列出一级评论
     *
     * @param articleId 文章id
     * @return
     */
    @GetMapping("listByArticleId/{articleId}")
    public List<Review> listByArticleId(@PathVariable String articleId) {

        return reviewService.listByArticleId(articleId);

    }
}
