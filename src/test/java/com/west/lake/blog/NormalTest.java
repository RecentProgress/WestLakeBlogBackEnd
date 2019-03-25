package com.west.lake.blog;

import com.west.lake.blog.tools.DateTools;
import org.junit.Test;

import java.sql.Timestamp;

/**
 * @author futao
 * Created on 2019-03-25.
 */
public class NormalTest {
    @Test
    public void test1() {
        String startTime = "2019-01-02";
        Timestamp startTimestamp = DateTools.dateToTimestamp(DateTools.stringToDate(startTime));
        Timestamp endTimestamp = DateTools.dateToTimestamp(DateTools.addTimes(DateTools.stringToDate(startTime), DateTools.TimeTypeEnum.DAY, 1));

        System.out.println(startTimestamp);
        System.out.println(endTimestamp);
    }
}
