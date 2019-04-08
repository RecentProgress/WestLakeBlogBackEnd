package com.west.lake.blog.controller.support;

import com.west.lake.blog.model.entity.MessageTemplateEnum;
import com.west.lake.blog.service.MessageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * @author futao
 * Created on 2019-03-25.
 */
//@ApiIgnore
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


}
