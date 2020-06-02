package com.dbf.studyandtest.javastudy.thread;

public class ThreadLocalStudy {
    public void main() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            Thread.sleep(2000);
            Thread thread = new Thread(runnable);
            thread.setName("Thread" + i);
            thread.start();
        }
    }


    static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            for (int k = 0; k <3 ; k++) {
                int i = threadLocal.get();
                i++;
                threadLocal.set(i);
                System.out.println(Thread.currentThread().getName() + "  i=" + threadLocal.get());
            }

        }
    };

}
