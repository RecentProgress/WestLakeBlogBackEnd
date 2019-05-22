package com.west.lake.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author futao
 * Created on 2019-05-22.
 */
@Slf4j
@RestController("/")
public class WxController {

    @GetMapping("/")
    public String token(HttpServletRequest request) {
        String wxToken = request.getParameter("wxToken");
        log.info("收到微信请求:[{}]", wxToken);
        return wxToken;
    }
}
