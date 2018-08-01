package org.spider.core.middleware;

/**
 * 对请求做处理
 * @author liuxin
 * @version Id: RequestMiddleware.java, v 0.1 2018/7/26 下午5:59
 */
public interface RequestMiddleware<T> {
   void customerRequest(final T t);
}
