package org.spider.util;

import com.google.common.util.concurrent.*;
import io.netty.channel.ConnectTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.spider.core.pipeline.DownloadsPipeline;
import org.spider.core.task.SpiderTask;
import org.spider.exception.SpiderDownloadPipelineException;
import org.spider.exception.SpiderException;

import java.net.SocketException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liuxin
 * @version Id: SpiderThreadExecutor.java, v 0.1 2018/7/27 下午2:04
 */
@Slf4j
public class SpiderThreadExecutor {
    /**
     * 维护线程可见性
     */
    private static volatile ListeningExecutorService threadPoolExecutor;

    private static volatile AtomicInteger atomicInteger = new AtomicInteger(0);

    private static volatile boolean mainFlag = false;

    public static ListeningExecutorService getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

    static {
        if (threadPoolExecutor == null) {
            SpiderThreadPoolExecutor smileThreadPoolExecutor = new SpiderThreadPoolExecutor(new SpiderThreadFactory("spider"));
            ThreadPoolExecutor executorService = (ThreadPoolExecutor) smileThreadPoolExecutor.getExecutory();
            threadPoolExecutor = MoreExecutors.listeningDecorator(executorService);
        }
    }

    /**
     * JVM终止,立即关闭,并打印丢失任务数量
     */
    public static void close() {
        threadPoolExecutor.shutdownNow();
        log.info("关闭线程池,丢弃任务数:{}", atomicInteger.get());
    }

    public static boolean isComplete() {
        return atomicInteger.get() == 0;
    }


    private static void checkTask() {
        if (isComplete()) {
            mainFlag = false;
        }
    }

    public static int tackCount() {
        return atomicInteger.get();
    }

    public static boolean isMainFlag() {
        return mainFlag;
    }

    public static void execute(Runnable runnable) {
        mainFlag = true;
        threadPoolExecutor.execute(runnable);
    }

    public static void submit(final SpiderTask task, final DownloadsPipeline downloadsPipeline) {
        atomicInteger.getAndIncrement();
        if (threadPoolExecutor == null) {
            SpiderThreadPoolExecutor smileThreadPoolExecutor = new SpiderThreadPoolExecutor(new SpiderThreadFactory("Smile"));
            ThreadPoolExecutor executorService = (ThreadPoolExecutor) smileThreadPoolExecutor.getExecutory();
            threadPoolExecutor = MoreExecutors.listeningDecorator(executorService);
        }
        /**
         * 处理完成任务如果任务完成就,渲染出去
         */
        ListenableFuture<Object> listenableFuture = threadPoolExecutor.submit(task);

        Futures.addCallback(listenableFuture, new FutureCallback<Object>() {
            @Override
            public void onSuccess(Object object) {
                try {
                    if (downloadsPipeline.checkPersistence(object)) {
                        downloadsPipeline.persistence(object);
                        task.getSpiderLocation().setIs(true);
                    }
                } catch (Exception e) {
                    throw new SpiderDownloadPipelineException("DownloadPipeline 处理异常:" + e.getMessage());
                } finally {
                    atomicInteger.getAndDecrement();
                    checkTask();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                atomicInteger.getAndDecrement();
                task.getSpiderLocation().setIs(true);
                checkTask();
                t.printStackTrace();
                throw new SpiderException(t.getMessage());
            }
        }, threadPoolExecutor);


    }
}
