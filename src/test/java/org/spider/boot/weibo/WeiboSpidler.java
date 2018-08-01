package org.spider.boot.weibo;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.spider.boot.FileSpider;
import org.spider.boot.SimpleSpider;
import org.spider.core.HtmlDefinition;
import org.spider.core.SpiderHandler;
import org.spider.core.WillRequests;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author liuxin
 * @version Id: WeiboSpidler.java, v 0.1 2018/7/30 下午1:49
 */
public class WeiboSpidler {


    @Test
    public void testWeb() {
        String s = userUrls().get(5);
        System.out.println(s);
        WebClient webClient = WebClient.create(s);
        webClient.head().headers(new Consumer<HttpHeaders>() {
            @Override
            public void accept(HttpHeaders httpHeaders) {
                httpHeaders.add("Referer", "https://weibo.com/5513970927/fans?rightmod=1&wvr=6");
                httpHeaders.add("Upgrade-Insecure-Requests", "1");
                httpHeaders.add("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Mobile Safari/537.36");
                httpHeaders.add("X-DevTools-Emulate-Network-Conditions-Client-Id", "70E1D82AF137A721144BC443E3DC3CFF");
                httpHeaders.add("SINAGLOBAL", "5559263978005.273.1487257572241; YF-Ugrow-G0=56862bac2f6bf97368b95873bc687eef; login_sid_t=2ce880a19c580a7217653d5e72cb12c8; cross_origin_proto=SSL; YF-V5-G0=16139189c1dbd74e7d073bc6ebfa4935; wb_view_log=1440*9001; _s_tentry=passport.weibo.com; UOR=www.liaoxuefeng.com,widget.weibo.com,www.baidu.com; Apache=369592481341.89606.1532929560964; ULV=1532929561966:14:1:1:369592481341.89606.1532929560964:1530199241013; SSOLoginState=1532929573; wvr=6; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WWUn7ulbUufseDROBsIWLJu5JpX5KzhUgL.Fo-feKe4S054eoM2dJLoI0qLxKnLB.BLB-qLxKBLB.zLBoqLxKMLB.eL1KqLxKMLBKnL12zLxKqL12eL1hMLxK-L12BL1KMt; SUHB=0auArfsBQvsdOg; ALF=1564465605; wb_view_log_5513970927=1440*9001; YF-Page-G0=ed0857c4c190a2e149fc966e43aaf725");
            }
        });
        webClient.options().retrieve();
        String block = webClient.get().retrieve().bodyToMono(String.class).block();
        System.out.println(block);
    }

    public List<String> userUrls() {
        List<String> urls = new ArrayList<>();
        String format = "https://weibo.com/5513970927/fans?cfs=600&relate=fans&t=1&f=1&type=&Pl_Official_RelationFans__87_page=%s#Pl_Official_RelationFans__87";
        for (int i = 1; i <= 206; i++) {
            String url = String.format(format, i);
            urls.add(url);
        }
        return urls;
    }

    @Test
    public void wbFromWeb() {
        List<String> userPageUrls = userUrls();
        String userCssQuery = "#Pl_Official_RelationFans__87 > div > div > div > div.follow_box > div.follow_inner > ul > li";
        String userLinkCss = "dl > dt > a > img";
        SpiderHandler spiderHandler = new SpiderHandler<List<WbUser>, Document>() {
            @Override
            public List<WbUser> doCrawl(HtmlDefinition<Document> htmlDefinition, WillRequests willRequests) {
                List<WbUser> wbUsers = new ArrayList<>();
                Document document = htmlDefinition.getDocument();
                Elements select = document.select(userCssQuery);
                select.forEach(element -> {
                    //获取用户连接
                    Elements userImgLink = element.select(userLinkCss);
                    //用户名称
                    String alt = userImgLink.attr("alt");
                    String src = userImgLink.attr("src");
                    WbUser wbUser = new WbUser(alt, src);
                    wbUsers.add(wbUser);
                });
                return wbUsers;
            }
        };
        SimpleSpider simpleSpider = new SimpleSpider(spiderHandler,false);
        simpleSpider.start(userPageUrls);
    }

    @Test
    public void wbFromFile() {
        List<String> userPageUrls = userUrls();
        String filePage = "/Users/mac/Desktop/软件编程指南的微博_微博_files/saved_resource.html";
        String userCssQuery = "#Pl_Official_RelationFans__87 > div > div > div > div.follow_box > div.follow_inner > ul > li";
        String userLinkCss = "dl > dt > a > img";
        SpiderHandler spiderHandler = new SpiderHandler<List<WbUser>, Document>() {
            @Override
            public List<WbUser> doCrawl(HtmlDefinition<Document> htmlDefinition, WillRequests willRequests) {
                List<WbUser> wbUsers = new ArrayList<>();
                Document document = htmlDefinition.getDocument();
                Elements select = document.select(userCssQuery);
                System.out.println(select.outerHtml());
                select.forEach(element -> {
                    //获取用户连接
                    Elements userImgLink = element.select(userLinkCss);
                    //用户名称
                    String alt = userImgLink.attr("alt");
                    String src = userImgLink.attr("src");
                    WbUser wbUser = new WbUser(alt, src);
                    wbUsers.add(wbUser);
                });
                return wbUsers;
            }
        };
        FileSpider fileSpider = new FileSpider(spiderHandler);
        fileSpider.start(filePage);
    }
}
