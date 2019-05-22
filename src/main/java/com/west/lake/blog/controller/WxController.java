package com.west.lake.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.lazyer.foundation.foundation.exception.LogicException;
import com.west.lake.blog.annotation.RestSkip;
import com.west.lake.blog.foundation.exception.ErrorMessage;
import com.west.lake.blog.tools.RequestTools;
import com.west.lake.blog.tools.wx.WXBizMsgCrypt;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.SAXWriter;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.TreeSet;


/**
 * @author futao
 * Created on 2019-05-22.
 */
@Slf4j
@RestController
@RequestMapping("/wx")
public class WxController {

    public static String readAsChars(HttpServletRequest request) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    @RestSkip
    @PostMapping
    @SneakyThrows
    public void handler(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("---" + readAsChars(request));
        SAXReader reader = new SAXReader();
        WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt("wxToken", "eKO1LbabymYBO7Ai0YFi4fRqnfFNQnWLazUo7PS3n4Z", "wxd0b4ee6746f31122");
        ServletInputStream servletInputStream = request.getInputStream();
        Document document = reader.read(servletInputStream);
        wxBizMsgCrypt.decryptMsg(
                request.getParameter("msg_signature"),
                request.getParameter("timestamp"),
                request.getParameter("nonce"),
                "<xml>\n" +
                        "  <ToUserName><![CDATA[13213123]]></ToUserName>\n" +
                        "  <FromUserName><![CDATA[fromUser]]></FromUserName>\n" +
                        "  <CreateTime>1348831860</CreateTime>\n" +
                        "  <MsgType><![CDATA[text]]></MsgType>\n" +
                        "  <Content><![CDATA[this is a test]]></Content>\n" +
                        "  <MsgId>1234567890123456</MsgId>\n" +
                        "</xml>"
        );


//        Document read = reader.read(inputStream);
//        Element rootElement = read.getRootElement();
//        String toUserName = rootElement.element("ToUserName").getStringValue();
//        String fromUserName = rootElement.element("FromUserName").getStringValue();
//        String createTime = rootElement.element("CreateTime").getStringValue();
//        String msgType = rootElement.element("MsgType").getStringValue();
//        String content = rootElement.element("Content").getStringValue();
//        String msgId = rootElement.element("MsgId").getStringValue();
//
//        Document document = DocumentHelper.createDocument();
//        Element xml = document.addElement("xml");
//        xml.addElement("ToUserName").setText(fromUserName);
//        xml.addElement("FromUserName").setText(toUserName);
//        xml.addElement("CreateTime").setText(String.valueOf(System.currentTimeMillis()));
//        xml.addElement("MsgType").setText("text");
//        xml.addElement("Content").setText("[noReply:]" + content);
//        response.getOutputStream().write(xml.asXML().getBytes(StandardCharsets.UTF_8));

        System.out.println(00);

    }

    /**
     * 服务器配置绑定
     *
     * @param request
     * @return
     */
    @RestSkip
    @GetMapping
    public long tokenBind(HttpServletRequest request) {
        //时间戳
        String timestamp = request.getParameter("timestamp");
        //随机数
        String nonce = request.getParameter("nonce");
        //微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数
        String signature = request.getParameter("signature");
        //随机字符串
        String echostr = request.getParameter("echostr");
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
        if (result.equals(signature)) {
            return Long.valueOf(echostr);
        }
        throw LogicException.le(ErrorMessage.LogicErrorMessage.WX.BIND_FAIL);
    }
}
