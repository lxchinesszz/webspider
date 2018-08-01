package org.spider.core.task;

import lombok.Builder;
import lombok.Data;
import org.spider.config.ParseTypeEnum;
import org.spider.core.pipeline.DownloadsPipeline;
import org.spider.core.SpiderHandler;
import org.spider.core.content.HtmlContent;
import org.spider.core.middleware.RequestMiddleware;

import java.util.List;
import java.util.concurrent.BlockingQueue;


/**
 * @author liuxin
 * @version Id: SpiderData.java, v 0.1 2018/7/28 下午8:11
 */
@Data
@Builder
public class SpiderTaskData {
    private SpiderHandler spiderHandler;
    private HtmlContent htmlContent;
    private ParseTypeEnum parseTypeEnum;
    private DownloadsPipeline downloadsPipeline;
    private BlockingQueue queue;
    private List<RequestMiddleware> globalRequestMiddleware;
}
