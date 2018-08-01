package org.spider.boot;

import org.jsoup.nodes.Document;
import org.spider.core.HtmlDefinition;
import org.spider.core.SpiderHandler;
import org.spider.core.WillRequests;

/**
 * @author liuxin
 * @version Id: WebSpiderHandler.java, v 0.1 2018/7/27 下午5:41
 */
public class WebSpiderHandler implements SpiderHandler<String,Document> {
    @Override
    public String doCrawl(HtmlDefinition<Document> htmlDefinition, WillRequests willRequests) {
        return null;
    }
}
