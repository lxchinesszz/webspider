package org.spider.core.parse;

import org.spider.core.HtmlDefinition;
import org.spider.core.WebClientHolder;
import org.spider.core.task.SpiderLocation;

/**
 * @author liuxin
 * @version Id: HtmlDefinitionParser.java, v 0.1 2018/7/26 下午4:18
 */
public interface HtmlDefinitionParser {
    /**
     * 解析成html
     * @param spiderLocation web请求对象及附属参数
     * @param htmlText 页面原始数据
     * @return
     */
    HtmlDefinition parse(SpiderLocation spiderLocation, String htmlText);
}
