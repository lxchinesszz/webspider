package org.spider.core.pipeline;

/**
 * @author liuxin
 * @version Id: DownloadsPipeline.java, v 0.1 2018/7/26 下午8:44
 */
public interface DownloadsPipeline<T> {
    void persistence(T t);

    boolean checkPersistence(T t);
}
