package com.west.lake.blog.configuration;

import com.lazy.foundation.foundation.FastJson2HttpMessageConverter;
import com.west.lake.blog.foundation.interceptor.LoginUserInterceptor;
import com.west.lake.blog.model.SystemConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.annotation.Resource;
import java.util.List;

/**
 * 扩展spring mvc的功能
 * 如果在该类上标注@EnableWebMvc,将会全面接管springboot对springmvc的配置。（springboot的自动配置全部失效）
 * 所有的配置都需要由自己实现
 *
 * @author futao
 * Created on 2018/9/18-15:15.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    //    @Resource
//    private RequestLogInterceptor requestLogInterceptor;
    @Resource
    private LocaleChangeInterceptor localeChangeInterceptor;

    //    @Resource
    private FastJson2HttpMessageConverter fastJson2HttpMessageConverterConfiguration;


    @Resource
    private LoginUserInterceptor loginUserInterceptor;

    @Resource
    private SystemConfig systemConfig;

    /**
     * 添加拦截器
     * addInterceptor()的顺序需要严格按照程序的执行的顺序
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(requestLogInterceptor).addPathPatterns("/**");
        registry.addInterceptor(loginUserInterceptor).addPathPatterns("/**");
        //  "/**"和"/*"是有区别的
        registry.addInterceptor(localeChangeInterceptor).addPathPatterns("/**");
    }


    /**
     * 添加静态资源映射
     * <p>
     * Add handlers to serve static resources such as images, js, and, css
     * files from specific locations under web application root, the classpath,
     * and others.
     * <p>
     * 配置了该资源解析器之后会导致swagger 404 ，需要加上对/swagger-ui.html的映射
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //这里不能随便乱改，否则SpringBoot默认的配置会失效
//        registry.addResourceHandler("/**")
//                .addResourceLocations("classpath:/static/");
        if (systemConfig.isEnableSwagger()) {
            registry.addResourceHandler("/swagger-ui.html")
                    .addResourceLocations("classpath:/META-INF/resources/");
        }
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//
        registry.addResourceHandler("/file/**")
                .addResourceLocations("classpath:/uploadFiles/");
    }

    /**
     * 跨域的第三种解决方案
     * Configure cross origin requests processing.
     *
     * @param registry
     * @since 4.2
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //允许带cookie的跨域
                .allowCredentials(true)
                .allowedMethods("*")
                .allowedHeaders("Content-Type")
                .allowedOrigins("*")
//                .allowedOrigins(SystemConfig.ALLOW_ORIGINS)
                .maxAge(systemConfig.getOriginMaxAge());
    }


    /**
     * 自定义httpMessageConvert
     *
     * @param converters 转换器集合
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(fastJson2HttpMessageConverterConfiguration);
    }
}
