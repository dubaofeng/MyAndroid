package com.dbf.studyandtest.javastudy.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MyLock {
    private int readCount = 10;
    private int writeCount = 3;

    public void main() {
        Merchandise merchandise = new Merchandise();
        WtiteRunnable wtiteRunnable = new WtiteRunnable(merchandise);
        ReadRunnable readRunnable = new ReadRunnable(merchandise);
        for (int i = 0; i < writeCount; i++) {
            Thread thread = new Thread(wtiteRunnable);
            thread.setName("writeThread" + i);
            thread.start();
            for (int j = 0; j < readCount; j++) {
                Thread thread2 = new Thread(readRunnable);
                thread2.setName("readThread" + j);
                thread2.start();
            }
        }

    }


    public class ReadRunnable implements Runnable {
        private Merchandise merchandise;

        public ReadRunnable(Merchandise merchandise) {
            this.merchandise = merchandise;
        }

        @Override
        public void run() {
            try {
                long start = System.currentTimeMillis();
                for (int i = 0; i < 100; i++) {
                    merchandise.getPrice();
                }
                System.out.println("ReadTime=" + (System.currentTimeMillis() - start));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    ;

    public class WtiteRunnable implements Runnable {

        private Merchandise merchandise;

        public WtiteRunnable(Merchandise merchandise) {
            this.merchandise = merchandise;
        }

        @Override
        public void run() {
            try {
                long start = System.currentTimeMillis();
                for (int i = 0; i < 50; i++) {
                    merchandise.setPrice();
                }

                System.out.println("WriteTime=" + (System.currentTimeMillis() - start));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    ;

    public class Merchandise {
        private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        private final Lock getLock = reentrantReadWriteLock.readLock();
        private final Lock setLock = reentrantReadWriteLock.writeLock();

        public  void setPrice() throws InterruptedException {

            setLock.lock();
            try {
                Thread.sleep(5);
            } finally {
                setLock.unlock();
            }
        }

        public  void getPrice() throws InterruptedException {
            getLock.lock();
            try {
                Thread.sleep(5);
            } finally {
                getLock.unlock();
            }


        }
    }

    private int count = 0;
    private Lock lock = new ReentrantLock();

    private void smple() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }


}
