package com.west.lake.blog.controller;

import com.alibaba.fastjson.JSON;
import com.lazy.httpclient.GetRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author futao
 * Created on 2019/9/3.
 */
@Slf4j
@RequestMapping("/wxBind")
@RestController
public class WxBindController {

    @PostMapping("/do")
    public WxUserInfo doBind(@RequestParam String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        GetRequest request = new GetRequest(url);
        request.addParameter("appid", "wx5b5c22eb6fdbcda8");
        request.addParameter("secret", "148d2288f07e5048a84986f98f7401d1");
        request.addParameter("grant_type", "authorization_code");
        request.addParameter("js_code", code);
        String result = request.send();
        WxUserInfo wxUserInfo = JSON.parseObject(result, WxUserInfo.class);
        log.info("code={},wxUserInfo={},", code, wxUserInfo);
        return wxUserInfo;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class WxUserInfo {
        private String openId;
        private String sessionKey;
    }
}
