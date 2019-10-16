package com.west.lake.blog.annotation;

import java.lang.annotation.*;

/**
 * @author futao
 * Created on 2019-04-08.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Role {

}
