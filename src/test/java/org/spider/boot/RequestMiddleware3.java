package org.spider.boot;

import org.spider.core.middleware.Order;
import org.spider.core.middleware.RequestMiddleware;
import org.spider.util.Console;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author liuxin
 * @version Id: RequestMiddleware1.java, v 0.1 2018/7/27 上午11:13
 */
@Order(weight = 1)
public class RequestMiddleware3 implements RequestMiddleware<WebClient> {
    @Override
    public void customerRequest(WebClient s) {
        Console.log("\"经过第三次对请求处理\")");
    }
}
