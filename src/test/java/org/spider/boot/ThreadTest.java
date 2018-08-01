package org.spider.boot;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author liuxin
 * @version Id: ThreadTest.java, v 0.1 2018/7/27 下午8:31
 */
public class ThreadTest {
    private static volatile int n = 0;

    public static void main(String[] args) {
        ExecutorService executorService =
                Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(n++);
                }
            });
        }
        System.out.println(executorService.isTerminated());
        System.out.println(executorService.isTerminated());
        System.out.println(executorService.isTerminated());
        System.out.println(executorService.isTerminated());
        executorService.shutdown();
        System.out.println(executorService.isTerminated());

    }
}
