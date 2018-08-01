package org.spider.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.spider.core.task.SpiderLocation;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author liuxin
 * @version Id: WebClientHolder.java, v 0.1 2018/7/26 下午6:47
 */
@Data
@AllArgsConstructor
public class WebClientHolder {
    /**
     * 请求实例
     */
    WebClient webClient;
    /**
     * 请求url
     */
    SpiderLocation spiderLocation;
}
