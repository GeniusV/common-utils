package com.geniusver.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by GeniusV on 6/21/20.
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
