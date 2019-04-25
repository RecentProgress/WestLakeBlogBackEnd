package com.west.lake.blog.controller;

import com.west.lake.blog.annotation.LoginUser;
import com.west.lake.blog.model.PageResult;
import com.west.lake.blog.model.SingleValueResult;
import com.west.lake.blog.model.entity.User;
import com.west.lake.blog.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;

/**
 * 用户
 *
 * @author futao
 * Created on 2019-03-22.
 */
@Validated
@Api("用户")
@RestController
@RequestMapping(path = "user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {

    @Resource
    private UserService userService;


    /**
     * 发送注册邮件
     *
     * @param email 邮件
     * @return
     */
    @ApiIgnore
    @ApiOperation("发送注册邮件")
    @PostMapping("sendRegisterEmail")
    public SingleValueResult<String> sendRegisterEmail(
            @RequestParam("email")
            @Email(message = "{01002.email.format.error}")
                    String email
    ) {
        userService.sendRegisterEmail(email);
        return new SingleValueResult<>("send success");
    }


    /**
     * 发送注册验证码
     *
     * @param mobile 手机号
     * @return
     */
    @ApiOperation("发送注册短信验证码")
    @PostMapping("sendRegisterVerifyCode")
    public SingleValueResult<String> sendRegisterVerifyCode(
            @RequestParam("mobile")
            @Size(min = 11, max = 11)
                    String mobile
    ) {
        userService.sendRegisterMessage(mobile);
        return new SingleValueResult<>("send success");
    }


    /**
     * 通过邮件注册
     *
     * @param email           邮箱
     * @param verifyNum       验证码
     * @param password        密码
     * @param confirmPassword 确认密码
     */
    @ApiIgnore
    @ApiOperation("通过邮件注册")
    @PostMapping("registerByEmail")
    public SingleValueResult<String> registerByEmail(@RequestParam("email")
                                                     @Email(message = "{01002.email.format.error}")
                                                             String email,
                                                     @RequestParam("verifyNum")
                                                     @NotNull
                                                             String verifyNum,
                                                     @RequestParam("password")
                                                     @NotNull
                                                             String password,
                                                     @RequestParam("confirmPassword")
                                                     @NotNull
                                                             String confirmPassword) {
        userService.registerByEmail(email, verifyNum, password, confirmPassword);
        return new SingleValueResult<>("注册成功");
    }

    /**
     * 通过手机号注册
     *
     * @param mobile          手机号
     * @param verifyNum       验证码
     * @param password        密码
     * @param confirmPassword 确认密码
     */
    @ApiOperation("通过手机号注册")
    @PostMapping("registerByMobile")
    public SingleValueResult<String> registerByMobile(@RequestParam("mobile")
                                                      @Size(min = 11, max = 11)
                                                              String mobile,
                                                      @RequestParam("verifyNum")
                                                      @NotNull
                                                              String verifyNum,
                                                      @RequestParam("password")
                                                      @NotNull
                                                              String password,
                                                      @RequestParam("confirmPassword")
                                                      @NotNull
                                                              String confirmPassword) {
        userService.registerByMobile(mobile, verifyNum, password, confirmPassword);
        return new SingleValueResult<>("注册成功");
    }

    /**
     * 列表
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param userName  用户名
     * @param status    用户状态
     * @param start     页
     * @param limit     分页大小
     * @return
     */
    @ApiOperation("用户列表")
    @LoginUser
    @GetMapping("list")
    public PageResult<User> list(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) Integer status,
            @Positive
            @RequestParam(required = false, defaultValue = "1") int start,
            @PositiveOrZero
            @RequestParam(required = false, defaultValue = "5") int limit
    ) {
        return userService.list(startDate, endDate, userName, status, start, limit);
    }

    /**
     * @param email    邮箱
     * @param password 密码
     * @param response 响应
     * @return
     */
    @ApiIgnore
    @ApiOperation("通过邮箱登录")
    @PostMapping("emailLogin")
    public User loginByEmail(
            @RequestParam("email")
            @Email(message = "{01002.email.format.error}")
                    String email,
            @RequestParam("password")
            @NotNull
                    String password,
            HttpServletResponse response) {
        return userService.emailLogin(email, password, response);
    }

    /**
     * 手机号密码登录
     *
     * @param mobile   手机号
     * @param password 密码
     * @param response 响应
     * @return
     */
    @ApiOperation("通过手机号密码登录")
    @PostMapping("mobileLogin")
    public User mobileLogin(
            @RequestParam("mobile")
            @Size(min = 11, max = 11)
                    String mobile,
            @RequestParam("password")
            @NotNull
                    String password,
            HttpServletResponse response) {
        return userService.mobileLogin(mobile, password, response);
    }


    @ApiOperation("当前登录用户")
    @LoginUser
    @GetMapping("currentUserInfo")
    public User currentUserInfo() {
        return userService.byId(userService.currentUserId());
    }


    /**
     * 更新个人资料
     *
     * @param userName 用户名
     * @param sex      性别
     * @param mobile   手机号
     * @param desc     个人简介
     * @param birthday 生日
     * @return
     */
    @ApiOperation("更新个人资料")
    @PutMapping("update")
    @LoginUser
    public User update(
            @RequestParam("userName") String userName,
            @RequestParam("sex") int sex,
            @RequestParam("mobile") String mobile,
            @RequestParam("desc") String desc,
            @RequestParam("birthday") String birthday
    ) {
        return userService.update(userService.currentUserId(), userName, sex, mobile, desc, birthday);
    }

}