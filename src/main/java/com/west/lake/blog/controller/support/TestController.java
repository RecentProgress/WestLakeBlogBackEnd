package com.west.lake.blog.controller.support;

import com.west.lake.blog.model.entity.MessageTemplateEnum;
import com.west.lake.blog.model.entity.User;
import com.west.lake.blog.service.MessageService;
import com.west.lake.blog.service.TestService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * @author futao
 * Created on 2019-03-25.
 */
//@ApiIgnore
@Api("测试")
@RestController
@RequestMapping("test")
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger("sms");

    @Resource
    private MessageService tencentMessage;

    @GetMapping("/")
    public void test() {
        LOGGER.info(StringUtils.repeat("-", 40));
        tencentMessage.send("18797811992", MessageTemplateEnum.REGISTER, "9999", "5");
    }

    @Resource
    private TestService testService;

    @GetMapping("test1/{param}")
    public User test1(@PathVariable("param") int param) {
        return testService.save(param);
    }

}
