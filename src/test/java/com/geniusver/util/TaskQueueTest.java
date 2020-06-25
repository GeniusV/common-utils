package com.geniusver.util;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by GeniusV on 6/21/20.
 */
class TaskQueueTest {

    @Test
    public void testTaskQueue() throws InterruptedException {
        TaskQueue taskQueue = new TaskQueue();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.SECONDS, taskQueue);
        taskQueue.setParent(threadPoolExecutor);
        int taskSize = 20;

        for (int i = 0; i < 20; i++) {
            int finalI = i;
            threadPoolExecutor.submit(() -> {
                try {
                    Thread.sleep(20000);
                    System.out.println("Thread: " + finalI + " finished");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("pool size: " + threadPoolExecutor.getPoolSize() + ", queue size: " + taskQueue.size());
            Thread.sleep(100);
        }

    }
}