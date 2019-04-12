package com.west.lake.blog.service.impl;

import com.west.lake.blog.model.entity.User;
import com.west.lake.blog.service.TestService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author futao
 * Created on 2019-04-12.
 */
@Service
public class TestServiceImpl implements TestService {

    @Resource
    private RedisTemplate<String, User> redisTemplate;

    @Override
    public User save(int i) {
        if (i == 1) {
            User user = new User();
            user.setUserName("futao");
            redisTemplate.opsForValue().set("1", user);
        } else {
            return redisTemplate.opsForValue().get("1");
        }
        return null;
    }
}
