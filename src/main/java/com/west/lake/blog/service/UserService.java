package com.west.lake.blog.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.west.lake.blog.model.PageResult;
import com.west.lake.blog.model.entity.User;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Email;

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
     * 预注册
     *
     * @param mobile 手机号
     */
    void sendRegisterMessage(String mobile);

    /**
     * 注册
     *
     * @param email           邮箱地址
     * @param verifyNum       验证码
     * @param password        密码
     * @param confirmPassword 确认密码
     */
    void registerByEmail(String email, String verifyNum, String password, String confirmPassword);

    /**
     * 手机注册
     *
     * @param mobile          手机号
     * @param verifyNum       验证码
     * @param password        密码
     * @param confirmPassword 确认密码
     */
    void registerByMobile(String mobile, String verifyNum, String password, String confirmPassword);

    /**
     * 列表
     *
     * @param startDateString 开始时间
     * @param endDateString   结束时间
     * @param userName        用户名
     * @param status          用户状态
     * @param start           开始位置
     * @param limit           分页大小
     * @return
     */
    PageResult<User> list(String startDateString, String endDateString, String userName, Integer status, int start, int limit);

    PageInfo<User> list4page(String startDateString, String endDateString, String userName, Integer status, int start, int limit);

    /**
     * 通过邮箱登录
     *
     * @param email    邮箱
     * @param password 密码
     * @param response 响应
     * @return
     */
    User emailLogin(String email, String password, HttpServletResponse response);

    /**
     * 手机号登录
     *
     * @param mobile   手机号
     * @param password 密码
     * @param response 响应
     * @return
     */
    User mobileLogin(String mobile, String password, HttpServletResponse response);

    /**
     * 当前用户id
     *
     * @return
     */
    String currentUserId();

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
     * @param id       用户id
     * @param userName 用户名
     * @param sex      性别
     * @param mobile   手机号
     * @param desc     描述
     * @param birthday 生日
     * @return 更新后的用户信息
     */
    User update(String id, String userName, int sex, String mobile, String desc, String birthday);
}
