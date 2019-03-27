package com.west.lake.blog.controller.support;

import com.west.lake.blog.foundation.exception.ApplicationException;
import com.west.lake.blog.foundation.exception.LogicException;
import com.west.lake.blog.model.entity.User;
import com.west.lake.blog.tools.I18nTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api("异常测试")
@RequestMapping("exceptionTest")
public class ExceptionTestController {


    /**
     * 逻辑异常测试类
     */
    @ApiOperation("业务逻辑异常")
    @GetMapping("logicException")
    public void logicException() {
        throw LogicException.le(I18nTools.getMessage("01003.logic.exception.test"));
    }

    /**
     * 应用异常
     */
    @ApiOperation("捕获的系统异常")
    @GetMapping("applicationException")
    public void applicationException() {
        throw ApplicationException.ae(I18nTools.getMessage("application.exception.test"));
    }


    /**
     * 未处理的异常测试
     */
    @ApiOperation("未处理的系统异常")
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
    @ApiOperation("字段验证框架测试")
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
