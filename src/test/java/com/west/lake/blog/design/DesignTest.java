package com.west.lake.blog.design;

import com.west.lake.blog.design.dynamicproxy.cglib.CglibProxy;
import com.west.lake.blog.design.dynamicproxy.jdk.Book;
import com.west.lake.blog.design.dynamicproxy.jdk.BookImpl;
import com.west.lake.blog.design.dynamicproxy.jdk.JdkDynamicProxy;
import org.junit.Test;

/**
 * @author futao
 * Created on 2019/9/11.
 */
public class DesignTest {

    @Test
    public void test2() {
        CglibProxy cglibProxy = new CglibProxy();
        com.west.lake.blog.design.dynamicproxy.cglib.Book proxyInstance = (com.west.lake.blog.design.dynamicproxy.cglib.Book) cglibProxy.createProxyInstance(new com.west.lake.blog.design.dynamicproxy.cglib.Book());
        proxyInstance.add();
        System.out.println("\n\n\n");
        proxyInstance.get();
    }

    @Test
    public void test1() {
        JdkDynamicProxy jdkDynamicProxy = new JdkDynamicProxy();
        Book bind = (Book) jdkDynamicProxy.createProxyInstance(new BookImpl());
        bind.add();
        bind.get();
    }
}
