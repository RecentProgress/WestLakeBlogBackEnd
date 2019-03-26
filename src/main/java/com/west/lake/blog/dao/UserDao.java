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
     * @param id              主键
     * @param email           邮箱
     * @param status          状态
     * @param preRegisterTime 预注册时间
     */
    void insertPreRegister(@Param("id") String id, @Param("email") String email, @Param("status") int status, @Param("preRegisterTime") Timestamp preRegisterTime);

    /**
     * 注册
     *
     * @param id             用户预注册id
     * @param password       密码
     * @param status         状态
     * @param createTime     创建时间
     * @param lastModifyTime 最后修改时间
     */
    void register(@Param("id") String id, @Param("password") String password, @Param("status") int status, @Param("createTime") Timestamp createTime, @Param("lastModifyTime") Timestamp lastModifyTime);

    /**
     * 列表
     *
     * @param startTimestamp 开始时间
     * @param endTimestamp   结束时间
     * @param userName       用户名
     * @param status         用户状态
     * @return
     */
    List<User> list(@Param("startTimestamp") Timestamp startTimestamp, @Param("endTimestamp") Timestamp endTimestamp, @Param("userName") String userName, @Param("status") Integer status);

    /**
     * 更新登录次数与上一次登录时间
     *
     * @param user 实体
     */
    void updateLoginTimesAndLastLoginTime(User user);

    /**
     * 通过id查询用户信息
     *
     * @param id 用户id
     * @return
     */
    User byId(String id);


    /**
     * 更新个人信息
     *
     * @param user 用户信息
     */
    void update(User user);
}
