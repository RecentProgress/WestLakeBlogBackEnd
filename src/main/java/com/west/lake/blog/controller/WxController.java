package com.west.lake.blog.controller;

import com.west.lake.blog.tools.RequestTools;
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
@RestController("/wx")
public class WxController {

    @GetMapping("/token")
    public String token(HttpServletRequest request) {
        log.info("收到微信请求:[{}]", RequestTools.queryParameters(request));
        return "success";
    }
}
