package org.spider.config;

import lombok.*;
import org.spider.core.pipeline.DownloadsPipeline;
import org.spider.core.content.HtmlContent;
import org.spider.core.middleware.RequestMiddleware;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author liuxin
 * @version Id: WebSpiderConfig.java, v 0.1 2018/7/26 下午7:04
 */
@Setter
@Getter
@Builder
public class WebSpiderConfig {
    /**
     * 默认的浏览器
     */
    private BrowserVersion browserTypeEnum;
    /**
     * 解析类型
     * 支持jsoup和xpath
     */
    private ParseTypeEnum parseTypeEnum;

    /**
     * 生成html原始文本,然后交给HtmlDefinitionParse解析最终生成Document对象
     */
    private HtmlContent htmlContent;

    /**
     * 请求中间件
     * 开发者可对请求中间件做定义
     */
    private List<RequestMiddleware> requestMiddlewares;

    /**
     * 下载中间件
     */
    private DownloadsPipeline downloadsPipeline;

    /**
     * 是否随机agent
     */
    private boolean isRandomAgent = false;

    private String bannerName = "bigMoneySe";


    @Override
    public String toString() {
        List<String> requestMiddlewareNames = new ArrayList<>();
        this.getRequestMiddlewares().forEach(new Consumer<RequestMiddleware>() {
            @Override
            public void accept(RequestMiddleware requestMiddleware) {
                requestMiddlewareNames.add(requestMiddleware.getClass().getSimpleName());
            }
        });
        return "WebSpiderConfig{" +
                "browserTypeEnum=" + browserTypeEnum +
                ", parseTypeEnum=" + parseTypeEnum +
                ", requestMiddlewares=" + requestMiddlewareNames +
                ", downloadsPipeline=" + downloadsPipeline.getClass().getSimpleName() +
                ", isRandomAgent=" + isRandomAgent +
                ",htmlContent="+htmlContent+"}";
    }
}
