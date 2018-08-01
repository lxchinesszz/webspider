package org.spider.queue;

import org.eclipse.jetty.util.BlockingArrayQueue;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;

/**
 * @author liuxin
 * @version Id: BlockQueueTest.java, v 0.1 2018/8/1 上午10:41
 */
public class BlockQueueTest {
    @Test
    public void queueTest()throws Exception{
        BlockingQueue bq=new BlockingArrayQueue();
        bq.add(1);
        bq.add(2);
        bq.add(3);
        System.out.println(bq.take());
        System.out.println(bq.take());
        System.out.println(bq.take());
    }
}
