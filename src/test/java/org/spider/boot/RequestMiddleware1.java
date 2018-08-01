package org.spider.boot;

import org.spider.core.middleware.Order;
import org.spider.core.middleware.RequestMiddleware;
import org.spider.util.Console;
import org.springframework.core.ResolvableType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author liuxin
 * @version Id: RequestMiddleware1.java, v 0.1 2018/7/27 上午11:13
 */
@Order(weight = 10)
public class RequestMiddleware1 implements RequestMiddleware<WebClient> {
    @Override
    public void customerRequest(WebClient s) {
        Console.log("经过第一次对请求处理");
    }

    public static void main(String[] args) {
        ResolvableType resolvableType = ResolvableType.forClass(RequestMiddleware1.class);
        System.out.println(resolvableType);
        Class<?> resolve = resolvableType.getInterfaces()[0].getGeneric(0).resolve();
        System.out.println(resolve);
    }
}
