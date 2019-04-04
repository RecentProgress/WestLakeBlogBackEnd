package com.west.lake.blog.model.entity;

/**
 * 短信模板
 *
 * @author futao
 * Created on 2019-04-04.
 */
public enum MessageTemplateEnum {

    /**
     * 注册参数1=短信验证码，2=有效分钟
     */
    REGISTER(307972);


    /**
     * 模板ID
     */
    private int templateId;
    /**
     * 参数1
     */
    private String param1;
    /**
     * 参数2
     */
    private String param2;
    /**
     * 参数3
     */
    private String param3;

    MessageTemplateEnum(int templateId) {
        this.templateId = templateId;
    }

    MessageTemplateEnum(int templateId, String param1) {
        this.templateId = templateId;
        this.param1 = param1;
    }

    MessageTemplateEnum(int templateId, String param1, String param2) {
        this.templateId = templateId;
        this.param1 = param1;
        this.param2 = param2;
    }

    MessageTemplateEnum(int templateId, String param1, String param2, String param3) {
        this.templateId = templateId;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
    }

    public int getTemplateId() {
        return templateId;
    }

    public String getParam1() {
        return param1;
    }

    public String getParam2() {
        return param2;
    }

    public String getParam3() {
        return param3;
    }
}

