package org.spider.core.pipeline;

import org.spider.util.Console;

/**
 *
 * @author liuxin
 * @version Id: PrintDownloadsPipeline.java, v 0.1 2018/7/27 上午10:46
 */
public class PrintDownloadsPipeline implements DownloadsPipeline<Object> {
    @Override
    public void persistence(Object s) {
        Console.customerNormal(String.format("PrintDownloadsPipeline print,Type:%s", s.getClass().getSimpleName()), s);
    }

    @Override
    public boolean checkPersistence(Object s) {
        return null != s;
    }
}
