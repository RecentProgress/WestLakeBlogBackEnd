package com.west.lake.blog.controller;

import com.west.lake.blog.annotation.RestSkip;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.TreeSet;


/**
 * @author futao
 * Created on 2019-05-22.
 */
@Slf4j
@RestController
@RequestMapping("/wx")
public class WxController {

    /**
     * @return
     */
    @RestSkip
    @GetMapping("/token")
    public long token(HttpServletRequest request) {
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String signature = request.getParameter("signature");
        String echostr = request.getParameter("echostr");
        /**
         *    * @param echostr   随机字符串
         *      * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
         *      * @param timestamp 时间戳
         *      * @param nonce     随机数
         */

        /*
        1）将token、timestamp、nonce三个参数进行字典序排序
        2）将三个参数字符串拼接成一个字符串进行sha1加密
        3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
         */
        log.info("接收到微信请求:echostr:[{}],signature:[{}],timestamp:[{}],nonce:[{}]", echostr, signature, timestamp, nonce);
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("wxToken");
        treeSet.add(timestamp);
        treeSet.add(nonce);

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : treeSet) {
            stringBuilder.append(s);
        }
        String result = (DigestUtils.sha1Hex(stringBuilder.toString().getBytes(Charset.forName("UTF-8"))));
        log.info("result:[{}]", result);
        log.error("equals:[{}]", result.equals(signature));
        return Long.valueOf(echostr);
    }
}
