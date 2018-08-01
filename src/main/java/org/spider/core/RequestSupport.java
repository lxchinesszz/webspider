package org.spider.core;
import java.util.List;

/**
 * @author liuxin
 * @version Id: RequestSupport.java, v 0.1 2018/7/28 下午12:55
 */
public interface RequestSupport {
    /**
     * 提交任务
     * @param url
     */
    void requests(List<String> url);
}
