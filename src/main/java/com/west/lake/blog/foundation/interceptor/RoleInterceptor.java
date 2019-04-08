package com.west.lake.blog.foundation.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author futao
 * Created on 2019-04-08.
 */
@Aspect
@Component
public class RoleInterceptor {

    @Pointcut("@within(com.west.lake.blog.annotation.Role)||@annotation(com.west.lake.blog.annotation.Role)")
    public void pointCut() {
    }

    @Before(value = "pointCut()")
    public void pre(JoinPoint point) {
        System.out.println("before" + point.getSignature());
    }

    @After(value = "pointCut()")
    public void after(JoinPoint point) {
        System.out.println("after: " + point.getSignature());
    }
}
