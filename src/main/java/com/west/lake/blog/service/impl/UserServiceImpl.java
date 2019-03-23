package com.west.lake.blog.service.impl;

import com.west.lake.blog.dao.UserDao;
import com.west.lake.blog.foundation.exception.LogicException;
import com.west.lake.blog.model.RedisKeySet;
import com.west.lake.blog.model.entity.User;
import com.west.lake.blog.model.entity.enums.UserStatusEnum;
import com.west.lake.blog.service.EmailService;
import com.west.lake.blog.service.UserService;
import com.west.lake.blog.tools.CommonTools;
import com.west.lake.blog.tools.DateTools;
import com.west.lake.blog.tools.I18nTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author futao
 * Created on 2019-03-22.
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserDao userDao;

    @Resource
    private EmailService emailService;

    private String SALT = "nobug";

    /**
     * 发送注册验证码邮件
     *
     * @param email 邮箱
     */
    @Override
    public void sendRegisterEmail(String email) {
        //查询该邮箱是否已经注册
        User user = userDao.selectByEmail(email);
        if (user == null || UserStatusEnum.PRE_REGISTER.getCode() == user.getStatus()) {
            //未注册成功
            //从redis中查询是否已经发送邮件
            String registerEmailKey = RedisKeySet.registerEmailKey(email);
            if (redisTemplate.opsForValue().get(registerEmailKey) == null) {
                if (user == null) {
                    userDao.insertPreRegister(CommonTools.uuid(), email, UserStatusEnum.PRE_REGISTER.getCode(), DateTools.currentTimeStamp(), DateTools.currentTimeStamp());
                }
                //发送注册验证码
                String verifyNum = CommonTools.verifyNum(4);
                emailService.sendSimpleEmail(email, "1185172056@qq.com", "WestLakeBlog | 注册验证码", String.format("您的验证码为%s，有效时间为5分钟，请尽快验证。", verifyNum));
                redisTemplate.opsForValue().set(registerEmailKey, verifyNum, 5, TimeUnit.MINUTES);

            } else {
                throw LogicException.le(I18nTools.getMessage("01010.email.already.sended"));
            }
        } else {
            //已注册
            throw LogicException.le(I18nTools.getMessage("01009.email.already.exist"));
        }
    }

    /**
     * 注册
     *
     * @param email
     * @param verifyNum
     * @param password
     * @param confirmPassword
     */
    @Override
    public void registerByEmail(String email, String verifyNum, String password, String confirmPassword) {
        //验证两次输入的密码是否一致
        if (password == null || !password.equals(confirmPassword)) {
            throw LogicException.le(I18nTools.getMessage("01011.password.not.equals"));
        }
        String redisVerifyNum = redisTemplate.opsForValue().get(RedisKeySet.registerEmailKey(email));
        //验证码过期
        if (redisVerifyNum == null) {
            throw LogicException.le(I18nTools.getMessage("01012.email.redis.expired"));
        }
        //验证码错误
        if (!redisVerifyNum.trim().equals(verifyNum.trim())) {
            throw LogicException.le(I18nTools.getMessage("01013.email.redis.verify.num.error"));
        }
        //插入注册信息
        userDao.register(userDao.selectByEmail(email).getId(), CommonTools.md5(password + SALT), UserStatusEnum.NORMAL.getCode(), DateTools.currentTimeStamp());
        //从缓存中移除
        redisTemplate.delete(RedisKeySet.registerEmailKey(email));
    }


    /**
     * 列表
     *
     * @return
     */
    @Override
    public List<User> list() {
        return userDao.list();
    }

}
