package com.geniusver.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * This queue is modified from tomcat's TaskQueue, which is used to change default ThreadPoolExecutor logic.
 *
 * With TaskQueue,
 * If current thread count < maximumPoolSize, ThreadPoolExecutor will use existed or create new thread to execute task until
 * thread number equals maximumPoolSize.
 * If current thread count >= maximumPoolSize, ThreadPoolExecutor will put task into queue.
 */
public class TaskQueue extends LinkedBlockingQueue<Runnable> {

    private static final long serialVersionUID = 1L;

    private transient volatile ThreadPoolExecutor parent = null;

    public TaskQueue() {

    }

    public TaskQueue(int capacity) {
        super(capacity);
    }

    public void setParent(ThreadPoolExecutor parent) {
        this.parent = parent;
    }

    @Override
    public boolean offer(Runnable o) {
        //we can't do any checks
        if (parent == null) return super.offer(o);
        //we are maxed out on threads, simply queue the object
        if (parent.getPoolSize() == parent.getMaximumPoolSize()) return super.offer(o);
        //if we have less threads than maximum force creation of a new thread
        if (parent.getPoolSize() < parent.getMaximumPoolSize()) return false;
        //if we reached here, we need to add it to the queue
        return super.offer(o);
    }
}
