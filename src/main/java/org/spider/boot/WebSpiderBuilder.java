package org.spider.boot;

import lombok.extern.slf4j.Slf4j;
import org.spider.config.WebSpiderConfig;
import org.spider.core.Spider;
import org.spider.core.SpiderHandler;
import org.spider.core.SpiderInitializer;
import org.spider.core.WebSpiderInitializer;
import org.spider.core.content.HtmlContent;


/**
 * @author liuxin
 * @version Id: WebSpiderBuilder.java, v 0.1 2018/7/26 下午7:07
 */
@Slf4j
public class WebSpiderBuilder {

    private WebSpiderConfig webSpiderConfig;


    private SpiderHandler spiderHandler;

    private HtmlContent htmlContent;


    /*************************start 需要用户配置的**********************/

    public WebSpiderBuilder setWebSpiderConfig(WebSpiderConfig webSpiderConfig) {
        this.webSpiderConfig = webSpiderConfig;
        return this;
    }


    public WebSpiderBuilder spiderHandler(SpiderHandler spiderHandler) {
        this.spiderHandler = spiderHandler;
        return this;
    }

    public WebSpiderBuilder htmlContent(HtmlContent htmlContent){
        this.htmlContent = htmlContent;
        return this;
    }

    public Spider create() {
        //TODO 检查泛型
        SpiderInitializer spiderInitializer = new WebSpiderInitializer();
        return spiderInitializer.initialize(webSpiderConfig, spiderHandler,htmlContent);
    }

    /************************end***************************/


}
