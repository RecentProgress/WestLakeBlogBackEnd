package com.west.lake.blog.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lazy.foundation.foundation.FastJson2HttpMessageConverter;
import com.lazy.httpclient.AbstractBaseRequest;
import com.lazy.httpclient.PostRequest;
import com.west.lake.blog.model.SingleValueResult;
import com.west.lake.blog.model.SystemConfig;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import static com.lazy.constant.Constant.SERIALIZER_FEATURES;

/**
 * @author futao
 * Created on 2019-03-28.
 */
@ApiIgnore
@RestController
@RequestMapping("tuling")
public class TulingController {

    @PostMapping("/")
    public SingleValueResult<String> reply(String ques) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.fluentPut("reqType", 0).fluentPut("perception", new JSONObject().fluentPut("inputText", new JSONObject().fluentPut("text", ques))).fluentPut("userInfo", new JSONObject().fluentPut("apiKey", "c9610a0052f04ff685b56887720c658a").fluentPut("userId", "WLBlog"));
        AbstractBaseRequest request = new PostRequest(SystemConfig.TULING);
        System.out.println(JSON.toJSONString(jsonObject, SERIALIZER_FEATURES));
        ((PostRequest) request).addEntity(jsonObject);
        return new SingleValueResult<>(request.send().getResult());
    }
}
