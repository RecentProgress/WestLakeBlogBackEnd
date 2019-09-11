package com.west.lake.blog.design.dynamicproxy.cglib;

import com.west.lake.blog.design.dynamicproxy.MyTransactionl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author futao
 * Created on 2019/9/11.
 */
public class CglibProxy implements MethodInterceptor {

    public Object createProxyInstance(Object target) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        //回调方法
        enhancer.setCallback(this);
        //创建代理对象
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib---start");
        Object result;
        if (method.isAnnotationPresent(MyTransactionl.class)) {
            System.out.println("事物增强-start");
            result = methodProxy.invokeSuper(o, objects);
            System.out.println("事物增强-end");
        } else {
            result = methodProxy.invokeSuper(o, objects);
        }
        System.out.println("cglib---end");
        return result;
    }
}
