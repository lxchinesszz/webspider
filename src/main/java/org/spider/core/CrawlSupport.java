package org.spider.core;

import java.util.List;

/**
 * @author liuxin
 * @version Id: CrawlSupport.java, v 0.1 2018/7/28 下午7:16
 */
public interface CrawlSupport {
    void innerCrawl(List<String> urls);
}
