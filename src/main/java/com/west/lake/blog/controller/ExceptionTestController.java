package com.west.lake.blog.controller;

import com.west.lake.blog.foundation.exception.ApplicationException;
import com.west.lake.blog.foundation.exception.LogicException;
import com.west.lake.blog.model.entity.User;
import com.west.lake.blog.tools.I18nTools;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

/**
 * 异常测试
 *
 * @author futao
 * Created on 2019-03-22.
 */
@Validated
@RestController
@RequestMapping("exceptionTest")
public class ExceptionTestController {


    /**
     * 逻辑异常测试类
     */
    @GetMapping("logicException")
    public void logicException() {
        throw LogicException.le(I18nTools.getMessage("01003.logic.exception.test"));
    }

    /**
     * 应用异常
     */
    @GetMapping("applicationException")
    public void applicationException() {
        throw ApplicationException.ae(I18nTools.getMessage("application.exception.test"));
    }


    /**
     * 未处理的异常测试
     */
    @GetMapping("unHandleException")
    public void unHandleException() {
        if (true) {
            throw new NullPointerException(I18nTools.getMessage("unhandle.exception.test"));
        }
    }

    /**
     * 字段验证测试
     *
     * @param userName
     */
    @GetMapping("validatorTest")
    public void validatorTest(
            @Min(value = 3, message = "{01001.user.name.length.illegal}")
            @RequestParam("userName") String userName
    ) {
        User user = new User();
        user.setUserName(userName);
//        HibernateValidatorConfig.validate(user);
    }
}
