package org.spider.boot.ele;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.spider.boot.SimpleSpider;
import org.spider.boot.WebSpiderBuilder;
import org.spider.config.BrowserVersion;
import org.spider.config.ParseTypeEnum;
import org.spider.config.WebSpiderConfig;
import org.spider.core.HtmlDefinition;
import org.spider.core.Spider;
import org.spider.core.SpiderHandler;
import org.spider.core.WillRequests;
import org.spider.core.middleware.RandomAgentRequestMiddleware;
import org.spider.core.middleware.RequestMiddleware;
import org.spider.core.pipeline.PrintDownloadsPipeline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试爬取elm红包
 *
 * @author liuxin
 * @version Id: EleCouponsSpider.java, v 0.1 2018/7/30 上午10:22
 */
public class EleCouponsSpider {
    /**
     * 使用完整配置,用户可配置请求中间件和转存逻辑
     */
    @Test
    public void ele() {
        String url = "http://www.quanmama.com/search/?keyword=饿了么";
        String cssQuery = "#coupons-list-wrapper > ul > li";

        List<RequestMiddleware> requestMiddlewares = new ArrayList<>();
        requestMiddlewares.add(new RandomAgentRequestMiddleware());

        WebSpiderConfig webSpiderConfig = WebSpiderConfig.builder()
                //配置下载器
                .downloadsPipeline(new PrintDownloadsPipeline())
                //配置请求中间件
                .requestMiddlewares(requestMiddlewares)
                //配置解析类型
                .parseTypeEnum(ParseTypeEnum.JSOUP)
                //设置是否随机头
                .isRandomAgent(false)
                //配置浏览器类型
                .browserTypeEnum(BrowserVersion.CHROME).build();

        //如果是Jsoup要用Document
        //如果是Xpath要用xpath
        Spider spider = new WebSpiderBuilder().setWebSpiderConfig(webSpiderConfig)
                .spiderHandler(new SpiderHandler<Map<String, String>, Document>() {
                    @Override
                    public Map<String, String> doCrawl(HtmlDefinition<Document> htmlDefinition, WillRequests willRequests) {
                        Map<String, String> map = new HashMap<>();
                        Document document = htmlDefinition.getDocument();
                        Elements select = document.select(cssQuery);
                        select.forEach(element -> {
                            //饿了么红包
                            String href = element.select("div > h2 > span > a").attr("href");
                            String title = element.select("div > h2 > span > a").attr("title");
                            map.put(title, href);
                        });
                        return map;
                    }
                }).create();

        spider.start(url);
    }

    /**
     * 演示使用静态网页爬取
     * 对WebSpiderBuilder和SpiderConfig做一个封装 =SimpleSpider（简单爬虫）
     * 使用get方式获取目标页面,并通过jsoup解析(Jsoup比xpath好用很多,API也更友好,建议使用Jsoup)
     * 用户只用写自己的爬虫逻辑获取页面想要的数据SpiderHandler
     * 和对爬取数据的转存逻辑DownloadsPipeline(默认使用PrintDownloadsPipeline只打印数据)
     * <p></p>
     * 构造:
     * ① public SimpleSpider(SpiderHandler spiderHandler)
     * ② public SimpleSpider(SpiderHandler spiderHandler, DownloadsPipeline downloadsPipeline)
     * }
     */
    @Test
    public void ele2() {
        String url = "http://www.quanmama.com/search/?keyword=饿了么";
        String cssQuery = "#coupons-list-wrapper > ul > li";
        SpiderHandler spiderHandler = new SpiderHandler<Map<String, String>, Document>() {
            @Override
            public Map<String, String> doCrawl(HtmlDefinition<Document> htmlDefinition, WillRequests willRequests) {
                Map<String, String> map = new HashMap<>();
                Document document = htmlDefinition.getDocument();
//                System.out.println(htmlDefinition.getHtmlText());
                Elements select = document.select(cssQuery);
                select.forEach(element -> {
                    //饿了么红包
                    String href = element.select("div > h2 > span > a").attr("href");
                    String title = element.select("div > h2 > span > a").attr("title");
                    map.put(title, href);
                });
                return map;
            }
        };
        SimpleSpider simpleSpider = new SimpleSpider(spiderHandler,true);
        simpleSpider.start(url);
    }
}
