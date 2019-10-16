package com.west.lake.blog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import com.lazy.foundation.foundation.FastJson2HttpMessageConverter;
import com.lazy.httpclient.AbstractBaseRequest;
import com.lazy.httpclient.GetRequest;
import com.lazy.httpclient.PostRequest;
import com.lazy.httpclient.enums.UserAgentEnum;
import com.west.lake.blog.model.SystemConfig;
import com.west.lake.blog.tools.DateTools;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ClassUtils;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Timestamp;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.lazy.constant.Constant.SERIALIZER_FEATURES;

/**
 * @author futao
 * Created on 2019-03-25.
 */
@Getter
@Setter
class A {
    String a;
    int b;
    String c;
}

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class B {
    String a;
    int b;
}

@Slf4j
public class NormalTest {

    @Test
    public void test31() throws InterruptedException {
        int threadCount = 20;
        AtomicInteger atomicInteger = new AtomicInteger(1);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount, (r) -> new Thread(r, "fu-thread-" + atomicInteger.getAndAdd(1)));
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < 100; j++) {
                    GetRequest request = new GetRequest("http://127.0.0.1:8090/measureDevice/list");
                    request.addParameter("pageIndex", "1");
                    request.addParameter("pageSize", String.valueOf(Integer.MAX_VALUE));
                    request.addParameter("forceInsp", "true");
                    request.addParameter("meterStatus", "");
                    request.addParameter("verificationState", "");
                    request.addHeader("Authorization", "000001");
                    ArrayList<Integer> es = new ArrayList<>();
                    es.add(1);
                    es.add(2);
                    es.add(3);
                    request.addParameter("meterStatus", es.toString());
                    request.send();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        Thread.sleep(1000 * 60 * 60);
    }

    @Test
    public void test30() {
        System.out.println("1234567".substring(0, 6));

    }


    public static void main(String[] args) {
        String a = "woniubi";
        String b = "woniubi";
        System.out.println(a.hashCode() + "," + b.hashCode() + "," + (a == b));                                 //true（都是指向常量池，所以相等）
        String c = new String("woniubi");
        String d = new String("woniubi");
        System.out.println(c.hashCode() + "," + d.hashCode() + "," + (c == d));                                 //false（new(String)都是在堆内存上分配的地址，所以不相等)
        System.out.println(c.hashCode() + "," + a.hashCode() + "," + (c == a));                                 //false （一个在常量池，一个在堆内存，内存地址不一样，所以不相等）
        String woniubi = new String("woniubi").intern();
        System.out.println("intern----");
        System.out.println(woniubi.hashCode() + "," + c.hashCode() + "," + (woniubi == c));                           //false（intern会先从常量池中取，取到了就直接指向常量池的地址，否则将此字符串添加到常量池中，并返回字符串的引用）
        System.out.println(woniubi.hashCode() + "," + d.hashCode() + "," + (woniubi == d));                           //false
        System.out.println(woniubi.hashCode() + "," + a.hashCode() + "," + (woniubi == a));                           //true
        String woniubi2 = new String("woniubi").intern();

        System.out.println("--===========");
        System.out.println(woniubi.hashCode() + "," + woniubi2.hashCode() + "," + (woniubi == woniubi2));                    //true


        System.out.println("int ======↓↓↓↓");
        int ai = 123;
        Integer bi = new Integer(123);
        System.out.println(ai == bi);               //true相等，因为bi会自动拆箱
        Integer i1 = new Integer(123);
        Integer i2 = new Integer(123);

        System.out.println(i1 == i2);                 //false   堆内存，内存地址不一致
        Integer integer1 = (Integer) 100;
        Integer integer2 = (Integer) 100;
        System.out.println(integer1 == integer2);       //true JVM已缓存-128~127的Integer，所以直接引用

        Integer integer3 = (Integer) 200;
        Integer integer4 = (Integer) 200;
        System.out.println(integer3 == integer4);           //false 超过缓存的数值，则在堆内存上开辟内存
    }

    @Test
    public void test23() {
        System.out.println(new Integer(10) == new Integer(10));

        System.out.println(10 == 10);
        System.out.println(new Integer(310) == new Integer(310));
    }

    @Test
    public void test22() {
        GetRequest request = new GetRequest("http://api.choviwu.top/garbage/uploadFile");
//     request.addParameter();
    }

    @Test
    public void test21() {
        GetRequest request = new GetRequest("http://api.choviwu.top/garbage/getGarbage");
        request.addParameter("garbageName", "男朋友");
        System.out.println(JSON.toJSONString(request.send(), SerializerFeature.PrettyFormat));
    }

    @Test
    public void test20() {
        System.out.println("9a7bdf43-e621-4852-a6ee-406f12d92e6".length());
        System.out.println("054a3a7a-8fa9-4e5b-aa8b-fd8e16e3fb58".length());
    }

    @Test
    public void test19() {
        String rule = "PACK AND AAA";
        Pattern pattern = Pattern.compile("[^ ]*\\{.*?}");
        Matcher matcher = pattern.matcher(rule);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group(0).replace("(", StringUtils.EMPTY));
        }
        System.out.println(list.isEmpty());
        System.out.println(list);
    }


    @Test
    public void test18() {
        List<String> strings = JSON.parseArray("[\"解析configuration rule失败，缺少必要字段[action]\",\"解析Configuration rule失败，不支持的RuleType[t]\",\"解析configuration rule失败，feature字段中的值[RCO000001APROFILE]为无效值\",\"解析configuration rule失败，feature字段中的值[WHEEL]为无效值\"]", String.class);
        System.out.println(strings);


    }

    @Test
    public void test17() {
        String p = "[^ ]*\\{.*?}";
        String str = "NOT (AAACCCA{A1,A2} AND (B{B1,B2} OR NOT C{C1,C2})) AND D{D1} OR E{E1}";
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            System.out.println("----" + matcher.group(0).replace("(", StringUtils.EMPTY));
        }
    }

    @Test
    public void test16() {
        List<A> as = new ArrayList<>();
        A e = new A();
        e.setA("1");
        as.add(e);
        System.out.println(as);

        as.set(0, null);
        System.out.println(as);

    }

    @Test

    public void test15() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String documentDate = simpleDateFormat.format(calendar.getTime());
        calendar.add(Calendar.DATE, -7);
        String startDate = simpleDateFormat.format(calendar.getTime());

        System.out.println("--" + documentDate);
        System.out.println("==" + startDate);
    }

    @Test
    public void test14() {
        Long a = 23L;
        DecimalFormat df = new DecimalFormat("#0.00");

        System.out.println(df.format(a.doubleValue() / 100.0));
        System.out.println(a / 100.00f);
    }

    private static String replace(String content) {
        return content.replaceAll("(?<=\\d{4})\\d(?=\\d{4})", "*");
    }

    @Test
    public void test13() {
        System.out.println(NormalTest.replace("12345"));
        System.out.println(NormalTest.replace("123456789"));
        System.out.println(NormalTest.replace("123"));
    }

    @Test
    public void test12() {
        System.out.println(0.1 + 0.2);
    }

    @Test
    public void test11() {
        A a = new A();
        B b = new B("1111", 2222);
        BeanUtils.copyProperties(a, b);
        System.out.println(b.a);
    }


    @Test
    public void test10() {
        Email email = new SimpleEmail();
        email.setHostName("1111");
        email.setSmtpPort(100);
        Email multiPartEmail = new MultiPartEmail();
        multiPartEmail.setHostName("11");
        System.out.println(multiPartEmail.getHostName());
        BeanUtils.copyProperties(email, multiPartEmail, "sendPartial");
        System.out.println(multiPartEmail.getHostName());
    }


    @Test
    public void test9() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String documentDate = simpleDateFormat.format(calendar.getTime());
        System.out.println(documentDate);
    }

    @Test
    public void test8() throws IOException {
        File file = new File("SAP/12.xls");
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
    }

    @Test
    public void test7() {
        OSSClient ossClient = new OSSClient("oss-cn-shanghai.aliyuncs.com", "Qx1pJWvQTpF1Aldb", "F77bMtYop1rgDI2KqdLxL4FIxdEGUP");
        try {
//            PutObjectResult result = ossClient.putObject("pic-wise-com", "1testExcel", new FileInputStream(new File("/Users/futao/Desktop/testExcel.xls")));
//            System.out.println(result.getETag());
            String url = "http://pic-wise-com" + "." + "oss-cn-shanghai.aliyuncs.com" + "/" + "1testExcel";
            System.out.println();
            OSSObject object = ossClient.getObject("pic-wise-com", "1testExcel");

            InputStream input = object.getObjectContent();

            int index;
            byte[] bytes = new byte[1024];
            File file = new File("/Users/futao/Desktop/555555555.xls");
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream downloadFile = new FileOutputStream(file);
            while ((index = input.read(bytes)) != -1) {
                downloadFile.write(bytes, 0, index);
                downloadFile.flush();
            }
            downloadFile.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test6() {
        String s = ClassUtils.convertClassNameToResourcePath("123.!@#****.10-sdaskdna");
        System.out.println(s);
    }

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
        System.out.println(JSON.toJSONString(jsonObject, SERIALIZER_FEATURES));
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
