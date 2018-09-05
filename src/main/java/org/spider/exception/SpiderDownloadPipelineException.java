package org.spider.exception;

/**
 * @author liuxin
 * @version Id: SpiderDownloadPepelineException.java, v 0.1 2018/7/27 下午9:24
 */
public class SpiderDownloadPipelineException extends RuntimeException  {
    public SpiderDownloadPipelineException(String message) {
        super(message);
    }

    public SpiderDownloadPipelineException(String message, Throwable cause) {
        super(message, cause);
    }
}
