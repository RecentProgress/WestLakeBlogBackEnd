package com.west.lake.blog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.west.lake.blog.configuration.HttpMessageConverterConfig;
import com.west.lake.blog.model.SingleValueResult;
import com.west.lake.blog.model.SystemConfig;
import com.west.lake.blog.tools.DateTools;
import com.west.lake.blog.tools.http.AbstractBaseRequest;
import com.west.lake.blog.tools.http.PostRequest;
import org.junit.Test;

import java.util.Date;
import java.sql.Timestamp;

/**
 * @author futao
 * Created on 2019-03-25.
 */
public class NormalTest {

    @Test
    public void reply() {
        String ques = "今天天气怎么样";
        JSONObject jsonObject = new JSONObject();
        jsonObject.fluentPut("reqType", 0).fluentPut("perception", new JSONObject().fluentPut("inputText", new JSONObject().fluentPut("ques", ques))).fluentPut("userInfo", new JSONObject().fluentPut("apiKey", "c9610a0052f04ff685b56887720c658a").fluentPut("userId", "FutaoSmile"));
        AbstractBaseRequest request = new PostRequest(SystemConfig.TULING);
        System.out.println(JSON.toJSONString(jsonObject, HttpMessageConverterConfig.SERIALIZER_FEATURES));
        ((PostRequest) request).addEntity(jsonObject);
        request.send();

    }

    @Test
    public void test3() {

    }

    @Test
    public void test2() {
        System.out.println(DateTools.addTimes(new Date(), DateTools.TimeTypeEnum.DAY, -1));
    }

    @Test
    public void test1() {
        String startTime = "2019-01-02";
        Timestamp startTimestamp = DateTools.dateToTimestamp(DateTools.stringToDate(startTime));
        Timestamp endTimestamp = DateTools.dateToTimestamp(DateTools.addTimes(DateTools.stringToDate(startTime), DateTools.TimeTypeEnum.DAY, 1));

        System.out.println(startTimestamp);
        System.out.println(endTimestamp);
    }
}
