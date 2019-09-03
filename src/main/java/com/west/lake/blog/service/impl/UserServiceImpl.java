package com.west.lake.blog.service.impl;

import com.lazyer.foundation.configuration.HibernateValidatorConfig;
import com.lazyer.foundation.foundation.exception.LogicException;
import com.west.lake.blog.dao.UserDao;
import com.west.lake.blog.foundation.exception.ErrorMessage;
import com.west.lake.blog.model.PageResult;
import com.west.lake.blog.model.RedisKeyFactory;
import com.west.lake.blog.model.SystemConfig;
import com.west.lake.blog.model.entity.MessageTemplateEnum;
import com.west.lake.blog.model.entity.User;
import com.west.lake.blog.model.entity.enums.UserSexEnum;
import com.west.lake.blog.model.entity.enums.UserStatusEnum;
import com.west.lake.blog.service.EmailService;
import com.west.lake.blog.service.MessageService;
import com.west.lake.blog.service.UserService;
import com.west.lake.blog.tools.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Objects;
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

    @Resource
    private SystemConfig systemConfig;

    @Resource
    private MessageService messageService;

    /**
     * 密码加盐
     */
    private static final String SALT = "nobug";

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
            String registerEmailKey = RedisKeyFactory.User.registerEmailKey(email);
            if (redisTemplate.opsForValue().get(registerEmailKey) == null) {
                if (user == null) {
                    userDao.insertEmailPreRegister(CommonTools.uuid(), email, UserStatusEnum.PRE_REGISTER.getCode(), DateTools.currentTimeStamp());
                }
                //发送注册验证码
                String verifyNum = CommonTools.verifyNum(4);
                emailService.sendSimpleEmail(email, "1185172056@qq.com", SystemConfig.EMAIL_SUBJECT_PREFIX + "注册验证码", String.format("您的验证码为%s，有效时间为" + SystemConfig.REGISTER_MESSAGE_AND_EMAIL_EXPIRED_MINUTES + "分钟，请尽快验证。", verifyNum));
                redisTemplate.opsForValue().set(registerEmailKey, verifyNum, SystemConfig.REGISTER_MESSAGE_AND_EMAIL_EXPIRED_MINUTES, TimeUnit.MINUTES);

            } else {
                throw LogicException.le(I18nTools.getMessage("01010.email.already.sended"));
            }
        } else {
            //已注册
            throw LogicException.le(I18nTools.getMessage("01009.email.already.exist"));
        }
    }

    /**
     * 预注册
     *
     * @param mobile 手机号
     */
    @Override
    public void sendRegisterMessage(String mobile) {
        //查询该手机号是否已经注册
        User user = userDao.selectByMobile(mobile);
        if (user == null || UserStatusEnum.PRE_REGISTER.getCode() == user.getStatus()) {
            //未注册成功
            String registerMessageKey = RedisKeyFactory.User.registerMessageKey(mobile);
            if (redisTemplate.opsForValue().get(registerMessageKey) == null) {
                if (user == null) {
                    //插预注册信息
                    userDao.insertMobilePreRegister(CommonTools.uuid(), mobile, UserStatusEnum.PRE_REGISTER.getCode(), DateTools.currentTimeStamp());
                }
                //生成验证码
                String verifyNum = CommonTools.verifyNum(4);
                //发送验证码
                messageService.send(mobile, MessageTemplateEnum.REGISTER, verifyNum, String.valueOf(SystemConfig.REGISTER_MESSAGE_AND_EMAIL_EXPIRED_MINUTES));
                //存入缓存并设置过期时间
                redisTemplate.opsForValue().set(registerMessageKey, verifyNum, SystemConfig.REGISTER_MESSAGE_AND_EMAIL_EXPIRED_MINUTES, TimeUnit.MINUTES);
            } else {
                throw LogicException.le(I18nTools.getMessage("01010.message.already.sended"));
            }
        } else {
            throw LogicException.le(I18nTools.getMessage("01016.mobile.already.exist"));
        }
    }

    /**
     * 注册
     *
     * @param email           邮箱地址
     * @param verifyNum       验证码
     * @param password        密码
     * @param confirmPassword 确认密码
     */
    @Override
    public void registerByEmail(String email, String verifyNum, String password, String confirmPassword) {
        //验证两次输入的密码是否一致
        if (password == null || !password.equals(confirmPassword)) {
            throw LogicException.le(I18nTools.getMessage("01011.password.not.equals"));
        }
        String redisVerifyNum = redisTemplate.opsForValue().get(RedisKeyFactory.User.registerEmailKey(email));
        //验证码过期
        if (redisVerifyNum == null) {
            throw LogicException.le(I18nTools.getMessage("01012.email.redis.expired"));
        }
        //验证码错误
        if (!redisVerifyNum.trim().equals(verifyNum.trim())) {
            throw LogicException.le(I18nTools.getMessage("01013.email.redis.verify.num.error"));
        }
        User user = userDao.selectByEmail(email);
        if (user.getStatus() == UserStatusEnum.NORMAL.getCode()) {
            throw LogicException.le(ErrorMessage.LogicErrorMessage.MUILTY_REGISTER_SUCCESS);
        }
        //插入注册信息
        userDao.register(user.getId(), CommonTools.md5(password + SALT), UserStatusEnum.NORMAL.getCode(), DateTools.currentTimeStamp(), DateTools.currentTimeStamp());
        //从缓存中移除
        redisTemplate.delete(RedisKeyFactory.User.registerEmailKey(email));
    }


    /**
     * 手机注册
     *
     * @param mobile          手机号
     * @param verifyNum       验证码
     * @param password        密码
     * @param confirmPassword 确认密码
     */
    @Override
    public void registerByMobile(String mobile, String verifyNum, String password, String confirmPassword) {
        //验证密码是否一致
        if (password == null || !password.equals(confirmPassword)) {
            throw LogicException.le(I18nTools.getMessage("01011.password.not.equals"));
        }
        //从缓存中获取验证码
        String redisVerifyNum = redisTemplate.opsForValue().get(RedisKeyFactory.User.registerMessageKey(mobile));
        //验证码过期
        if (redisVerifyNum == null) {
            throw LogicException.le(I18nTools.getMessage("01012.email.redis.expired"));
        }
        //验证码错误
        if (!redisVerifyNum.trim().equals(verifyNum.trim())) {
            throw LogicException.le(I18nTools.getMessage("01013.email.redis.verify.num.error"));
        }
        User user = userDao.selectByMobile(mobile);
        if (user.getStatus() == UserStatusEnum.NORMAL.getCode()) {
            throw LogicException.le(ErrorMessage.LogicErrorMessage.MUILTY_REGISTER_SUCCESS);
        }
        //插入注册信息
        userDao.register(user.getId(), CommonTools.md5(password + SALT), UserStatusEnum.NORMAL.getCode(), DateTools.currentTimeStamp(), DateTools.currentTimeStamp());
        //从缓存中移除
        redisTemplate.delete(RedisKeyFactory.User.registerMessageKey(mobile));

    }


    /**
     * 列表
     *
     * @param startDateString 开始时间
     * @param endDateString   结束时间
     * @param userName        用户名
     * @param status          用户状态
     * @param start           开始位置
     * @param limit           分页大小
     * @return
     */
    @Override
    public PageResult<User> list(String startDateString, String endDateString, String userName, Integer status, int start, int limit) {
        Timestamp startTimestamp = null;
        Timestamp endTimestamp = null;
        if (StringUtils.isNotEmpty(startDateString)) {
            startTimestamp = ServiceTools.parseStartTimestamp(startDateString);
        }
        if (StringUtils.isNotEmpty(endDateString)) {
            endTimestamp = ServiceTools.parseEndTimestampAddOneDay(endDateString);
        }
        return new PageResult<>(userDao.listCount(startTimestamp, endTimestamp, userName, status, start, limit), userDao.list(startTimestamp, endTimestamp, userName, status, start, limit));
    }

    /**
     * 通过邮箱登录
     *
     * @param email    邮箱
     * @param password 密码
     * @param response 响应
     * @return
     */
    @Override
    public User emailLogin(String email, String password, HttpServletResponse response) {
        return loginLogic(userDao.selectByEmail(email), password, response);

    }

    /**
     * 手机号登录
     *
     * @param mobile   手机号
     * @param password 密码
     * @param response 响应
     * @return
     */
    @Override
    public User mobileLogin(String mobile, String password, HttpServletResponse response) {
        return loginLogic(userDao.selectByMobile(mobile), password, response);
    }

    /**
     * 登录逻辑
     *
     * @param user     用户信息
     * @param password 密码
     * @param response 响应
     * @return
     */
    private User loginLogic(User user, String password, HttpServletResponse response) {
        if (user == null || user.getStatus() == UserStatusEnum.PRE_REGISTER.getCode()) {
            //用户不存在
            throw LogicException.le(ErrorMessage.LogicErrorMessage.USER_NOT_EXIST);
        } else if (Objects.equals(CommonTools.md5(password + SALT), user.getPassword())) {
            //密码正确
            if (user.getStatus() == UserStatusEnum.DISABLE.getCode()) {
                //用户被禁用
                throw LogicException.le(ErrorMessage.LogicErrorMessage.ACCOUNT_DISABLE);
            }
        } else {
            //密码错误
            throw LogicException.le(ErrorMessage.LogicErrorMessage.PASSWORD_WRONG);
        }
        //用户登录信息存入redis
        String sessionValue = CommonTools.uuid();
        String redisKey = RedisKeyFactory.User.userSessionKey(sessionValue);
        redisTemplate.opsForValue().set(redisKey, user.getId(), systemConfig.getSessionExpiredSecond(), TimeUnit.SECONDS);
        redisTemplate.opsForSet().add(RedisKeyFactory.Set.LOGIN_USER_ID_SET, user.getId());
        redisTemplate.expire(RedisKeyFactory.Set.LOGIN_USER_ID_SET, systemConfig.getSessionExpiredSecond(), TimeUnit.SECONDS);

        Cookie cookie = new Cookie(SystemConfig.SESSION_KEY, sessionValue);
        //https
        cookie.setSecure(false);
        //js
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(systemConfig.getSessionExpiredSecond());
        response.addCookie(cookie);
        updateLoginTimesAndLastLoginTime(user);
        ThreadLocalTools.set(user.getId());
        return user;
    }

    /**
     * 更新登录次数与上一次登录时间
     *
     * @param user 实体
     */
    private void updateLoginTimesAndLastLoginTime(User user) {
        user.setLoginTimes(user.getLoginTimes() + 1);
        user.setLastLoginTime(user.getCurrentLoginTime());
        user.setCurrentLoginTime(DateTools.currentTimeStamp());
        userDao.updateLoginTimesAndLastLoginTime(user);
    }

    /**
     * 当前用户id
     *
     * @return
     */
    @Override
    public String currentUserId() {
        String userId = ThreadLocalTools.get();
        if (userId == null) {
            throw LogicException.le(ErrorMessage.LogicErrorMessage.NOT_LOGIN);
        }
        return userId;
    }


    /**
     * 通过id查询用户信息
     *
     * @param id 用户id
     * @return
     */
    @Override
    public User byId(String id) {
        return userDao.byId(id);
    }

    /**
     * 更新个人信息
     *
     * @param id       用户id
     * @param userName 用户名
     * @param sex      性别
     * @param mobile   手机号
     * @param desc     描述
     * @param birthday 生日
     * @return 更新后的用户信息
     */
    @Override
    public User update(String id, String userName, int sex, String mobile, String desc, String birthday) {
        User user = ServiceTools.checkResultNullAndThrow(byId(id), "用户");
        if (user.getSex() == UserSexEnum.UNKNOWN.getCode()) {
            user.setSex(sex);
        }
        if (user.getMobile() == null) {
            user.setMobile(mobile);
        }
        if (user.getBirthday() == null) {
            user.setBirthday(DateTools.stringToDate(birthday, DateTools.yyyyMMdd));
        }
        user.setUserName(userName);
        user.setDesc(desc);
        ServiceTools.setLastModiftTimeNow(user);
        HibernateValidatorConfig.validate(user);
        userDao.update(user);
        return user;
    }
}