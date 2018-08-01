package org.spider.core.middleware;

import java.lang.annotation.*;

/**
 * @author liuxin
 * @version Id: SpiderOrder.java, v 0.1 2018/7/27 上午9:14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Order {
    /**
     * 处理器的添加顺序(默认处理顺序从小到大)
     * 数值越大,先执行。
     *
     * @return
     */
    int weight() default 1000;
}
