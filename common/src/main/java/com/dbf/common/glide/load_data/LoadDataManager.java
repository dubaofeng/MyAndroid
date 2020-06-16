package com.dbf.common.glide.load_data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import com.dbf.common.glide.resource.Value;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public
/**
 *Created by dbf on 2020/6/7 
 *describe:
 */
class LoadDataManager implements ILoadData, Runnable {
    private final String TAG = LoadDataManager.class.getCanonicalName();
    private String path;
    private ResponListener responListener;
    private Context context;

    @Override
    public Value loadResource(String path, ResponListener responListener, Context context) {
        this.path = path;
        this.responListener = responListener;
        this.context = context;
        //加载本地 或 网络 图片
        Uri uri = Uri.parse(path);
        if ("HTTP".equalsIgnoreCase(uri.getScheme()) || "HTTPS".equalsIgnoreCase(uri.getScheme())) {
//异步
            new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60,
                    TimeUnit.SECONDS, new SynchronousQueue<Runnable>()).execute(this);
            // new Thread().start(); ---> 不符合标准（阿里开发规范）
            // SD本地资源 返回Value，我本地资源，不需要异步线程，所以我可以自己返回

            // ....
        }
        return null;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;// HttpURLConnection内部已经是Okhttp，因为太高效了
        try {
            URL url = new URL(path);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            final int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
// TODO 注意：没有写这个代码： Bitmap 做缩放，做比例，做压缩，.....
//成功切换线程
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Value value = Value.getInstance();
                        value.setmBitmap(bitmap);
                        responListener.responseSuccess(value);
                    }
                });
            } else {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        responListener.responseException(new IllegalStateException("请求失败，请求码：" + responseCode));
                    }
                });
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
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
            if (null != httpURLConnection) {
                httpURLConnection.disconnect();
            }
        }
    }
}
