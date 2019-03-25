package com.west.lake.blog.service.impl;

import com.west.lake.blog.dao.TagDao;
import com.west.lake.blog.model.SystemConfig;
import com.west.lake.blog.service.TagService;
import com.west.lake.blog.model.entity.Tag;
import com.west.lake.blog.tools.ServiceTools;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import com.west.lake.blog.controller.HibernateValidatorConfig;


/**
 * 标签
 *
 * @author futao
 * Created on 2019-03-23.
 */
@Transactional(isolation = Isolation.DEFAULT, timeout = SystemConfig.SERVICE_TRANSACTION_TIMEOUT_SECOND, rollbackFor = Exception.class)
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    /**
     * 新增标签
     *
     * @return 标签
     */
    @Override
    public Tag add() {
//参数封装成对象
        Tag tag = new Tag();
//TODO("赋值")

//参数合法性校验
        HibernateValidatorConfig.validate(tag);
//调用dao层
        tagDao.add(tag);
        return tag;
    }

    /**
     * 删除标签
     *
     * @param id 要删除的标签主键
     * @return 标签
     */
    @Override
    public void delete(String id) {
//调用dao层
        tagDao.delete(tagDao.byId(id).getId());
    }

    /**
     * 修改标签
     *
     * @param id 要修改的标签主键
     * @return 标签
     */
    @Override
    public Tag update(String id) {
//先查询数据是否存在
        Tag tag = ServiceTools.checkResultNullAndThrow(tagDao.byId(id), "标签");
//TODO("赋用户修改之后的值")

//参数合法性校验
        HibernateValidatorConfig.validate(tag);
//调用dao层
        tagDao.update(tag);
        return tag;
    }

    /**
     * 查询标签列表
     *
     * @return 标签列表
     */
    @Override
    public List<Tag> list() {
//调用dao层
        return tagDao.list();
    }


    /**
     * 获取标签详情
     *
     * @param id 要查询的标签主键
     * @return 标签
     */
    @Override
    public Tag byId(String id) {
//调用dao层
        return tagDao.byId(id);
    }


}
