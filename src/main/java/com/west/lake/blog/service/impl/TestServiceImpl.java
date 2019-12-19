package com.west.lake.blog.service.impl;

import com.west.lake.blog.dao.TagDao;
import com.west.lake.blog.dao.TestADao;
import com.west.lake.blog.model.entity.TestA;
import com.west.lake.blog.model.entity.User;
import com.west.lake.blog.service.TestService;
import com.west.lake.blog.tools.CommonTools;
import com.west.lake.blog.tools.DateTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * @author futao
 * Created on 2019-04-12.
 */
@Slf4j
@Service
public class TestServiceImpl implements TestService {

    @Resource
    private RedisTemplate<String, User> redisTemplate;

    @Resource
    private TagDao tagDao;

    @Resource
    private Executor executor;

    @Autowired
    private TestADao testADao;

    @Override
    public User save(int i) {
        if (i == 1) {
            User user = new User();
            user.setUserName("futao");
            redisTemplate.opsForValue().set("1", user);
        } else {
            return redisTemplate.opsForValue().get("1");
        }
        return null;
    }

    @Override
    public void insertDb() {
        long l = System.currentTimeMillis();
//        singleThread();
        threads();
        log.info("耗时:" + (System.currentTimeMillis() - l));
    }

    @Override
    public void param(String p1, String p2) {
        System.out.println("p1 = " + p1 + ", p2 = " + p2);
    }

    private void singleThread() {
        for (int i = 0; i < 10000; i++) {
            tagDao.addByField(CommonTools.uuid(), 1 + "-" + 1, 1, DateTools.currentTimeStamp(), DateTools.currentTimeStamp());
        }
    }

    private void threads() {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            executor.execute(() -> {
                        for (int j = 0; j < 10000; j++) {
                            tagDao.addByField(CommonTools.uuid(), finalI + "-" + j, 1, DateTools.currentTimeStamp(), DateTools.currentTimeStamp());
                        }
                        countDownLatch.countDown();
                    }
            );
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Transactional(rollbackFor = Exception.class, timeout = 10)
    @Override
    public TestA insertTestA(String id, String data) {
        Date date = new Date();

        System.out.println(TransactionAspectSupport.currentTransactionStatus());
        testADao.insert(new TestA(id, data, date, date));
        System.out.println(TransactionAspectSupport.currentTransactionStatus());
        TestA testA = testADao.selectById(id);
        System.out.println(TransactionAspectSupport.currentTransactionStatus());
        return testA;

    }
}

