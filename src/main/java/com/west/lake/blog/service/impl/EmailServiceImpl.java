package com.west.lake.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.west.lake.blog.foundation.exception.LogicException;
import com.west.lake.blog.service.EmailService;
import com.west.lake.blog.tools.I18nTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 邮件服务
 *
 * @author futao
 * Created on 2019-03-22.
 */
@Service
@Slf4j
@Async
public class EmailServiceImpl implements EmailService {

    @Resource
    private JavaMailSender sender;

    @Resource
    private TemplateEngine template;

    @Value("${spring.mail.username}")
    private String emailFrom;

    /**
     * 简单文本邮件
     *
     * @param to      发送给
     * @param cc      抄送给
     * @param subject 主题
     * @param content 内容
     */
    @Override
    public void sendSimpleEmail(String to, String cc, String subject, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(emailFrom);
        mailMessage.setTo(to);
        mailMessage.setCc(cc);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);
        sender.send(mailMessage);
    }

    /**
     * 发送html邮件
     *
     * @param to      发送给
     * @param cc      抄送给
     * @param subject 主题
     * @param content 内容
     * @param isHtml  是否html
     */
    @Override
    public void sendHtmlEmail(String to, String cc, String subject, String content, boolean isHtml) {
        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        try {
            messageHelper.setFrom(emailFrom);
            messageHelper.setTo(to);
            messageHelper.setCc(cc);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, isHtml);
        } catch (MessagingException e) {
            log.error("邮件发送失败{},邮件信息:{}", e.getMessage(), JSON.toJSONString(mimeMessage), e);
            throw LogicException.le(I18nTools.getMessage("email.send.fail"));
        }
        sender.send(mimeMessage);
    }

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
    @Override
    public void sendHtmlEmailWithTemplate(String to, String cc, String subject, String templatePath, Context context) {
        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        try {
            messageHelper.setFrom(emailFrom);
            messageHelper.setTo(to);
            messageHelper.setCc(cc);
            messageHelper.setSubject(subject);
            messageHelper.setText(template.process(templatePath, context), true);
        } catch (MessagingException e) {
            log.error("邮件发送失败{},邮件信息:{}", e.getMessage(), JSON.toJSONString(mimeMessage), e);
            throw LogicException.le(I18nTools.getMessage("email.send.fail"));
        }
        sender.send(mimeMessage);
    }

}
