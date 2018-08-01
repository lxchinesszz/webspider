package org.spider.core.task;

import lombok.extern.slf4j.Slf4j;
import org.spider.core.middleware.Order;
import org.spider.core.middleware.RequestMiddleware;
import org.spider.util.Console;
import org.spider.util.SpiderThreadExecutor;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;


/**
 * @author liuxin
 * @version Id: WebClientConsumer.java, v 0.1 2018/7/28 下午7:27
 */
@Slf4j
public class FileClientConsumer implements Runnable {
    private BlockingQueue<SpiderLocation> queue;
    private SpiderTaskData spiderTaskData;

    private volatile boolean isRunning = true;

    public FileClientConsumer(BlockingQueue<SpiderLocation> queue, SpiderTaskData spiderTaskData) {
        this.queue = queue;
        this.spiderTaskData = spiderTaskData;
    }

    @Override
    public void run() {
        try {
            while (isRunning) {
                SpiderLocation spiderLocation = queue.take();
                if (spiderLocation != null&&!spiderLocation.isIs()) {
                    SpiderTask spiderTask = new SpiderTask(spiderLocation, spiderTaskData);
                    SpiderThreadExecutor.submit(spiderTask, spiderTaskData.getDownloadsPipeline());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    private static Consumer customerRequest(WebClient webClient) {
        return new Consumer<RequestMiddleware<WebClient>>() {
            @Override
            public void accept(RequestMiddleware requestMiddleware) {
                int weight = requestMiddleware.getClass().getAnnotation(Order.class).weight();
                log.debug("当前请求处理中间件:{},权重数:{}", requestMiddleware.getClass().getSimpleName(), Console.yellows(weight + ""));
                requestMiddleware.customerRequest(webClient);
            }
        };
    }

    public void stop() {
        isRunning = false;
    }

}
