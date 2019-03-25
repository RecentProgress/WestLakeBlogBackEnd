package com.west.lake.blog.controller;

import com.west.lake.blog.model.SingleValueResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author futao
 * Created on 2019-03-25.
 */
@RestController
@RequestMapping("test")
public class TestController {

    static final ThreadLocal<String> thread = new ThreadLocal<>();

    /**
     * @param param
     * @return
     */
    @GetMapping("threadLocalTest")
    public SingleValueResult<String> threadLocalTest(@RequestParam("param") String param) throws InterruptedException {
        thread.set(param);
        System.out.println(">>>>" + thread);
        TimeUnit.SECONDS.sleep(3);
        return new SingleValueResult<>(thread.get());
    }

    /**
     * @param param
     * @return
     */
    @GetMapping("threadLocalTest2")
    public SingleValueResult<String> threadLocalTest2(@RequestParam("param") String param) throws InterruptedException {
        thread.set(param);
        return new SingleValueResult<>(thread.get());
    }

}
