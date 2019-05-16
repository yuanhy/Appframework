package com.yuanhy.library_tools.task;

import java.util.concurrent.ThreadFactory;

/**
 * yuanhy
 */
public class BackgroundThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setPriority(Thread.MIN_PRIORITY);
        return t;
    }
}
