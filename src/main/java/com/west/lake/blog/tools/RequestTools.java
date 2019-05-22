package com.west.lake.blog.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

/**
 * HttpServletRequest请求工具类
 *
 * @author futao
 * Created on 2018/9/20-13:07.
 */
public final class RequestTools {

    /**
     * 将cookie数组转换成jsonString
     *
     * @param cookies
     * @return
     */
    public static String getCookies(Cookie[] cookies) {
        return JSON.toJSONString(getCookieJsonObject(cookies));
    }

    /**
     * 获取cookieJsonObject
     *
     * @param cookies
     * @return
     */
    public static JSONObject getCookieJsonObject(Cookie[] cookies) {
        JSONObject jsonObject = new JSONObject();
        if (ArrayUtils.isNotEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                jsonObject.put(cookie.getName(), cookie.getValue());
            }
        }
        return jsonObject;
    }

    /**
     * 根据cookieKey获取cookieValue
     *
     * @param cookies
     * @param key
     * @return
     */
    public static String getCookieValue(Cookie[] cookies, String key) {
        return cookies == null ? null : getCookieJsonObject(cookies).getString(key);
    }

    /**
     * 将session转换成jsonString
     *
     * @param session
     * @return
     */
    public static String getSessionParameters(HttpSession session) {
        JSONObject jsonObject = new JSONObject();
        if (ObjectUtils.allNotNull(session)) {
            Enumeration<String> attributeNames = session.getAttributeNames();
            while (attributeNames.hasMoreElements()) {
                String element = attributeNames.nextElement();
                jsonObject.put(element, session.getAttribute(element));
            }
        }
        return JSON.toJSONString(jsonObject);
    }

    public static void requestLog(HttpServletRequest request, Logger logger) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n")
                .append("From: ").append(request.getRemoteHost()).append("|").append(request.getHeaders("x-forwarded-for") == null ? request.getRemoteAddr() : request.getHeader("x-forwarded-for")).append("|").append(request.getRemotePort())
                .append("\n")
                .append("请求方式: ").append(request.getMethod())
                .append("\n")
                .append("请求地址: ").append(request.getRequestURL())
                .append("\n")
                .append("请求sessions: ").append(getSessionParameters(request.getSession(false)))
                .append("\n")
                .append("请求参数：").append(queryParameters(request))
                .append("\n")
                .append("请求cookies: ").append(getCookies(request.getCookies()));
        logger.info(String.valueOf(sb));
    }

    /**
     * 获取getParameter中的数据
     * 使用getParameter代替getQueryString的原因是后者只能拿到url中的参数，对于放在body中的参数是拿不到的
     * 虽然GET和POST方法都可以将参数放在url中，但是POST放在body中的时候，getQueryString拿不到数据
     *
     * @param request 请求
     * @return
     */
    public static JSONObject queryParameters(HttpServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();
        JSONObject jsonObject = new JSONObject();
        map.forEach((k, v) -> jsonObject.put(k, Arrays.toString(v)));
        return jsonObject;
    }
}
