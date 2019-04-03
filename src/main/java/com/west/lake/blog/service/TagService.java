package com.west.lake.blog.service;

import com.west.lake.blog.model.PageResult;
import com.west.lake.blog.model.entity.Tag;

import java.util.List;

/**
 * 标签接口定义
 *
 * @author futao
 * Created on 2019-03-23.
 */
public interface TagService {

    /**
     * 新增标签
     *
     * @return 标签
     */
    Tag add(String tagName, int type);

    /**
     * 删除标签
     *
     * @param id 要删除的标签主键
     * @return 标签
     */
    void delete(String id);


    /**
     * 修改标签
     *
     * @param id 要修改的标签主键
     * @return 标签
     */
    Tag update(String id, String tagName);

    /**
     * 查询标签列表
     *
     * @return 标签列表
     */
    PageResult<Tag> list(String tagName, Integer type);

    /**
     * 获取标签详情
     *
     * @param id 要查询的标签主键
     * @return 标签
     */
    Tag byId(String id);
}
