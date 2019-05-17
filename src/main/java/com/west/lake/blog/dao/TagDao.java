package com.west.lake.blog.dao;

import com.west.lake.blog.model.entity.Tag;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
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
     *
     * @param tag 标签
     * @return 标签
     */
    void add(Tag tag);

    /**
     * 添加标签
     *
     * @param id             主键
     * @param tagName        标签名
     * @param type           类型
     * @param createTime     创建时间
     * @param lastModifyTime 最后修改时间
     */
    void addByField(
            @Param("id") String id,
            @Param("tagName") String tagName,
            @Param("type") int type,
            @Param("createTime") Timestamp createTime,
            @Param("lastModifyTime") Timestamp lastModifyTime);

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
     * @param tagName 标签名称
     * @param type    类型
     * @return 标签列表
     */
    List<Tag> list(String tagName, Integer type);

    int count(String tagName, Integer type);

    /**
     * 获取标签详情
     *
     * @param id 要查询的标签主键
     * @return 标签
     */
    Tag byId(String id);

    Tag byNameAndType(@Param("tagName") String tagName, @Param("type") int type);
}
