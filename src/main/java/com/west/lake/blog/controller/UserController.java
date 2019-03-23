package com.west.lake.blog.controller;

import com.west.lake.blog.model.SingleValueResult;
import com.west.lake.blog.model.entity.User;
import com.west.lake.blog.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

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
     * 通过邮件注册
     *
     * @param email           邮箱
     * @param verifyNum       验证码
     * @param password        密码
     * @param confirmPassword 确认密码
     */
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
     * 用户列表
     *
     * @return
     */
    @ApiOperation("用户列表")
    @GetMapping("list")
    public List<User> list() {
        return userService.list();
    }

}