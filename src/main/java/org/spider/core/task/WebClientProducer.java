package org.spider.core.task;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author liuxin
 * @version Id: WebClientProducer.java, v 0.1 2018/7/28 下午7:27
 */
public class WebClientProducer implements Runnable {
    private volatile boolean isRunning = true;
    private BlockingQueue<String> queue;// 内存缓冲区
    private static final int SLEEPTIME = 1000;
    private String url;

    public WebClientProducer(BlockingQueue<String> queue,String url) {
        this.queue = queue;
        this.url=url;
    }

    @Override
    public void run() {
        Random r = new Random();
        System.out.println("start producting id:" + Thread.currentThread().getId());
        try {
            while (isRunning) {
                System.out.println(url + " 加入队列");
                if (!queue.offer(url, 2, TimeUnit.SECONDS)) {
                    System.err.println(" 加入队列失败");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

    }

    public void stop() {
        isRunning = false;
    }
}
