package com.yuanhy.library_tools.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * yuanhy
 */
public class ExecutorTasks implements Tasks {
    ExecutorService fixedThreadPool;
    public ExecutorTasks()
    {
         fixedThreadPool = Executors.newFixedThreadPool(4,new BackgroundThreadFactory());
    }
    @Override
    public void isExitTasks() {
    }

    @Override
    public void exitTasks() {
        if(fixedThreadPool != null){
            fixedThreadPool.shutdown();
        }
    }

    @Override
    public void postRunnable(Runnable _run) {
        if(fixedThreadPool!=null) {
            fixedThreadPool.execute(_run);
        }
    }
}
