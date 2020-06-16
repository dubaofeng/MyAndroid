package com.dbf.common.glide.cache;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

import com.dbf.common.glide.resource.Value;

/**
 * Created by dbf on 2020/6/3
 * describe:内存缓存 LRU 最少使用
 */

public class MemoryCache extends LruCache<String, Value> {
    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public MemoryCache(int maxSize) {
        super(maxSize);
    }

    private MemoryCacheCallback memoryCacheCallback;
    private boolean isShoudongRemove;

    public void setMemoryCacheCallback(MemoryCacheCallback memoryCacheCallback) {
        this.memoryCacheCallback = memoryCacheCallback;
    }

    public Value shoudongRemove(String key) {
        isShoudongRemove = true;
        Value value = remove(key);
        isShoudongRemove = false;
        return value;
    }

    @Override
    protected void entryRemoved(boolean evicted, String key, Value oldValue, Value newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
        //被lru算法移除,排除手动移除
        if (null != memoryCacheCallback && !isShoudongRemove) {
            memoryCacheCallback.entryRemoveMemoryCache(key, oldValue);
        }
    }

    @Override
    protected int sizeOf(String key, Value value) {

        Bitmap bitmap = value.getmBitmap();
        // 最开始的时候
        // int result = bitmap.getRowBytes();

        // API 12  3.0
        // int result = bitmap.getByteCount();

        // API  19 4.4
        // int result = bitmap.getAllocationByteCount();
        int intSdk = Build.VERSION.SDK_INT;
        if (intSdk >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        }
        return bitmap.getByteCount();
    }
}
