package com.dbf.studyandtest.javastudy.thread;

public class WiatAndNotify {
    Thread thread1;
    Thread thread2;

    public void main() throws InterruptedException {
        System.out.println("WiatAndNotify");
        CountBean countBean = new CountBean();
        ImplRunnable implRunnable = new ImplRunnable(countBean);
        ImplRunnable2 implRunnable2 = new ImplRunnable2(countBean);
        thread1 = new Thread(implRunnable);
        thread1.setName("thread1");
        thread1.start();
        thread2 = new Thread(implRunnable2);
        thread2.setName("thread2");
        thread2.start();

    }

    public class CountBean {
        public static final int MaxCount = 5;
        private int i = 0;

        public synchronized void Iadd() {
            i++;
            System.out.println(Thread.currentThread().getName() + "  i=" + i);
            notifyAll();
        }

        public synchronized int getI() throws InterruptedException {
            while (i < MaxCount) {
                wait();
            }
            return i;
        }


    }

    public class ImplRunnable implements Runnable {
        private CountBean countBean;

        public ImplRunnable(CountBean countBean) {
            this.countBean = countBean;
        }

        @Override
        public void run() {

            for (int i = 0; i < CountBean.MaxCount; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countBean.Iadd();
            }


        }
    }

    public class ImplRunnable2 implements Runnable {
        private CountBean countBean;

        public ImplRunnable2(CountBean countBean) {
            this.countBean = countBean;
        }

        @Override
        public void run() {
            try {
                int i = countBean.getI();
                System.out.println(Thread.currentThread().getName() + "  保存i=" + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
