package com.west.lake.blog.foundation.interceptor;

import com.lazyer.foundation.foundation.exception.LogicException;
import com.west.lake.blog.annotation.LoginUser;
import com.west.lake.blog.foundation.exception.ErrorMessage;
import com.west.lake.blog.model.RedisKeyFactory;
import com.west.lake.blog.model.SystemConfig;
import com.west.lake.blog.tools.RequestTools;
import com.west.lake.blog.tools.ThreadLocalTools;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对请求标记了LoginUser的方法进行拦截
 *
 * @author futao
 * Created on 2018/9/19-14:44.
 */
@Component
public class LoginUserInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginUserInterceptor.class);

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 在请求到达Controller之前进行拦截并处理
     *
     * @param request  请求
     * @param response 响应
     * @param handler  拦截的对象
     * @return 是否放行
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            //注解在方法上
            LoginUser loginUserAnnotation = ((HandlerMethod) handler).getMethodAnnotation(LoginUser.class);
            //注解在类上
            LoginUser classLoginUserAnnotation = ((HandlerMethod) handler).getMethod().getDeclaringClass().getAnnotation(LoginUser.class);
            if (ObjectUtils.anyNotNull(loginUserAnnotation, classLoginUserAnnotation)) {
                String cookieValue = RequestTools.getCookieValue(request.getCookies(), SystemConfig.SESSION_KEY);
                if (cookieValue == null) {
                    //前端cookie不存在->未登录
                    throw LogicException.le(ErrorMessage.LogicErrorMessage.NOT_LOGIN);
                } else {
                    String sessionKey = redisTemplate.opsForValue().get(RedisKeyFactory.User.userSessionKey(cookieValue));
                    if (sessionKey == null || sessionKey.isEmpty()) {
                        //后端redis中不存在sessionKey->未登录
                        throw LogicException.le(ErrorMessage.LogicErrorMessage.NOT_LOGIN);
                    }
                    ThreadLocalTools.set(sessionKey);
                }
            }
            //未被标记，直接放行
            return true;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //释放threadLocal资源
        ThreadLocalTools.remove();
    }
}
