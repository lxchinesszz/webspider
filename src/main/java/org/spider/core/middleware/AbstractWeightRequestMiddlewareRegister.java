package org.spider.core.middleware;


import java.util.List;

/**
 * 根据权重来添加
 *
 * @author liuxin
 * @version Id: WeightRequestConfigRegisterImpl.java, v 0.1 2018/7/27 上午9:19
 */
public abstract class AbstractWeightRequestMiddlewareRegister implements WeightRequestConfigRegister {

    public RequestConfigRegister requestConfigRegister;

    public AbstractWeightRequestMiddlewareRegister(RequestConfigRegister requestConfigRegister) {
        this.requestConfigRegister = requestConfigRegister;
    }

    @Override
    public boolean registerGlobalRequestMiddleware(List<RequestMiddleware> requestMiddlewares) {
        return requestConfigRegister.registerGlobalRequestMiddleware(doRegisterGlobalRequestMiddleware(requestMiddlewares));
    }


    /**
     * KernelSpider 实现
     * @return
     */
    @Override
    public List<RequestMiddleware> getGlobalRequestMiddleware() {
        return null;
    }

    /**
     * 由子类去实现,子类需要实现排序的功能,设置权重
     *
     * @param requestMiddlewares
     * @return
     */
    public abstract List<RequestMiddleware> doRegisterGlobalRequestMiddleware(List<RequestMiddleware> requestMiddlewares);
}
