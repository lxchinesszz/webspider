package org.spider.core.middleware;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author liuxin
 * @version Id: DefaultWeightRequestConfigRegisterImpl.java, v 0.1 2018/7/27 上午9:37
 */
public class DefaultWeightRequestMiddlewareRegisterImpl extends AbstractWeightRequestMiddlewareRegister {

    public DefaultWeightRequestMiddlewareRegisterImpl(RequestConfigRegister requestConfigRegister) {
        super(requestConfigRegister);
    }

    @Override
    public List<RequestMiddleware> doRegisterGlobalRequestMiddleware(List<RequestMiddleware> requestMiddlewares) {
        Collections.sort(requestMiddlewares, new Comparator<RequestMiddleware>() {
            @Override
            public int compare(RequestMiddleware o1, RequestMiddleware o2) {
                Order frontOrder = o1.getClass().getAnnotation(Order.class);
                Order backOrder = o2.getClass().getAnnotation(Order.class);
                int frontWeight = 0;
                int backWeight = 0;
                if (null != frontOrder) {
                    frontWeight = frontOrder.weight();
                }
                if (null != backOrder) {
                    backWeight = backOrder.weight();
                }
                return backWeight-frontWeight;
            }
        });
        return requestMiddlewares;
    }


}
