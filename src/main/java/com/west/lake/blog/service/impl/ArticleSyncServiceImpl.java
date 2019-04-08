package com.west.lake.blog.service.impl;

import com.west.lake.blog.model.entity.enums.ArticleType;
import com.west.lake.blog.model.entity.enums.face.IEnum;
import com.west.lake.blog.service.ArticleService;
import com.west.lake.blog.service.ArticleSyncService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author futao
 * Created on 2019-04-08.
 */
@Slf4j
@Service
public class ArticleSyncServiceImpl implements ArticleSyncService {


    @Resource
    private ArticleService articleService;

    /**
     * 同步文章
     *
     * @param thirdPartType
     * @param syncType
     * @param url
     * @throws IOException
     */
    @Override
    public void sync(int thirdPartType, int syncType, String url) throws IOException {
        ThirdPartTypeEnum thirdPartTypeEnum = ThirdPartTypeEnum.getByType(thirdPartType);
        SyncTypeEnum syncTypeEnum = SyncTypeEnum.getByType(syncType);
        assert thirdPartTypeEnum != null;
        log.info(">>> 开始同步：【{}】", thirdPartTypeEnum.domain + url);
        long startTime = System.currentTimeMillis();
        Document document = Jsoup.connect(thirdPartTypeEnum.domain + "/u/9e2e579df7dd").get();
        System.out.println(document.title());
        Element element = document.selectFirst("ul.note-list");
        element.children().forEach(it -> {
            Element article = it.selectFirst("a.title");
            //标题
            String title = article.text();
            //链接
            String link = article.attr("href");
            System.out.println(title);
            try {
                Document articleContent = Jsoup.connect(thirdPartTypeEnum.domain + link).get();
                articleService.add(title, null, articleContent.selectFirst("div.show-content-free").toString(), ArticleType.SYNC_JIANSHU.getType(), thirdPartTypeEnum.domain + link);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        log.info("<<<同步完成, 耗时:【{}】毫秒", System.currentTimeMillis() - startTime);
    }

    /**
     * 同步平台
     */
    @AllArgsConstructor
    @Getter
    public enum ThirdPartTypeEnum implements IEnum {
        /**
         * 1=简书
         */
        JIANSHU(1, "https://www.jianshu.com"),
        /**
         * 2=CSDN
         */
        CSDN(2, "");

        /**
         * 类型
         */
        private int type;
        /**
         * 地址
         */
        private String domain;

        @Override
        public String getStatus() {
            return String.valueOf(type);
        }


        public static ThirdPartTypeEnum getByType(int type) {
            switch (type) {
                case 1:
                    return JIANSHU;
                case 2:
                    return CSDN;
                default:
                    return null;
            }
        }
    }

    /**
     * 同步类型
     */
    @AllArgsConstructor
    @Getter
    public enum SyncTypeEnum implements IEnum {
        /**
         * 1=用户
         */
        USER(1, "用户"),
        /**
         * 2=单篇文章
         */
        SINGLE_ARTICLE(2, "单篇文章");

        private int type;
        private String desc;

        @Override
        public String getStatus() {
            return String.valueOf(type);
        }

        public static SyncTypeEnum getByType(int type) {
            switch (type) {
                case 1:
                    return USER;
                case 2:
                    return SINGLE_ARTICLE;
                default:
                    return null;
            }
        }
    }
}
