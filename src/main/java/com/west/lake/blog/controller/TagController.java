package com.west.lake.blog.controller;

import com.west.lake.blog.model.SingleValueResult;
import com.west.lake.blog.model.entity.Tag;
import com.west.lake.blog.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

/**
 * 标签操作接口
 *
 * @author futao
 * Created on 2019-03-23.
 */
@ApiIgnore
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
    @ApiOperation("新增标签")
    @PostMapping(path = "add")
    public Tag add(
    ) {
//TODO("not implement")
        return tagService.add();
    }

    /**
     * 删除标签
     *
     * @param id 要删除的标签的id
     * @return
     */
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
    @PutMapping("{id}")
    public Tag update(
            @PathVariable("id") String id
    ) {
        //TODO("not implement")
        return tagService.update(id);
    }


    /**
     * 查询标签列表
     *
     * @return
     */
    @GetMapping("list")
    public List<Tag> list() {
        return tagService.list();
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
