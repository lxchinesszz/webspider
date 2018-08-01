package org.spider.core;

import java.util.List;

/**
 * @author liuxin
 * @version Id: Spider.java, v 0.1 2018/7/26 下午11:58
 */
public interface Spider {
    /**
     * 开始爬取
     *
     * @param urls 可变参数
     */
    void start(String... urls);

    /**
     * 开始爬取
     *
     * @param urls
     */
    void start(List<String> urls);

    /**
     * 关闭爬虫
     * 当关闭会立即关闭,会丢失爬取任务
     */
    void close();

    /**
     * 任务数量
     *
     * @return
     */
    int taskCount();
}
