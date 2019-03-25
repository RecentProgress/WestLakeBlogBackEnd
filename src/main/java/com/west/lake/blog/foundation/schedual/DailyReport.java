package com.west.lake.blog.foundation.schedual;

import com.alibaba.fastjson.JSON;
import com.west.lake.blog.configuration.HttpMessageConverterConfig;
import com.west.lake.blog.dao.UserDao;
import com.west.lake.blog.model.entity.User;
import com.west.lake.blog.service.EmailService;
import com.west.lake.blog.tools.DateTools;
import com.west.lake.blog.tools.ServiceTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author futao
 * Created on 2019-03-25.
 */
@Component
@Async
@Slf4j
public class DailyReport {

    @Resource
    private EmailService emailService;
    @Resource
    private UserDao userDao;

    /**
     * 注册人数统计
     * 每天凌晨一点执行
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void dailyRegisterReport() {
        String yesterday = DateTools.dateToString(DateTools.addTimes(new Date(), DateTools.TimeTypeEnum.DAY, -1), DateTools.yyyyMMdd);
        List<User> list = userDao.list(ServiceTools.parseStartTimestamp(yesterday), ServiceTools.parseEndTimestampAddOneDay(yesterday), null, null);
        emailService.sendSimpleEmail("1185172056@qq.com", "1185172056@qq.com", "westLakeBlog|" + yesterday + "统计", JSON.toJSONString(list, HttpMessageConverterConfig.SERIALIZER_FEATURES));
    }

}
