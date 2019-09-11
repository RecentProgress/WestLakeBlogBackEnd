package com.west.lake.blog.design.dynamicproxy.jdk;

import com.west.lake.blog.design.dynamicproxy.MyTransactionl;

/**
 * @author futao
 * Created on 2019/9/11.
 */
public class BookImpl implements Book {

    @MyTransactionl
    @Override
    public void add() {
        System.out.println("新增图书成功");
    }

    @Override
    public void get() {
        System.out.println("颈椎病康复指南");
    }
}
