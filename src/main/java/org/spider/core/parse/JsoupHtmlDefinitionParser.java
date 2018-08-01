package org.spider.core.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.spider.core.HtmlDefinition;
import org.spider.core.WebClientHolder;
import org.spider.core.task.SpiderLocation;

/**
 * @author liuxin
 * @version Id: JsoupHtmlDefinitionParser.java, v 0.1 2018/7/26 下午5:43
 */
public class JsoupHtmlDefinitionParser  implements HtmlDefinitionParser{

    @Override
    public HtmlDefinition parse(SpiderLocation spiderLocation, String htmlText) {
        Document document = Jsoup.parse(htmlText);
        String title = document.select("head > title").text();
        return new HtmlDefinition(title,spiderLocation.getLocation(),document,htmlText);
    }
}
