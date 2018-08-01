package org.spider.boot;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.seimicrawler.xpath.JXDocument;
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
 * @author liuxin
 * @version Id: WebSpiderBuilderTest.java, v 0.1 2018/7/27 上午10:38
 */
public class WebSpiderBuilderTest {


    @Test
    public void simple() {
        String url = "https://www.baidu.com";
        WebSpiderBuilder webSpiderBuilder = new WebSpiderBuilder();
        Spider spider = webSpiderBuilder.spiderHandler(new SpiderHandler<String, Document>() {
            @Override
            public String doCrawl(HtmlDefinition<Document> htmlDefinition, WillRequests willRequests) {
                return null;
            }
        }).create();
        spider.start(url);
    }


    @Test
    public void allConfigXpath() {
        String url = "https://blog.csdn.net/bluemoon213/article/details/558406";
        String url2 = "https://blog.csdn.net/quanbugu/article/details/78171389";
        List<RequestMiddleware> requestMiddlewares = new ArrayList<>();
        requestMiddlewares.add(new RequestMiddleware2());
        requestMiddlewares.add(new RequestMiddleware1());
        requestMiddlewares.add(new RequestMiddleware3());
        requestMiddlewares.add(new RandomAgentRequestMiddleware());

        WebSpiderConfig webSpiderConfig = WebSpiderConfig.builder()
                //配置下载器
                .downloadsPipeline(new PrintDownloadsPipeline())
                //配置请求中间件
                .requestMiddlewares(requestMiddlewares)
                //配置解析类型
                .parseTypeEnum(ParseTypeEnum.XPATH)
                //设置是否随机头
                .isRandomAgent(false)
                //配置浏览器类型
                .browserTypeEnum(BrowserVersion.CHROME).build();

        Spider spider = new WebSpiderBuilder().setWebSpiderConfig(webSpiderConfig)
                .spiderHandler(new SpiderHandler<Object, JXDocument>() {
                    @Override
                    public Object doCrawl(HtmlDefinition<JXDocument> htmlDefinition, WillRequests willRequests) {
                        JXDocument jxDocument = htmlDefinition.getDocument();
                        String title = (String) jxDocument.selOne("/html/head/title");
                        return title;

                    }
                }).create();

        spider.start(url, url2);
    }

    /**
     * 如果是Jsoup 使用Document
     * 如果是Xpath 使用JxDocument
     */
    @Test
    public void allConfigJsoup() {
        String url = "https://blog.csdn.net/bluemoon213/article/details/558406";
        String url2 = "https://blog.csdn.net/quanbugu/article/details/78171389";
        List<RequestMiddleware> requestMiddlewares = new ArrayList<>();
//        requestMiddlewares.add(new RequestMiddleware2());
//        requestMiddlewares.add(new RequestMiddleware1());
//        requestMiddlewares.add(new RequestMiddleware3());
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
                .spiderHandler(new SpiderHandler<String, Document>() {
                    @Override
                    public String doCrawl(HtmlDefinition<Document> htmlDefinition, WillRequests willRequests) {
                        Document document = htmlDefinition.getDocument();
                        willRequests.add(url2);
                        return htmlDefinition.getTitle() + document.select("#uid").text();
                    }
                }).create();

        spider.start(url, url2);
    }

    @Test
    public void ele() {
        //#coupons-list-wrapper > ul > li:nth-child(1) > div
        String url = "https://blog.springlearn.cn/";
        String cssQuery = "body > section > div > div > main > article";
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
                            String href = element.select("div.post-content > div > h1 > a").attr("href");
                            String value = url + href;
                            String key = document.select("div.post-content > div > h1 > a").text();
                            map.put(key, value);
                        });
                        return map;
                    }
                }).create();

        spider.start(url);
    }

}
