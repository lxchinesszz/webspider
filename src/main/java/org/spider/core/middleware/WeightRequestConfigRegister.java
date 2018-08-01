package org.spider.core.middleware;


import java.util.List;

/**
 * @author liuxin
 * @version Id: WeightRequestConfigRegister.java, v 0.1 2018/7/27 上午9:39
 */
public interface WeightRequestConfigRegister {
    /**
     * 添加全局的中间件
     *
     * @param requestMiddlewares
     * @return
     */
    boolean registerGlobalRequestMiddleware(List<RequestMiddleware> requestMiddlewares);

    /**
     * 获取全局中间件
     *
     * @return
     */
    List<RequestMiddleware> getGlobalRequestMiddleware();
}
