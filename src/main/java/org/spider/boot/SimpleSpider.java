package org.spider.boot;

import org.spider.config.ParseTypeEnum;
import org.spider.core.DefaultWebKernelSpider;
import org.spider.core.SpiderHandler;
import org.spider.core.content.HtmlContent;
import org.spider.core.content.WebDynamicHtmlContentResolver;
import org.spider.core.content.WebStaticHtmlContentResolver;
import org.spider.core.middleware.HtmlUnitRandomAgentRequestMiddleware;
import org.spider.core.middleware.RandomAgentRequestMiddleware;
import org.spider.core.middleware.RequestMiddleware;
import org.spider.core.pipeline.DownloadsPipeline;
import org.spider.core.pipeline.PrintDownloadsPipeline;

import java.util.Arrays;
import java.util.List;


/**
 * @author liuxin
 * @version Id: SimpleSpider.java, v 0.1 2018/7/30 上午10:54
 */
public class SimpleSpider extends DefaultWebKernelSpider {

    private boolean isStatic = true;

    public SimpleSpider(SpiderHandler spiderHandler) {
        this(spiderHandler, true);
    }

    public SimpleSpider(SpiderHandler spiderHandler, boolean isStatic) {
        this(spiderHandler, isStatic ? new WebStaticHtmlContentResolver() : new WebDynamicHtmlContentResolver(),
                new PrintDownloadsPipeline(),
                isStatic?Arrays.asList(new RandomAgentRequestMiddleware())
                        :Arrays.asList(new HtmlUnitRandomAgentRequestMiddleware()));
    }

    public SimpleSpider(SpiderHandler spiderHandler, HtmlContent htmlContent) {
        this(spiderHandler, htmlContent, new PrintDownloadsPipeline(), Arrays.asList(new RandomAgentRequestMiddleware()));
    }

    public SimpleSpider(SpiderHandler spiderHandler, HtmlContent htmlContent, DownloadsPipeline downloadsPipeline, List<RequestMiddleware> requestMiddlewares) {
        super(spiderHandler, htmlContent, downloadsPipeline, ParseTypeEnum.JSOUP);
        this.registerGlobalRequestMiddleware(requestMiddlewares);
    }

    public SimpleSpider(SpiderHandler spiderHandler, HtmlContent htmlContent, DownloadsPipeline downloadsPipeline, ParseTypeEnum parseTypeEnum, List<RequestMiddleware> requestMiddlewares) {
        super(spiderHandler, htmlContent, downloadsPipeline, parseTypeEnum);
        this.registerGlobalRequestMiddleware(requestMiddlewares);
    }

}
