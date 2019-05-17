package com.west.lake.blog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lazyer.foundation.foundation.FastJson2HttpMessageConverter;
import com.lazyer.httpclient.AbstractBaseRequest;
import com.lazyer.httpclient.GetRequest;
import com.lazyer.httpclient.PostRequest;
import com.lazyer.httpclient.enums.UserAgentEnum;
import com.west.lake.blog.model.SystemConfig;
import com.west.lake.blog.tools.DateTools;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.sql.Timestamp;

/**
 * @author futao
 * Created on 2019-03-25.
 */
@Slf4j
public class NormalTest {

    @Test
    public void test5() {
        String qs = "https://translate.google.cn/translate_a/single?client=webapp&sl=auto&tl=zh-CN&hl=zh-CN&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&otf=1&ssel=0&tsel=0&kc=11&tk=149201.319068&q=";
        String word = "perfect";
        qs += word;
        AbstractBaseRequest request = new GetRequest(qs);
        request.addHeader(":authority", "translate.google.cn");
        request.addHeader(":method", "GET");
        request.addHeader(":path", "/translate_a/single?client=webapp&sl=auto&tl=en&hl=zh-CN&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&dt=gt&otf=1&ssel=0&tsel=0&kc=5&tk=149201.319068&q=" + word);
        request.addHeader(":scheme", "https");
        request.addUserAgent(UserAgentEnum.CHROME);
        request.send();
    }

    @Test
    public void test4() throws IOException {
        String domain = "https://www.jianshu.com";
        Document document = Jsoup.connect(domain + "/u/9e2e579df7dd").get();
//        log.info(String.valueOf(document));
        System.out.println(document.title());
        Element element = document.selectFirst("ul.note-list");
        element.children().forEach(it -> {
            Element article = it.selectFirst("a.title");
            //标题
            String title = article.text();
            //链接
            String link = article.attr("href");
            System.out.println(title);
            try {
                Document articleContent = Jsoup.connect(domain + link).get();
                System.out.println(link);
                System.out.println(articleContent.selectFirst("div.show-content-free"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("----------------");
        });
    }


    @Test
    public void reply() {
        String ques = "今天天气怎么样";
        JSONObject jsonObject = new JSONObject();
        jsonObject.fluentPut("reqType", 0).fluentPut("perception", new JSONObject().fluentPut("inputText", new JSONObject().fluentPut("ques", ques))).fluentPut("userInfo", new JSONObject().fluentPut("apiKey", "c9610a0052f04ff685b56887720c658a").fluentPut("userId", "FutaoSmile"));
        AbstractBaseRequest request = new PostRequest(SystemConfig.TULING);
        System.out.println(JSON.toJSONString(jsonObject, FastJson2HttpMessageConverter.SERIALIZER_FEATURES));
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
