package com.west.lake.blog.service;

import org.springframework.validation.annotation.Validated;
import org.thymeleaf.context.Context;

import javax.validation.constraints.Email;

/**
 * 邮件
 *
 * @author futao
 * Created on 2019-03-22.
 */
@Validated
public interface EmailService {

    /**
     * 简单文本邮件
     *
     * @param to      发送给
     * @param cc      抄送给
     * @param subject 主题
     * @param content 内容
     */
    void sendSimpleEmail(@Email(message = "{01002.email.format.error")
                                 String to,
                         @Email(message = "{01002.email.format.error")
                                 String cc,
                         String subject,
                         String content);

    /**
     * 发送html邮件
     *
     * @param to      发送给
     * @param cc      抄送给
     * @param subject 主题
     * @param content 内容
     * @param isHtml  是否html
     */
    void sendHtmlEmail(@Email(message = "{01002.email.format.error")
                               String to,
                       @Email(message = "{01002.email.format.error")
                               String cc,
                       String subject,
                       String content,
                       boolean isHtml);

    /**
     * 使用邮件模板发送邮件
     * * Context().apply {
     * * setVariable("username", "futao")
     *
     * @param to           发送给
     * @param cc           抄送给
     * @param subject      主题
     * @param templatePath 模板
     * @param context      内容
     */
    void sendHtmlEmailWithTemplate(@Email(message = "{01002.email.format.error")
                                           String to,
                                   @Email(message = "{01002.email.format.error")
                                           String cc,
                                   String subject,
                                   String templatePath,
                                   Context context);
}
