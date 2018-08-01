package org.spider.core;

import lombok.extern.slf4j.Slf4j;
import org.spider.core.task.SpiderLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

/**
 * @author liuxin
 * @version Id: WillRequests.java, v 0.1 2018/7/28 下午6:52
 */
@Slf4j
public class WillRequests {
    private BlockingQueue<SpiderLocation> queue;
    private Object urlMonitor = new Object();
    private static volatile List<String> historyUrl = new ArrayList<>();

    public WillRequests(BlockingQueue queue) {
        this.queue = queue;
    }

    public void add(String url) {
        synchronized (urlMonitor) {
            if (!historyUrl.contains(url)) {
                historyUrl.add(url);
                queue.add(new SpiderLocation(UUID.randomUUID().toString(), url));
                log.info("url:{},任务添加成功", url);
            }
        }
    }
    public void addAll(List<String>urls){
        urls.stream().forEach(x->add(x));
    }
}
