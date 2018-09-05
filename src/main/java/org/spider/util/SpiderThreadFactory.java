package org.spider.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liuxin
 * @version Id: SpiderThreadFactory.java, v 0.1 2018/7/27 下午6:07
 */
public class SpiderThreadFactory implements ThreadFactory {
    /**
     * 原子操作保证每个线程都有唯一的
     */
    private static final AtomicInteger poolId = new AtomicInteger(1);

    private final AtomicInteger nextId = new AtomicInteger(1);

    private final String prefix;

    private final boolean daemonThread;

    private final ThreadGroup threadGroup;

    public SpiderThreadFactory() {
        this("smile-server-threadpool-" + poolId.getAndIncrement(), false);
    }

    public SpiderThreadFactory(String poolName) {
        this(poolName + "-server-threadpool-" + poolId.getAndIncrement(), false);
    }

    public SpiderThreadFactory(Class cls) {
        this(cls.getSimpleName(), false);
    }


    public SpiderThreadFactory(String prefix, boolean daemon) {
        this.prefix = null != prefix ? prefix + "-thread-" : "";
        daemonThread = daemon;
        SecurityManager s = System.getSecurityManager();
        threadGroup = (s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(threadGroup, r, prefix + nextId.getAndIncrement());
    }

}
