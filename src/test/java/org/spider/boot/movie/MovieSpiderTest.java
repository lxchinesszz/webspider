package org.spider.boot.movie;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.spider.boot.SimpleSpider;
import org.spider.core.HtmlDefinition;
import org.spider.core.SpiderHandler;
import org.spider.core.WillRequests;
import org.spider.core.middleware.RandomAgentRequestMiddleware;
import org.spider.core.middleware.RequestMiddleware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 演示爬取动态爬虫
 *
 * @author liuxin
 * @version Id: MovieSpiderTest.java, v 0.1 2018/7/30 下午4:08
 */
public class MovieSpiderTest {
    @Test
    public void spiderTest() {
        String url = "http://ent.sina.com.cn/film/";
        String cssQuery = "#feedCardContent > div:nth-child(1) > div";
        SpiderHandler spiderHandler = new SpiderHandler<Map<String, String>, Document>() {
            @Override
            public Map<String, String> doCrawl(HtmlDefinition<Document> htmlDefinition, WillRequests willRequests) {
                Map<String, String> map = new HashMap<>();
                Document document = htmlDefinition.getDocument();
                Elements elements = document.getElementById("feedCardContent").getElementsByAttributeValue("class", "feed-card-item");//获取元素节点等
                elements.forEach(element -> {
                    System.out.println(element.getElementsByTag("h2").first().getElementsByTag("a").text());
                    System.out.println(element.getElementsByTag("h2").first().getElementsByTag("a").attr("href"));
                });
                return map;
            }
        };
        SimpleSpider simpleSpider = new SimpleSpider(spiderHandler, false);
        simpleSpider.start(url);
    }
}
