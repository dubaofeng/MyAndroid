package com.dbf.common.glide.resource;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.PrintStreamPrinter;

import com.dbf.common.glide.Tool;
import com.dbf.common.myutils.MyLog;

/**
 * Created by dbf on 2020/6/3
 * describe:
 */
public class Value {
    private final String TAG = Value.class.getCanonicalName();
    // 单例模式
    private static Value instance;

    private Value() {
    }

    public static Value getInstance() {
        if (null == instance) {
            synchronized (Value.class) {
                if (null == instance) {
                    instance = new Value();
                }
            }
        }
        return instance;
    }

    private Bitmap mBitmap;
    //使用计数
    private int count;
    private ValueCallback valueCallback;
    //唯一标记
    private String key;

    /**
     * 使用计数+1
     */
    public void useAction() {
        //如果mBitmap为null，抛出异常
        Tool.checkNotEmpty(mBitmap);
        if (mBitmap.isRecycled()) {
            MyLog.INSTANCE.d(TAG, "bitmap已经被回收了");
            return;
        }
        count++;
        MyLog.INSTANCE.d(TAG, "count++=" + count);
    }

    /**
     * 使用完成计数-1
     */
    public void noUseAction() {
        count--;
        if (count <= 0 && null != valueCallback) {
            //value没有使用 回调告知要回收了,活动缓存监听
            valueCallback.valaueNonUseListener(key, this);
        }
        MyLog.INSTANCE.d(TAG, "count--=" + count);
    }

    public void recycleBitmap() {
        if (0 < count) {
            Log.d(TAG, "recycleBitmap: 正在使用，不能回收");
            return;
        }
        if (mBitmap.isRecycled()) {
            Log.d(TAG, "recycleBitmap: 已经回收了");
            return;
        }
        //。。其他条件
        mBitmap.recycle();
        instance = null;
        System.gc();
    }

    public Bitmap getmBitmap() {
        return mBitmap;
    }

    public void setmBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ValueCallback getValueCallback() {
        return valueCallback;
    }

    public void setValueCallback(ValueCallback valueCallback) {
        this.valueCallback = valueCallback;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
