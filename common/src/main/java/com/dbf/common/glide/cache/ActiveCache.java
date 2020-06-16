package com.dbf.common.glide.cache;

import com.dbf.common.glide.Tool;
import com.dbf.common.glide.resource.Value;
import com.dbf.common.glide.resource.ValueCallback;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * 活动缓存-》正在使用的资源
 */
public class ActiveCache {
    //容器
    private Map<String, WeakReference<Value>> maps = new HashMap<>();
    //监听弱引用
    private ReferenceQueue<Value> queue;
    //阻塞循环
    private Thread thread;
    //关闭阻塞循环线程的标记
    private boolean isCloseThread;
    //手动删除标志位
    private boolean isShoudongRemove;
    //资源释放监听
    private ValueCallback valueCallback;

    public ActiveCache(ValueCallback valueCallback) {
        this.valueCallback = valueCallback;
    }

    //添加活动缓存
    public void put(String key, Value value) {
        Tool.checkNotEmpty(key);
        value.setValueCallback(valueCallback);
        maps.put(key, new CustomWeakReference(value, getQueue(), key));

    }

    public Value get(String key) {
        WeakReference<Value> reference = maps.get(key);
        if (null != reference) {
            return reference.get();
        }
        return null;
    }

    public Value remove(String key) {
        isShoudongRemove = true;
        WeakReference<Value> reference = maps.remove(key);
        isShoudongRemove = false;
        if (null != reference) {
            return reference.get();
        }
        return null;
    }

    public void closeThread() {
        isCloseThread = true;

        // 中断线程 -- 高版本有问题，所以注释
        /*if (thread != null) {
            thread.interrupt(); // 中断线程

            try {
                thread.join(TimeUnit.SECONDS.toMillis(5)); // 线程稳定的停止下了

                if (thread.isAlive()) { // 5秒 还听不下了
                    throw new IllegalStateException("活动缓存中，关闭线程，无法停止下了");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        maps.clear();
        System.gc();
    }

    private ReferenceQueue<? super Value> getQueue() {
        if (null == queue) {
            queue = new ReferenceQueue<>();
            thread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    while (!isCloseThread) {
                        if (!isShoudongRemove) {
                            //如果不是手动则自动干活
                            try {
                                Reference<? extends Value> reference = queue.remove();
                                CustomWeakReference weakReference = (CustomWeakReference) reference;
                                if (null != maps && maps.isEmpty()) {
                                    //移除容器里对应的元素
                                    maps.remove(weakReference.key);
                                }
                                //。。。

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            };
            thread.start();
        }
        return queue;
    }

    //用于监听什么时候被回收
    public class CustomWeakReference extends WeakReference<Value> {
        private String key;

        public CustomWeakReference(Value referent, ReferenceQueue<? super Value> q, String key) {
            super(referent, q);
        }
    }
}
