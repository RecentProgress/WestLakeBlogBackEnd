package com.west.lake.blog.foundation.filter;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * druid过滤请求
 *
 * @author futao
 * Created on 2018/10/11.
 */
@WebFilter(filterName = "DruidFilter", urlPatterns = "/*", initParams = {
        @WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")// 忽略资源
})
public class DruidFilter extends WebStatFilter {
}
