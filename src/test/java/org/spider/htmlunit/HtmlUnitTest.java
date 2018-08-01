package org.spider.htmlunit;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

/**
 * @author liuxin
 * @version Id: HtmlUnitTest.java, v 0.1 2018/7/30 下午3:31
 */
public class HtmlUnitTest {
    @Test
    public void test() throws Exception {
        for (int i = 0; i <100 ; i++) {
            WebClient webClient = new WebClient();
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(false);
            //去拿网页
            HtmlPage htmlPage = webClient.getPage("https://blog.springlearn.cn/");
//        //得到表单
//        HtmlForm form = htmlPage.getFormByName("searchForm");
//        //得到提交按钮
//        HtmlSubmitInput button = form.getInputByValue("搜狗搜索");
//        //得到输入框
//        HtmlTextInput textField = form.getInputByName("query");
//        //输入内容
//        textField.setValueAttribute("和尚");
//        //点一下按钮
//        HtmlPage nextPage = button.click();
//        String str = nextPage.getDocumentURI();
            String html = htmlPage.getWebResponse().getContentAsString();
            Document parse = Jsoup.parse(html);
            String busuanzi_value_site_pv = parse.getElementById("busuanzi_value_site_pv").text();
            System.out.println(busuanzi_value_site_pv);
            webClient.close();
        }

    }
}
