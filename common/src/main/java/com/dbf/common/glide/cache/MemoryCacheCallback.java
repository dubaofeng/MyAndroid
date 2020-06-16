package com.dbf.common.glide.cache;

import com.dbf.common.glide.resource.Value;

/**
 * Created by  on 2020/6/3
 * describe:
 */
public interface MemoryCacheCallback {
    //移除内存缓存中的key-value
    void entryRemoveMemoryCache(String key, Value oldValue);
}
