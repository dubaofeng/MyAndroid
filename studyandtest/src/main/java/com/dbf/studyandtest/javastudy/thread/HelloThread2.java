package com.dbf.studyandtest.javastudy.thread;

public class HelloThread2 {
    public void main() throws InterruptedException {
        Simple simple = new Simple();
        ImplRunnable runnable = new ImplRunnable(simple);
        ImplRunnable2 runnable2 = new ImplRunnable2(simple);
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable2);
        thread1.start();
        thread2.start();
        Thread.sleep(20);
//        simple.println();

    }

    public class ImplRunnable implements Runnable {
        private Simple simple;

        public ImplRunnable(Simple simple) {
            this.simple = simple;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                simple.countAdd();
            }

        }
    }

    public class ImplRunnable2 implements Runnable {
        private Simple simple;

        public ImplRunnable2(Simple simple) {
            this.simple = simple;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                simple.countAdd2();
            }

        }
    }

    public class Simple {
        private int count = 0;
        private int count2 = 0;


        public void countAdd() {
            synchronized (this) {
                count++;
                System.out.println("count=" + count);
            }
        }

        Object object = new Object();

        public void countAdd2() {
            synchronized (object) {
                count2++;
                System.out.println("count2=" + count2);
            }
        }

        public void println() {
            System.out.println("count=" + count);
            System.out.println("count2=" + count2);
        }
    }

    public static class Simple2 {
        private int count = 0;


        public static synchronized void countAdd() {
        }


        public void println() {
            System.out.println("count=" + count);
        }
    }
}
