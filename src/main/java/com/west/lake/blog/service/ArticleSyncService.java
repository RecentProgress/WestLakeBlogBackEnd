package com.west.lake.blog.service;

import java.io.IOException;

/**
 * @author futao
 * Created on 2019-04-08.
 */
public interface ArticleSyncService {
    /**
     * 同步文章
     *
     * @param thirdPartType
     * @param syncType
     * @param url
     * @throws IOException
     */
    void sync(int thirdPartType, int syncType, String url) throws IOException;
}
