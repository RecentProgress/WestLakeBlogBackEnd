package com.west.lake.blog.service;

import com.west.lake.blog.model.entity.User;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import java.util.List;

/**
 * @author futao
 * Created on 2019-03-22.
 */
@Validated
public interface UserService {

    /**
     * 发送注册验证码邮件
     *
     * @param email 邮箱
     */
    void sendRegisterEmail(@Email(message = "{01002.email.format.error}") String email);

    /**
     * 注册
     *
     * @param email
     * @param verifyNum
     * @param password
     * @param confirmPassword
     */
    void registerByEmail(@Email(message = "{01002.email.format.error}") String email, String verifyNum, String password, String confirmPassword);

    /**
     * 列表
     *
     * @return
     */
    List<User> list();
}
