package org.spider.core.content;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import org.spider.core.middleware.RequestMiddleware;
import org.spider.core.task.SpiderLocation;

import java.io.IOException;
import java.util.List;

/**
 * @author liuxin
 * @version Id: WebDynamicHtmlContent.java, v 0.1 2018/7/30 下午3:39
 */
public class WebDynamicHtmlContentResolver extends AbstractHtmlContentResolver {

    @Override
    public String doHtmlFromLocation(SpiderLocation spiderLocation, List<RequestMiddleware> globalRequestMiddleware) {
        Page page = null;
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        globalRequestMiddleware.forEach(grm -> grm.customerRequest(webClient));
        //当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        //是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setCssEnabled(false);
        //很重要，启用JS
        webClient.getOptions().setJavaScriptEnabled(true);
        //很重要，设置支持AJAX
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        try {
            page = webClient.getPage(spiderLocation.getLocation());
            //异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束
            webClient.waitForBackgroundJavaScript(30000);
            //直接将加载完成的页面转换成xml格式的字符串
            return page.getWebResponse().getContentAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
