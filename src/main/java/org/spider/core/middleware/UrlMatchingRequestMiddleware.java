package org.spider.core.middleware;

/**
 * @author liuxin
 * @version Id: UrlMatchingRequestMiddleware.java, v 0.1 2018/7/27 上午9:49
 */
public interface UrlMatchingRequestMiddleware {
    /**
     * 添加匹配的请求中间件
     *
     * @param url               将要匹配的url
     * @param requestMiddleware 请求中间件
     */
    void registerMatchingRequestMiddleware(String url, RequestMiddleware requestMiddleware);

    /**
     * 移除某域名对应的中间件
     *
     * @param url 域名
     * @return
     */
    boolean removeRequestMiddleware(String url);
}
