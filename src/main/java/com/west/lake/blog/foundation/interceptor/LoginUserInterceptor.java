package com.west.lake.blog.foundation.interceptor;

import com.alibaba.fastjson.JSON;
import com.west.lake.blog.annotation.LoginUser;
import com.west.lake.blog.foundation.exception.ErrorMessage;
import com.west.lake.blog.foundation.exception.LogicException;
import com.west.lake.blog.model.SystemConfig;
import com.west.lake.blog.service.UserService;
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
import java.util.Set;

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
    private UserService userService;

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
                    throw LogicException.le(ErrorMessage.LogicErrorMessage.NOT_LOGIN);
                } else {
                    Set<String> members = redisTemplate.opsForSet().members(SystemConfig.SESSION_KEY + ":" + cookieValue);
                    if (members == null || members.isEmpty()) {
                        throw LogicException.le(ErrorMessage.LogicErrorMessage.NOT_LOGIN);
                    }
                    ThreadLocalTools.set(JSON.parseObject(members.toArray(new String[1])[0]).getString("id"));
                }


//                HttpSession session = request.getSession(false);
//                //session不为空
//                if (ObjectUtils.allNotNull(session)) {
//                    String loginUserId = (String) session.getAttribute(Constant.LOGIN_USER_SESSION_KEY);
//                    if (ObjectUtils.allNotNull(loginUserId)) {
//                        User currentUser = userService.getUserById(loginUserId);
//                        LOGGER.info("当前登陆用户为：" + currentUser);
//                        //将当前用户的信息存入threadLocal中
//                        threadLocalUtils.set(currentUser);
//                    } else {
//                        LOGGER.info("用户不存在");
//                        return false;
//                    }
//                } else {//session为空，用户未登录
//                    RestResult restResult = new RestResult(false, "-1", ErrorMessage.LogicErrorMessage.NOT_LOGIN, ErrorMessage.LogicErrorMessage.NOT_LOGIN.substring(6));
//                    response.getWriter().append(JSON.toJSONString(restResult));
//                    return false;
//                }
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //释放threadLocal资源
        ThreadLocalTools.remove();
    }
}
