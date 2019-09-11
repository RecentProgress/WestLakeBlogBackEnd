package com.west.lake.blog.design.dynamicproxy;

import java.lang.annotation.*;

/**
 * @author futao
 * Created on 2019/9/11.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface MyTransactionl {
}
