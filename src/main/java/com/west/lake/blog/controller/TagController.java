package com.west.lake.blog.controller;

import com.west.lake.blog.annotation.LoginUser;
import com.west.lake.blog.model.PageResult;
import com.west.lake.blog.model.SingleValueResult;
import com.west.lake.blog.model.entity.Tag;
import com.west.lake.blog.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * 标签操作接口
 *
 * @author futao
 * Created on 2019-03-23.
 */
@Api("标签")
@RestController
@RequestMapping(path = "tag", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TagController {

    @Resource
    private TagService tagService;

    /**
     * 新增标签
     *
     * @return
     */
    @LoginUser
    @ApiOperation("新增标签")
    @PostMapping(path = "add")
    public Tag add(
            @RequestParam("tagName") String tagName,
            @RequestParam("type") int type
    ) {
        return tagService.add(tagName, type);
    }

    /**
     * 删除标签
     *
     * @param id 要删除的标签的id
     * @return
     */
    @LoginUser
    @DeleteMapping("{id}")
    public SingleValueResult<String> delete(
            @PathVariable("id") String id
    ) {
        tagService.delete(id);
        return new SingleValueResult<>("success");
    }

    /**
     * 更新标签
     *
     * @param id 要更新的标签的id
     * @return
     */
    @LoginUser
    @PutMapping("update")
    public Tag update(
            @RequestParam("id") String id,
            @RequestParam("tagName") String tagName
    ) {
        //TODO("not implement")
        return tagService.update(id, tagName);
    }


    /**
     * 查询标签列表
     *
     * @return
     */
    @GetMapping("list")
    public PageResult<Tag> list(
            @RequestParam(value = "tagName", required = false) String tagName,
            @RequestParam(value = "type", required = false) Integer type
    ) {
        return tagService.list(tagName, type);
    }

    /**
     * 获取标签详情
     *
     * @param id 标签id
     * @return
     */
    @GetMapping("{id}")
    public Tag byId(@PathVariable("id") String id) {
        return tagService.byId(id);
    }
}
