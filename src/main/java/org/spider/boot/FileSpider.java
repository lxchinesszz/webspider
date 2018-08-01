package org.spider.boot;

import org.spider.config.ParseTypeEnum;
import org.spider.core.DefaultWebKernelSpider;
import org.spider.core.SpiderHandler;
import org.spider.core.content.HtmlContent;
import org.spider.core.content.HtmlFileContentResolver;
import org.spider.core.middleware.RandomAgentRequestMiddleware;
import org.spider.core.pipeline.DownloadsPipeline;
import org.spider.core.pipeline.PrintDownloadsPipeline;

import java.util.Arrays;

/**
 * @author liuxin
 * @version Id: FileSpider2.java, v 0.1 2018/7/30 下午6:10
 */
public class FileSpider extends DefaultWebKernelSpider {

    public FileSpider(SpiderHandler spiderHandler) {
        this(spiderHandler, new PrintDownloadsPipeline(), new HtmlFileContentResolver());
    }

    public FileSpider(SpiderHandler spiderHandler, DownloadsPipeline downloadsPipeline) {
        this(spiderHandler, downloadsPipeline, new HtmlFileContentResolver());
    }

    public FileSpider(SpiderHandler spiderHandler, DownloadsPipeline downloadsPipeline, HtmlContent htmlContent) {
        super(spiderHandler, htmlContent, downloadsPipeline, ParseTypeEnum.JSOUP);
        this.registerGlobalRequestMiddleware(Arrays.asList(new RandomAgentRequestMiddleware()));

    }


}
