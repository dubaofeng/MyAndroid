package com.dbf.javastudy.thread;

import java.sql.Connection;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockAndCondition {
    public void main() throws InterruptedException {
        Express express = new Express();
        ImplRunnable implRunnable = new ImplRunnable(express);
        ImplRunnable2 implRunnable2 = new ImplRunnable2(express);
        long start = System.currentTimeMillis();
        System.out.println("Thread.sleep(0)");
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(implRunnable);
            thread.setName("ThreadA" + i);
            thread.start();

        }
        Thread.sleep(1000);
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(implRunnable2);
            thread.setName("ThreadB" + i);
            thread.start();
        }

        Thread.sleep(5000);
        System.out.println("Thread.sleep(5000)=" + (System.currentTimeMillis() - start));
        express.changeDistance(101);
//        Thread.sleep(5000);
//        express.changeStation(Express.ST_beijing);

    }

    public class ImplRunnable implements Runnable {
        private Express express;

        public ImplRunnable(Express express) {
            this.express = express;
        }

        @Override
        public void run() {
            express.waitStation();
        }
    }

    public class ImplRunnable2 implements Runnable {
        private Express express;

        public ImplRunnable2(Express express) {
            this.express = express;
        }

        @Override
        public void run() {
            express.waitDistance();
        }
    }

    public class Express {
        public static final String ST_shanghai = "shanghai";
        public static final String ST_beijing = "beijing";
        private String currentStation = ST_shanghai;
        private int distance = 0;
        private Lock lock = new ReentrantLock();
        private Condition stationCondition = lock.newCondition();
        private Condition distanceCondition = lock.newCondition();

        public void changeStation(String station) {
            lock.lock();
            try {
                currentStation = station;
                stationCondition.signal();
            } finally {
                lock.unlock();
            }


        }

        public void changeDistance(int distance) {
            lock.lock();
            try {
                this.distance = distance;
                distanceCondition.signalAll();
                distanceCondition.signal();

            } finally {
                lock.unlock();

            }

        }

        public void waitStation() {
            lock.lock();
            try {
                while (currentStation.equals(ST_shanghai)) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "  waitStation");
                        stationCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + "  Station is Change");
            } finally {
                lock.unlock();

            }


        }

        public void waitDistance() {
            lock.lock();
            try {
                while (distance < 100) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "  waitDistance");
                        distanceCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + "   Distance is Change");
            } finally {
                lock.unlock();

            }

        }
    }
}
