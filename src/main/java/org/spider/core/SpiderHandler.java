package org.spider.core;


/**
 * @author liuxin
 * @version Id: SpiderHandler.java, v 0.1 2018/7/26 下午9:45
 */
public interface SpiderHandler<T, D> {
    /** 需要开发者自己去实现的 **/

    /**
     * @param htmlDefinition 当前url页面文档
     *                       返回将要持久化的实体类
     * @return
     */
    T doCrawl(HtmlDefinition<D> htmlDefinition,WillRequests willRequests);

}
