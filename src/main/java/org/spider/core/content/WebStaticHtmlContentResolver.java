package org.spider.core.content;

import org.spider.core.middleware.RequestMiddleware;
import org.spider.core.task.SpiderLocation;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * 静态web网页数据
 * 使用WebClient请求客户端请求
 *
 * @author liuxin
 * @version Id: SimpleHtmlContent.java, v 0.1 2018/7/28 下午6:03
 */
public class WebStaticHtmlContentResolver extends AbstractHtmlContentResolver {
    @Override
    public String doHtmlFromLocation(SpiderLocation spiderLocation, List<RequestMiddleware> globalRequestMiddleware) {
        String location = spiderLocation.getLocation();
        WebClient webClient = WebClient.create(location);
        return webClient.get().retrieve().bodyToMono(String.class).block();
    }
}
