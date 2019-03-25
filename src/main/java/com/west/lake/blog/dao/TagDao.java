package com.west.lake.blog.dao;

import com.west.lake.blog.model.entity.Tag;

import java.util.List;

/**
* 标签Dao层
*
* @author futao
* Created on 2019-03-23.
*/
public interface TagDao {

/**
* 新增标签
* @param tag 标签
*
* @return 标签
*/
void add(Tag tag);

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
* @param tag 要修改的标签
* @return 标签
*/
void update(Tag tag);

/**
* 查询标签列表
*
* @return 标签列表
*/
List<Tag> list();

/**
* 获取标签详情
*
* @param id 要查询的标签主键
* @return 标签
*/
Tag byId(String id);
}
