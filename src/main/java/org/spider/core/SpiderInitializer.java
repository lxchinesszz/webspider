package org.spider.core;

import org.spider.config.WebSpiderConfig;
import org.spider.core.content.HtmlContent;

/**
 * @author liuxin
 * @version Id: SpiderInitializer.java, v 0.1 2018/7/27 下午10:12
 */
public interface SpiderInitializer {
    Spider initialize(WebSpiderConfig webSpiderConfig, SpiderHandler spiderHandler, HtmlContent htmlContent);
}
