package com.west.lake.blog.service.impl;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.lazy.rest.exception.LogicException;
import com.west.lake.blog.foundation.exception.ErrorMessage;
import com.west.lake.blog.model.SystemConfig;
import com.west.lake.blog.model.entity.MessageTemplateEnum;
import com.west.lake.blog.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 腾信短信实现
 *
 * @author futao
 * Created on 2019-04-04.
 */
@Slf4j
@Service
@Async
public class MessageTengcentServiceImpl implements MessageService {

    @Resource
    private SystemConfig.TencentMessage tencentMessage;

    /**
     * 发送
     *
     * @param phoneNumber     手机号
     * @param messageTemplate 短信模板
     * @param params          参数列表
     */
    @Override
    public void send(String phoneNumber, MessageTemplateEnum messageTemplate, String... params) {
        try {
            log.info(">>> 发送短信给[{}],模板id为[{}]，参数列表为[{}]", phoneNumber, messageTemplate.getTemplateId(), params);
            //数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
            SmsSingleSender ssender = new SmsSingleSender(tencentMessage.getAppId(), tencentMessage.getAppKey());
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumber,
                    messageTemplate.getTemplateId(), params, tencentMessage.getSmsSign(), "", "");
            log.info("<<< 发送完成，结果为[{}]", result);
        } catch (Exception e) {
            log.error("发送短信发生异常:{}", e.getMessage(), e);
            throw LogicException.le(ErrorMessage.LogicErrorMessage.SEND_MESSAGE_FAIL);
        }

//        catch (HTTPException e) {
//            // HTTP响应码错误
////            e.printStackTrace();
////        } catch (JSONException e) {
////            // json解析错误
////            e.printStackTrace();
////        } catch (IOException e) {
////            // 网络IO错误
////            e.printStackTrace();
//        }
    }
}
