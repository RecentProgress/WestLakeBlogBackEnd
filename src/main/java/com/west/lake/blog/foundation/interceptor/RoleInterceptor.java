package com.west.lake.blog.foundation.interceptor;

import com.west.lake.blog.annotation.Role;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

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

    @Around(value = "pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        System.out.println("被标记" + method.isAnnotationPresent(Role.class));
        return point.proceed();
    }
}
