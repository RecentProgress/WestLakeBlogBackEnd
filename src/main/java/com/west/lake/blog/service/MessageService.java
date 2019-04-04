package com.west.lake.blog.service;

import com.west.lake.blog.model.entity.MessageTemplateEnum;

/**
 * @author futao
 * Created on 2019-04-04.
 */
public interface MessageService {
    /**
     * 发送
     *
     * @param phoneNumber     手机号
     * @param messageTemplate 短信模板
     * @param params          参数列表
     */
    void send(String phoneNumber, MessageTemplateEnum messageTemplate, String... params);
}
