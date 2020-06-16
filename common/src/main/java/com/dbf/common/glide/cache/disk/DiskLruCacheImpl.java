package com.dbf.common.glide.cache.disk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.dbf.common.glide.Tool;
import com.dbf.common.glide.resource.Value;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by dbf on 2020/6/3
 * describe:将图片缓存到磁盘
 */
public class DiskLruCacheImpl {
    private final String TAG = DiskLruCacheImpl.class.getCanonicalName();
    //磁盘缓存目录
    private final String DISK_CACHE_DIR = "disk_lru_cache_dir";
    //版本号，若修改，之前的缓存失效
    private final int VERSION = 1;
    private final int VALUE_COUNT = 1; // 通常情况下都是1
    //缓存最大值，之后修改成可设置的
    private final long MAX_SIZE = 1024 * 1024 * 10;
    private DiskLruCache diskLruCache;

    public DiskLruCacheImpl(Context context) {
        //SD 路径
        //File file = new File(Environment.getExternalStorageDirectory() + File.separator + DISK_CACHE_DIR);
        //10.0后只能沙盒访问
        File file = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES + File.separator + DISK_CACHE_DIR);
        try {
            diskLruCache = DiskLruCache.open(file, VERSION, VALUE_COUNT, MAX_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void put(String key, Value value) {
        Tool.checkNotEmpty(key);
        DiskLruCache.Editor editor = null;
        OutputStream outputStream = null;
        try {
            editor = diskLruCache.edit(key);
            outputStream = editor.newOutputStream(0);//index不能大于
            Bitmap bitmap = value.getmBitmap();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);//把bitmap写入outputStream
            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
            try {
                editor.abort();
            } catch (IOException ex) {
                ex.printStackTrace();
                Log.e(TAG, "put: ", ex);
            }
        } finally {
            try {
                editor.commit();
                diskLruCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "put: ", e);
            }
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "put: ", e);
                }
            }
        }
    }

    public Value get(String key) {
        Tool.checkNotEmpty(key);
        InputStream inputStream = null;
        try {
            DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
            if (null != snapshot) {
                Value value = Value.getInstance();
                inputStream = snapshot.getInputStream(0);//index不能大于0
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                value.setKey(key);
                value.setmBitmap(bitmap);
                return value;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
