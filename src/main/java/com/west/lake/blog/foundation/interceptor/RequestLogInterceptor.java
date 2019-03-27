package com.west.lake.blog.foundation.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.west.lake.blog.tools.RequestTools.requestLog;

/**
 * 请求controller记录日志，以及接口请求时间记录
 *
 * @author futao
 * Created on 2018/9/20-12:12.
 */
@Component
public class RequestLogInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(RequestLogInterceptor.class);

    /**
     * 统计接口请求次数请求
     */
    private ConcurrentHashMap<String, AtomicInteger> apiRequestStatistic = new ConcurrentHashMap<>();

    /**
     * controller执行之前
     * getRemoteAddr（）是获得客户端的ip地址
     * getRemoteHost（）是获得客户端的主机名
     * 获取客户端的IP地址的方法是：request.getRemoteAddr()，这种方法在大部分情况下都是有效的。但是在通过了Apache,Squid等反向代理软件就不能获取到客户端的真实IP地址了（比如负载均衡处理）。
     * return request.getHeader("x-forwarded-for");
     * 此处，获取ip,当x-forwarded-for为null时，表示请求没有经过处理，此时调用getRemoteAddr（）和getRemoteHost（）都可获取真实ip
     * 反之，则getHeader（"x-forwarded-for"）为真实的ip。
     *
     * @param request  请求
     * @param response 响应
     * @param handler  拦截的对象
     * @return 是否放行
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        String uuid = UUID.randomUUID().toString();
        logger.info("请求(id={})开始：开始时间：{}", uuid, startTime);
        request.setAttribute("startTime", startTime);
        request.setAttribute("uuid", uuid);
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            RestController restController = method.getDeclaringClass().getAnnotation(RestController.class);
//            if (ObjectUtils.allNotNull(restController)) {
//                requestLog(request, logger);
//            }
            String methodName = method.getDeclaringClass() + ".< " + method.getName() + " >";
            AtomicInteger atomicInteger = apiRequestStatistic.get(methodName);
            if (atomicInteger == null) {
                apiRequestStatistic.put(methodName, new AtomicInteger(1));
            } else {
                apiRequestStatistic.put(methodName, new AtomicInteger(atomicInteger.incrementAndGet()));
            }
        }
        requestLog(request, logger);
        return true;
    }

    /**
     * 获取queryString中的数据
     *
     * @param request 请求
     * @return 请求参数
     * @throws UnsupportedEncodingException
     */
    private String queryString(HttpServletRequest request) throws UnsupportedEncodingException {
        String qStr = request.getQueryString();
        return qStr == null ? "" : URLDecoder.decode(qStr, "UTF-8");
    }


    /**
     * 视图渲染之前
     *
     * @param request      请求
     * @param response     响应
     * @param handler      拦截的对象
     * @param modelAndView 视图与数据
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 还可以在这个地方根据response.getStatus来处理404，500等问题
     *
     * @param request  请求
     * @param response 响应
     * @param handler  拦截的对象
     * @param ex       异常
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long currentTimeMillis = System.currentTimeMillis();
        logger.info("请求(id={})结束:结束时间:{}：本次请求耗时（毫秒）：{}", request.getAttribute("uuid"), currentTimeMillis, ((currentTimeMillis - (Long.valueOf(request.getAttribute("startTime").toString())))));
    }

    public ConcurrentHashMap<String, AtomicInteger> getApiRequestStatistic() {
        return apiRequestStatistic;
    }
}
