package com.west.lake.blog.controller;

import com.west.lake.blog.model.SingleValueResult;
import com.west.lake.blog.model.entity.Article;
import com.west.lake.blog.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

/**
 * 文章操作接口
 *
 * @author futao
 * Created on 2019-03-23.
 */
@Api("文章")
@RestController
@ApiIgnore
@RequestMapping(path = "article", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ArticleController {

    @Resource
    private ArticleService articleService;

    /**
     * 新增文章
     *
     * @return
     */
    @ApiOperation("新增文章")
    @PostMapping(path = "add")
    public Article add(
    ) {
//TODO("not implement")
        return articleService.add();
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
        return articleService.list();
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
}
