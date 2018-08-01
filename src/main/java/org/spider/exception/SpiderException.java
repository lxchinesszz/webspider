package org.spider.exception;

/**
 * @author liuxin
 * @version Id: SpiderException.java, v 0.1 2018/7/26 下午10:04
 */
public class SpiderException extends RuntimeException {
    public SpiderException(String errorMsg){
        super(errorMsg);
    }
}
