package com.west.lake.blog.design.dynamicproxy.cglib;

import com.west.lake.blog.design.dynamicproxy.MyTransactionl;

/**
 * @author futao
 * Created on 2019/9/11.
 */
public class Book {
    @MyTransactionl
    public void add() {
        System.out.println("新增图书成功-cglib");
    }

    public void get() {
        System.out.println("颈椎病康复指南-cglib");
    }
}
