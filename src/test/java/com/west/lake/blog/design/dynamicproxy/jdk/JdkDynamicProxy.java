package com.west.lake.blog.design.dynamicproxy.jdk;

import com.west.lake.blog.design.dynamicproxy.MyTransactionl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author futao
 * Created on 2019/9/11.
 */
public class JdkDynamicProxy implements InvocationHandler {

    /**
     * 代理目标
     */
    private Object target;


    /**
     * 绑定委托对象并返回一个代理类
     *
     * @param target
     * @return
     */
    public Object createProxyInstance(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    /**
     * 在这里对代理目标进行增强
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        System.out.println("-----proxy start");
        if (method.isAnnotationPresent(MyTransactionl.class)) {
            //不生效，因为这个method是接口的method，不是实现类的method
//            System.out.println("事物开始");
//            result = method.invoke(target, args);
//            System.out.println("事物结束");
        }
        result = method.invoke(target, args);
        System.out.println("-----proxy end");
        return result;
    }
}
