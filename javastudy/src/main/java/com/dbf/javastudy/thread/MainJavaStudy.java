package com.dbf.javastudy.thread;

public class MainJavaStudy {

    public static void main(String[] args) throws InterruptedException {
//        new HelloThread().main();
//        new HelloThread2().main();
//        new WiatAndNotify().main();
//        new ThreadLocalStudy().main();
//        new MyLock().main();
//        new LockAndCondition().main();
        ThreadPool threadPool = new ThreadPool();

        for (int i = 0; i < 10; i++) {
            threadPool.execute(new ImplRunnable("Task" + i));
        }
    }

    public static class ImplRunnable implements Runnable {
        private String taskName;

        public ImplRunnable(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("complete:" + taskName);
        }
    }
}
