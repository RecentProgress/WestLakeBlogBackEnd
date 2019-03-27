package com.west.lake.blog.foundation.schedual;

import com.west.lake.blog.dao.UserDao;
import com.west.lake.blog.model.SystemConfig;
import com.west.lake.blog.model.entity.User;
import com.west.lake.blog.service.EmailService;
import com.west.lake.blog.tools.DateTools;
import com.west.lake.blog.tools.ServiceTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
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
     * 每天凌晨一点执行，统计前一天的注册人数
     */
    @Scheduled(cron = "0 0 1 * * ?")
//    @Scheduled(cron = "*/5 * * * * ?")
    public void dailyRegisterReport() {
        log.info(">>>dailyReport start");
        String yesterday = DateTools.dateToString(DateTools.addTimes(new Date(), DateTools.TimeTypeEnum.DAY, -1), DateTools.yyyyMMdd);
        List<User> list = userDao.list(ServiceTools.parseStartTimestamp(yesterday), ServiceTools.parseEndTimestampAddOneDay(yesterday), null, null);
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("total", list.size());
        map.put("users", list);
        Context context = new Context();
        context.setVariables(map);
        emailService.sendHtmlEmailWithTemplate("1185172056@qq.com", "1185172056@qq.com", SystemConfig.EMAIL_SUBJECT_PREFIX + yesterday + "统计", "dailyReport.html", context);
        log.info(">>>dailyReport end");
    }

}
