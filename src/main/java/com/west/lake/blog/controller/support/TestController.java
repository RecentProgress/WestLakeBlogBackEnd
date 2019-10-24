package com.west.lake.blog.controller.support;

import com.west.lake.blog.model.SingleValueResult;
import com.west.lake.blog.model.entity.MessageTemplateEnum;
import com.west.lake.blog.model.entity.User;
import com.west.lake.blog.service.MessageService;
import com.west.lake.blog.service.TestService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author futao
 * Created on 2019-03-25.
 */
//@ApiIgnore
@Slf4j
@Api("测试")
@RestController
@RequestMapping("test")
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger("sms");

    @Resource
    private MessageService tencentMessage;

    @Resource
    private TestService testService;

    @GetMapping("/")
    public void test() {
        LOGGER.info(StringUtils.repeat("-", 40));
        tencentMessage.send("18797811992", MessageTemplateEnum.REGISTER, "9999", "5");
    }

    @GetMapping("test1/{param}")
    public User test1(@PathVariable("param") int param) {
        return testService.save(param);
    }

    @PostMapping("/save")
    public SingleValueResult<String> save() {
        testService.insertDb();
        return new SingleValueResult<>("");
    }

    @GetMapping("/testString")
    public String testString() {
        return "S";
    }


    @GetMapping("/testGetLength")
    public void testGetLength(
            @RequestParam("param") String param
    ) {
        log.info(param);
    }
}
