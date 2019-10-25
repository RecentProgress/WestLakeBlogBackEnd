package com.west.lake.blog.controller;

import com.west.lake.blog.annotation.LoginUser;
import com.west.lake.blog.model.SingleValueResult;
import com.west.lake.blog.model.entity.Article;
import com.west.lake.blog.model.entity.enums.ArticleType;
import com.west.lake.blog.service.ArticleService;
import com.west.lake.blog.service.ArticleSyncService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * 文章操作接口
 *
 * @author futao
 * Created on 2019-03-23.
 */
@Slf4j
@Api("文章")
@RestController
@RequestMapping(path = "article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @Resource
    private ArticleSyncService articleSyncService;

    /**
     * 新增文章
     *
     * @return
     */
    @LoginUser
    @ApiOperation("新增文章")
    @PostMapping(path = "add")
    public Article add(
            @RequestParam("title") String title,
            @RequestParam("desc") String desc,
            @RequestParam("content") String content
    ) {
//TODO("not implement")
        return articleService.add(title, desc, content, ArticleType.ORIGINAL.getType(), null);
    }

    /**
     * 删除文章
     *
     * @param id 要删除的文章的id
     * @return
     */
    @DeleteMapping("{id}")
    public SingleValueResult<String> delete(
            @PathVariable("id") String id
    ) {
        articleService.delete(id);
        return new SingleValueResult<>("success");
    }

    /**
     * 更新文章
     *
     * @param id 要更新的文章的id
     * @return
     */
    @PutMapping("{id}")
    public Article update(
            @PathVariable("id") String id
    ) {
        //TODO("not implement")
        return articleService.update(id);
    }


    /**
     * 查询文章列表
     *
     * @return
     */
    @GetMapping("list")
    public List<Article> list() {
        List<Article> list = articleService.list();
        log.info("{}", list.get(0));
        return list;
    }

    /**
     * 获取文章详情
     *
     * @param id 文章id
     * @return
     */
    @GetMapping("{id}")
    public Article byId(@PathVariable("id") String id) {
        return articleService.byId(id);
    }


    @ApiIgnore
    @PostMapping("sync")
    @LoginUser
    public SingleValueResult<String> sync(@RequestParam("thirdPartType")
                                                      int thirdPartType,
                                          @RequestParam("syncType") int syncType,
                                          @RequestParam("url") String url) throws IOException {
        articleSyncService.sync(thirdPartType, syncType, url);
        return new SingleValueResult<>("success");
    }
}
