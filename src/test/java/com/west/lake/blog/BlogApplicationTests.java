package com.west.lake.blog;

import com.west.lake.blog.model.entity.MessageTemplateEnum;
import com.west.lake.blog.service.impl.MessageTengcentServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {

    @Test
    public void contextLoads() {

    }

    @Resource
    private MessageTengcentServiceImpl messageTengcentService;

    @Test
    public void test4() {
        messageTengcentService.send("18797811992", MessageTemplateEnum.REGISTER, "9999", "5");
    }

}
