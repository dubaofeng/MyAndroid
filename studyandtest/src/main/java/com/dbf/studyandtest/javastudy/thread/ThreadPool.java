package com.dbf.studyandtest.javastudy.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ThreadPool {
    private int coreThreadCount = 5;
    private int defaultTaskCount = 100;
    private WorkThread[] workThreads;
    private final BlockingQueue<Runnable> taskQueue;

    public ThreadPool() {
        taskQueue = new ArrayBlockingQueue<>(defaultTaskCount);
        workThreads = new WorkThread[coreThreadCount];
        for (int i = 0; i < coreThreadCount; i++) {
            workThreads[i] = new WorkThread();
            workThreads[i].start();
        }
        System.out.println("cpuCoreCount=" + Runtime.getRuntime().availableProcessors());
    }

    public void execute(Runnable task) {
        try {
            taskQueue.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        for (int i = 0; i < coreThreadCount; i++) {
            workThreads[i].interrupt();
            workThreads[i] = null;

        }
        taskQueue.clear();
    }

    public class WorkThread extends Thread {
        @Override
        public void run() {
            super.run();
            Runnable runnable = null;

            try {
                while (!isInterrupted()) {
                    runnable = taskQueue.take();
                    if (runnable != null) {
                        runnable.run();
                    }
                    runnable = null;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}
