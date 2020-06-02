package com.dbf.javastudy.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class HelloThread {
    public  void main() throws InterruptedException {
        System.out.println("java Thread study");
        //Virtual machine thread management interface
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        //Gets thread management information
        ThreadInfo[] threadInfo = threadMXBean.dumpAllThreads(false, false);
        //Java  Inherent multithreading
        for (ThreadInfo info : threadInfo) {
            System.out.println("[" + info.getThreadId() + "]" + info.getThreadName());
        }
        System.out.println("");
        System.out.println("beautiful line--------------" + Thread.currentThread());
        System.out.println("");
        UserThread userThread = new UserThread();
        userThread.start();
        Thread.sleep(5);
        userThread.interrupt();

        ImplRunnable implRunnable = new ImplRunnable();
        new Thread(implRunnable).start();

        ImplCallable implCallable = new ImplCallable();
        FutureTask<String> futureTask = new FutureTask<>(implCallable);
        new Thread(futureTask).start();

        try {
            System.out.println("return==" + futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }


    //How create Thread?

    //extends Thread
    public static class UserThread extends Thread {

        @Override
        public void run() {
            super.run();
            System.out.println(Thread.currentThread() + "I am extends Thread"+isInterrupted());
            while (!isInterrupted()) {
//            while (true){
                System.out.println(Thread.currentThread() + "I am extends Thread  is Running");
            }
            System.out.println(Thread.currentThread() + "I am extends Thread  is Running" + isInterrupted());
        }
    }

    //implements Runnable interface
    public static class ImplRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread() + "I am implements Runnable");
        }
    }

    /*implements Callable  ，allow return value**/
    public static class ImplCallable implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println(Thread.currentThread() + "I am implements Callable");
            return "I am ImplCallable return value";
        }
    }
}
