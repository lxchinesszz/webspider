package org.spider.core.middleware;

import com.gargoylesoftware.htmlunit.WebClient;
import org.spider.exception.SpiderException;
import org.springframework.core.ResolvableType;

/**
 * @author liuxin
 * @version Id: AbstractRequestMiddleware.java, v 0.1 2018/7/30 下午5:32
 */
public abstract class AbstractRequestMiddleware<T> implements RequestMiddleware<T> {
    @Override
    public void customerRequest(T t) {
        checkResolvableType(t);
        doCustomerPostProcessRequest(t);
    }

    /**
     * 检查泛型是否一致
     *
     * @param t 请求的客户端
     */
    public void checkResolvableType(T t) {
        boolean isOk;
        ResolvableType resolvableType = ResolvableType.forClass(getClass());
        //当前类泛型,因为HtmlUnit库中WebClient和Spring库中WebClient名字一致，所以在这里判断下
        Class<?> resolve = resolvableType.getSuperType().getGeneric(0).resolve();
        //如果是
        if (t instanceof WebClient) {
            isOk = resolve.equals(t.getClass());
        } else {
            //因为Spring中WebClient的实例是DefaultWebClient所以要根据接口判断
            isOk = t.getClass().getInterfaces()[0].equals(resolve);
        }
        if (!isOk) {
            throw new SpiderException(this.getClass().getSimpleName() + "当前泛型为:" + resolve + "与" + t + "类型不一致,请保持一致");
        }
    }

    public abstract void doCustomerPostProcessRequest(T t);
}
