package org.spider.core.content;

import lombok.extern.slf4j.Slf4j;
import org.spider.core.middleware.RequestConfigRegister;
import org.spider.core.middleware.RequestMiddleware;
import org.spider.core.task.SpiderLocation;
import org.spider.util.Console;

import java.util.List;

/**
 * @author liuxin
 * @version Id: AbstractHtmlContent.java, v 0.1 2018/7/30 下午3:58
 */
@Slf4j
public abstract class AbstractHtmlContentResolver implements HtmlContent {
    @Override
    public String getHtmlText(SpiderLocation spiderLocation, RequestConfigRegister requestConfigRegister) {
        log.info(Console.yellows(this.getClass().getSimpleName() + ".doHtmlFromLocation").toString());
        String htmlPage = doHtmlFromLocation(spiderLocation,requestConfigRegister.getGlobalRequestMiddleware());
        log.debug(htmlPage);
        return htmlPage;
    }

    abstract String doHtmlFromLocation(SpiderLocation spiderLocation, List<RequestMiddleware> globalRequestMiddleware);
}
