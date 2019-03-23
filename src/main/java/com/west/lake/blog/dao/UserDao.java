package com.west.lake.blog.dao;

import com.west.lake.blog.model.entity.User;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * 用户
 *
 * @author futao
 * Created on 2019-03-22.
 */
public interface UserDao {

    /**
     * 通过邮箱查询用户
     *
     * @param email 邮箱
     * @return
     */
    User selectByEmail(@Param("email") String email);

    /**
     * 插入预注册信息
     *
     * @param id             主键
     * @param email          邮箱
     * @param status         状态
     * @param createTime     创建时间
     * @param lastModifyTime 最后修改时间
     */
    void insertPreRegister(@Param("id") String id, @Param("email") String email, @Param("status") int status, @Param("createTime") Timestamp createTime, @Param("lastModifyTime") Timestamp lastModifyTime);

    /**
     * 注册
     *
     * @param id             用户预注册id
     * @param password       密码
     * @param status         状态
     * @param lastModifyTime 最后修改时间
     */
    void register(@Param("id") String id, @Param("password") String password, @Param("status") int status, @Param("lastModifyTime") Timestamp lastModifyTime);

    /**
     * 列表
     *
     * @return
     */
    List<User> list();
}
