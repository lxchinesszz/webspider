package org.spider.core.content;

import org.spider.core.middleware.RequestConfigRegister;
import org.spider.core.middleware.RequestMiddleware;
import org.spider.core.task.SpiderLocation;

import java.util.List;

/**
 * @author liuxin
 * @version Id: HtmlContext.java, v 0.1 2018/7/26 下午4:15
 */
public interface HtmlContent {
    /**
     * 获取网页原始数据
     *
     * @param spiderLocation        目标地址
     * @param requestConfigRegister 全局的中间件注册器
     * @return 目标页面html文本
     */
    String getHtmlText(SpiderLocation spiderLocation, RequestConfigRegister requestConfigRegister);
}
