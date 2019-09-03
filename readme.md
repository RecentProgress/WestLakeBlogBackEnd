### # 2019-09-03
* 图灵机器人
* 微信小程序授权登录
* 集成logback
* 集成pageHelper
    * 使用：
       ```java
                PageMethod.startPage(start, limit);
                List<User> list = userDao.list4page(startTimestamp, endTimestamp, userName, status);
                return new PageInfo<>(list);
        ``` 
#### # 2019-05-23
* 集成微信公众号开发

#### # 2019-04-04

* 集成腾讯短信
* 通过手机号注册功能

![image.png](https://upload-images.jianshu.io/upload_images/1846623-9891b22dae13eb77.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
