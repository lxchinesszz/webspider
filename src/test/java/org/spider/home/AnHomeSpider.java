package org.spider.home;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.spider.boot.SimpleSpider;
import org.spider.core.HtmlDefinition;
import org.spider.core.SpiderHandler;
import org.spider.core.WillRequests;
import org.spider.home.modle.HomeInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 安居客
 *
 * @author liuxin
 * @version Id: AnHome.java, v 0.1 2018/9/4 下午5:09
 */
public class AnHomeSpider {

    @Test
    public void newHome() {
        //金水新房
        String url = "https://zz.fang.anjuke.com/loupan/jinshui/?from=SearchBar";
        String zhengzhong = "https://zz.fang.anjuke.com/loupan/zhengdongxinqu/";
        spider(zhengzhong, false);
    }

    @Test
    public void oldHome() {
        //金水区二手
        String url = "https://zhengzhou.anjuke.com/sale/jinshui/?from=SearchBar";
        //郑东新区
        String zhengdong = "https://zhengzhou.anjuke.com/sale/zhengdongxinquzhengzhou/";
        //管城区
        String guancheng = "https://zhengzhou.anjuke.com/sale/guanchenga/";

        String zhongyuan="https://zhengzhou.anjuke.com/sale/zhongyuanb/";

        spider(zhongyuan, true);
    }


    public void spider(String url, boolean isOld) {
        SpiderHandler spiderHandler = null;
        if (isOld) {
            spiderHandler = doOldSpider();
        } else {
            spiderHandler = doNewSpider();

        }
        HomeDownloadsPipeline downloadsPipeline = new HomeDownloadsPipeline(isOld);
        SimpleSpider simpleSpider = new SimpleSpider(spiderHandler, downloadsPipeline, true);
        simpleSpider.start(url);
    }

    private SpiderHandler doNewSpider() {
        String cssQuery = "#container > div.list-contents > div.list-results > div.key-list > div";

        String totalPriceCssQuery = "div.pro-price > span.price-det";

        String priceCssQuery = "div.pro-price > span.unit-price";

        String fangxing = "div.house-details > div:nth-child(2) > span:nth-child(1)";

        String mianji = "div.house-details > div:nth-child(2) > span:nth-child(3)";

        String buildTime = "div.house-details > div:nth-child(2) > span:nth-child(7)";

        String addressCssQuery = "div.house-details > div:nth-child(3) > span";

        String hrefCssQuery = "div.house-details > div.house-title > a";

        String nextButCssQuery = "#content > div.sale-left > div.multi-page > a.aNxt";

        SpiderHandler spiderHandler = new SpiderHandler<List<HomeInfo>, Document>() {
            @Override
            public List<HomeInfo> doCrawl(HtmlDefinition<Document> htmlDefinition, WillRequests willRequests) {
                List<HomeInfo> homeInfos = new ArrayList<>();
                Document document = htmlDefinition.getDocument();
                //每一个房子列表
                Elements homeList = document.select(cssQuery);
                homeList.forEach(new Consumer<Element>() {
                    @Override
                    public void accept(Element element) {
                        String href = element.select(hrefCssQuery).attr("href");
                        String fangXing = element.select(fangxing).text();
                        String address = element.select(addressCssQuery).text();
                        String mianJi = element.select(mianji).text();
                        String time = element.select(buildTime).text();
                        //单价
                        String unitPirce = element.select(priceCssQuery).text();
                        //总价
                        String totalPrice = element.select(totalPriceCssQuery).text();
                        System.err.println("地址:" + address + ",房型:" + fangXing + ",面积:" + mianJi + ",建造时间:" + time + ",单价(/m):" + unitPirce + ",总价:" + totalPrice + ",href:" + href);
                        HomeInfo homeInfo = HomeInfo.builder().address(address).roomType(fangXing).area(mianJi).buildDate(time)
                                .unitPrice(unitPirce).totalPrice(totalPrice).href(href).build();
                        homeInfos.add(homeInfo);
                    }
                });
                Elements nextButton = document.select(nextButCssQuery);
                String nextHref = nextButton.attr("href");
                if (StringUtils.isNotEmpty(nextHref)) {
                    willRequests.add(nextHref);
                }
                return homeInfos;
            }
        };

        return spiderHandler;
    }


    private SpiderHandler doOldSpider() {
        String cssQuery = "#houselist-mod-new > li";

        String totalPriceCssQuery = "div.pro-price > span.price-det";

        String priceCssQuery = "div.pro-price > span.unit-price";

        String fangxing = "div.house-details > div:nth-child(2) > span:nth-child(1)";

        String mianji = "div.house-details > div:nth-child(2) > span:nth-child(3)";

        String buildTime = "div.house-details > div:nth-child(2) > span:nth-child(7)";

        String addressCssQuery = "div.house-details > div:nth-child(3) > span";

        String hrefCssQuery = "div.house-details > div.house-title > a";

        String nextButCssQuery = "#content > div.sale-left > div.multi-page > a.aNxt";

        SpiderHandler spiderHandler = new SpiderHandler<List<HomeInfo>, Document>() {
            @Override
            public List<HomeInfo> doCrawl(HtmlDefinition<Document> htmlDefinition, WillRequests willRequests) {
                List<HomeInfo> homeInfos = new ArrayList<>();
                Document document = htmlDefinition.getDocument();
                //每一个房子列表
                Elements homeList = document.select(cssQuery);
                homeList.forEach(new Consumer<Element>() {
                    @Override
                    public void accept(Element element) {
                        String href = element.select(hrefCssQuery).attr("href");
                        String fangXing = element.select(fangxing).text();
                        String address = element.select(addressCssQuery).text();
                        String mianJi = element.select(mianji).text();
                        String time = element.select(buildTime).text();
                        //单价
                        String unitPirce = element.select(priceCssQuery).text();
                        //总价
                        String totalPrice = element.select(totalPriceCssQuery).text();
                        System.err.println("地址:" + address + ",房型:" + fangXing + ",面积:" + mianJi + ",建造时间:" + time + ",单价(/m):" + unitPirce + ",总价:" + totalPrice + ",href:" + href);
                        HomeInfo homeInfo = HomeInfo.builder().address(address).roomType(fangXing).area(mianJi).buildDate(time)
                                .unitPrice(unitPirce).totalPrice(totalPrice).href(href).build();
                        homeInfos.add(homeInfo);
                    }
                });
                Elements nextButton = document.select(nextButCssQuery);
                String nextHref = nextButton.attr("href");
                if (StringUtils.isNotEmpty(nextHref)) {
                    willRequests.add(nextHref);
                }
                return homeInfos;
            }
        };

        return spiderHandler;
    }
}
