package com.dbf.common.glide;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.widget.ImageView;

import com.dbf.common.glide.cache.ActiveCache;
import com.dbf.common.glide.cache.MemoryCache;
import com.dbf.common.glide.cache.MemoryCacheCallback;
import com.dbf.common.glide.cache.disk.DiskLruCacheImpl;
import com.dbf.common.glide.fragment.LifecycleCallback;
import com.dbf.common.glide.load_data.LoadDataManager;
import com.dbf.common.glide.load_data.ResponListener;
import com.dbf.common.glide.resource.Key;
import com.dbf.common.glide.resource.Value;
import com.dbf.common.glide.resource.ValueCallback;

public
/**
 *Created by dbf on 2020/6/7 
 *describe:
 */
class RequestTargetEngine implements LifecycleCallback, ValueCallback, MemoryCacheCallback, ResponListener {
    private final String TAG = RequestTargetEngine.class.getCanonicalName();
    private ActiveCache activeCache;
    private MemoryCache memoryCache;
    private DiskLruCacheImpl diskLruCache;
    private Context context;
    private ImageView imageView;
    private String key;
    private String path;
    //Glide获取内存的八分之一
    private final int MEMORY_MAX_SIXE = 1024 * 1024 * 60;

    public RequestTargetEngine(Context context) {
        this.context = context;
        if (null == activeCache) {
            activeCache = new ActiveCache(this);
        }
        if (null == memoryCache) {
            memoryCache = new MemoryCache(MEMORY_MAX_SIXE);
            memoryCache.setMemoryCacheCallback(this);
        }
        diskLruCache = new DiskLruCacheImpl(context);
    }

    public void into(ImageView imageView) {
        this.imageView = imageView;
        Tool.checkNotEmpty(imageView);
        Tool.assertMainThread();//非主线程 抛出异常
//触发缓存
        Value value = cacheAction();
        if (null != value) {
            value.noUseAction();//使用完了-1
            imageView.setImageBitmap(value.getmBitmap());
        }
    }

    /**
     * TODO 加载资源 ---》缓存机制 ---》网络/sd/加载资源成功后 ---》把资源保存到缓存中
     */
    private Value cacheAction() {
        Value value = activeCache.get(key);
        if (null != value) {
            Log.d(TAG, "cacheAction: 本次加载资源是《活动》缓存中的资源");
            value.useAction();//使用+1
            return value;
        }
        value = memoryCache.get(key);
        if (null != value) {
            Log.d(TAG, "cacheAction: 本次加载资源时《内存》缓存中的资源");
            memoryCache.shoudongRemove(key);//移除内存缓存中的元素
            activeCache.put(key, value);//将元素添加到活动缓存中
            value.useAction();//使用+1
            return value;
        }
        value = diskLruCache.get(key);
        if (null != value) {
            Log.d(TAG, "cacheAction: 本次加载资源时《磁盘》缓存中的资源");
            activeCache.put(key, value);
            //可能会后续扩展
            value.useAction();//使用+1
            return value;
        }
        value = new LoadDataManager().loadResource(path, this, context);
        if (null != value) {
            return value;
        }
        return null;
    }

    public void loadValueInitAction(String path, Context context) {
        this.path = path;
        this.context = context;
        this.key = new Key(path).getKey();
    }

    @Override
    public void entryRemoveMemoryCache(String key, Value oldValue) {
//内存缓存中的资源经过LUR算法后被移除回调到这里，这里应该添加到磁盘？
    }

    @Override
    public void glideInitAction() {
        Log.d(TAG, "glideInitAction: Glide生命周期已经开始初始化了");
    }

    @Override
    public void glideStopAcyion() {
        Log.d(TAG, "glideInitAction: Glide生命周期已经停止");
    }

    @Override
    public void glideRecycleAction() {
        Log.d(TAG, "glideInitAction: Glide生命周期释放操作");
        if (null != activeCache) {
            activeCache.closeThread();
        }
    }

    @Override
    public void responseSuccess(Value value) {
//外置资源加载成功回调
        if (null != value) {
            saveCache(key, value);
            imageView.setImageBitmap(value.getmBitmap());

        }
    }

    private void saveCache(String key, Value value) {
        value.setKey(key);
        if (null != diskLruCache) {
            diskLruCache.put(key, value);
        }
    }

    @Override
    public void responseException(Exception e) {
//外置资源加载失败
        Log.d(TAG, "responseException: 加载外部资源失败：" + e.getMessage());
    }

    @Override
    public void valaueNonUseListener(String key, Value value) {
        //活动缓存中没有用到的资源，添加到内存缓存中
        if (null != key && null != value) {
            memoryCache.put(key, value);
        }
    }
}
