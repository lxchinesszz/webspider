package org.spider.core;

import lombok.extern.slf4j.Slf4j;
import org.spider.config.ParseTypeEnum;
import org.spider.core.content.HtmlContent;
import org.spider.core.pipeline.DownloadsPipeline;
import org.spider.core.task.SpiderLocation;
import java.util.List;
import java.util.UUID;

/**
 * @author liuxin
 * @version Id: WebKernelSpider.java, v 0.1 2018/7/28 下午11:16
 */
@Slf4j
public class DefaultWebKernelSpider extends KernelSpider implements CrawlSupport {



    public DefaultWebKernelSpider(SpiderHandler spiderHandler, HtmlContent htmlContent, DownloadsPipeline downloadsPipeline, ParseTypeEnum parseTypeEnum) {
        super(spiderHandler, htmlContent, downloadsPipeline, parseTypeEnum);
    }

    @Override
    public void requests(List<String> urls) {
        for (String url : urls) {
            super.spiderTaskQueue.add(new SpiderLocation(UUID.randomUUID().toString(),url));
        }
    }

}
