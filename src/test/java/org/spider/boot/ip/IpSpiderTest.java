package org.spider.boot.ip;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.spider.boot.SimpleSpider;
import org.spider.core.HtmlDefinition;
import org.spider.core.SpiderHandler;
import org.spider.core.WillRequests;
import org.spider.util.Console;

import java.util.ArrayList;
import java.util.List;

/**
 * 演示对西刺ip的爬取
 *
 * @author liuxin
 * @version Id: IpSpiderTest.java, v 0.1 2018/7/31 下午4:06
 */
public class IpSpiderTest {
    /**
     * 将西刺的ip爬取并打印
     * #ip_list > tbody > tr:nth-child(84) > td:nth-child(6)
     * css
     * xpath
     */
    @Test
    public void spider() {
        String url = "http://www.xicidaili.com/nn/";
        String url2 = "http://www.xicidaili.com/nt/";
        String url3 = "http://www.xicidaili.com/wn/";
        String url4 = "http://www.xicidaili.com/wt/";
        String cssQuery = "#ip_list > tbody > tr";
        SpiderHandler spiderHandler = new SpiderHandler<List<IpEntities>, Document>() {
            @Override
            public List<IpEntities> doCrawl(HtmlDefinition<Document> htmlDefinition, WillRequests willRequests) {
                List<IpEntities> ipEntities = new ArrayList<>();
                Document document = htmlDefinition.getDocument();
                List<String> strings = willUrls(document, htmlDefinition.getUrl());
//                willRequests.addAll(strings);
                Console.customerNormal("title", htmlDefinition.getTitle());
                Elements select = document.select(cssQuery);
                for (int i = 0; i < select.size(); i++) {
                    if (i == 0) {
                        continue;
                    }
                    Element element = select.get(i);
                    String ip = element.select("td:nth-child(2)").text();
                    String port = element.select("td:nth-child(3)").text();
                    String address = element.select("td:nth-child(4) > a").text();
                    String type = element.select("td:nth-child(6)").text();
                    ipEntities.add(new IpEntities(ip, port, address, type));
                }
                return ipEntities;
            }
        };
        SimpleSpider simpleSpider = new SimpleSpider(spiderHandler, false);
        simpleSpider.start(url, url2, url3, url4);
//        simpleSpider.start(url);

    }

    public List<String> willUrls(Document document,String url) {
        List<String> willUrl=new ArrayList<>();
        String text = document.select("#body > div.pagination > a:nth-child(13)").text();
        int i = Integer.parseInt(text);
        for (int j = 1; j <=i ; j++) {
            willUrl.add(url+j);
        }
        System.out.println(willUrl);
        return willUrl;
    }
}
