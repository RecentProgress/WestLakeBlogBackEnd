package com.west.lake.blog.configuration;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 通用bean注册管理中心
 *
 * @author futao
 * Created on 2019-01-15.
 */
@Slf4j
@Configuration
public class CommonConfig {

    /**
     * 国际化，设置默认的语言为中文
     * 将用户的区域信息存在session
     * 也可以存在cookie,由客户端保存   {@link org.springframework.web.servlet.i18n.CookieLocaleResolver}
     *
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver slr = new CookieLocaleResolver();
        slr.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return slr;
    }

    /**
     * 国际化，设置url识别参数
     *
     * @return
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }


    @Primary
    @Bean(destroyMethod = "shutdown")
    public ThreadPoolExecutor a() {
        ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder();
        threadFactoryBuilder.setNameFormat("wlb-tpe-%s");
        ThreadFactory threadFactory = threadFactoryBuilder.build();
        return new ThreadPoolExecutor(
                4,
                10,
                60,
                TimeUnit.SECONDS,
                //可被阻塞的消息数量
                new LinkedBlockingQueue<>(1024),
                threadFactory,
                (r, e) -> log.error("线程池ThreadPoolExecutor发生异常{}", e));
    }
}
