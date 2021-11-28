package com.dbf.studyandtest.recordscreen;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.media.projection.MediaProjection;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ScreenUtils;
import com.dbf.studyandtest.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

public class RecordScreenService extends Service {
    private final String TAG = RecordScreenService.class.getCanonicalName();
    private MediaProjection mediaProjection;
    private boolean isLiveing;
    private MediaMuxer mMuxer;
    long timestamp;
    public RecordScreenService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isLiveing = true;
        createNotificationChannel();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new ServiceObject();
    }

    public void startRecord(MediaProjection mediaProjection) {
        if (mediaProjection == null) {
            Log.i(TAG, "onActivityResult: mediaProjection==null");
            return;
        }
        this.mediaProjection = mediaProjection;
        this.mediaProjection.registerCallback(new ProjectionCallback(), new Handler(getMainLooper()));
        startCapturing(mediaProjection);
    }

    public void stopRecord() {
        isLiveing = false;
    }

    private void startCapturing(MediaProjection mediaProjection) {
        File dir = getSavingDir();
        if (!dir.exists()) {
            Log.i(TAG, "startCapturing: 开始创建文件夹");
            dir.mkdirs();
        } else {
            Log.i(TAG, "startCapturing: 已创建文件夹");
        }
        String fileName = System.currentTimeMillis() + ".mp4";
        String moviesPath = getSavingDir().getAbsolutePath();
        String filePath;
        if (moviesPath.endsWith(File.separator)) {
            filePath = moviesPath + fileName;
        } else {
            filePath = moviesPath + File.separator + fileName;
        }
        Log.i(TAG, "startCapturing: filePath=" + filePath);
//        try {
//            Uri uri = getVideoUri(this, fileName, "video/mp4");
//            Log.i(TAG, "startCapturing: uri.toString=" + uri.toString());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            Log.i(TAG, "startCapturing: FileNotFoundException");
//            return;
//        }
        try {
            mMuxer = new MediaMuxer(filePath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);

            int screenWidth = ScreenUtils.getScreenWidth();
            int screenHeight = ScreenUtils.getScreenHeight();
            screenWidth = 360;
            screenHeight = 480;
            String MIMETYPE = MediaFormat.MIMETYPE_VIDEO_AVC;
            MediaFormat format = MediaFormat.createVideoFormat(MIMETYPE, screenWidth, screenHeight);
            format.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
            format.setInteger(MediaFormat.KEY_BIT_RATE, 800_000);
            format.setInteger(MediaFormat.KEY_FRAME_RATE, 15);
            format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 1);
            //编码数据源的格式
            format.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);


            MediaCodec mediaCodec = MediaCodec.createEncoderByType(MIMETYPE);
            mediaCodec.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
            Surface surface = mediaCodec.createInputSurface();

            int screenDpi = ScreenUtils.getScreenDensityDpi();
            screenDpi = 1;
            VirtualDisplay virtualDisplay = mediaProjection.createVirtualDisplay(TAG + "-display",
                    screenWidth, screenHeight, screenDpi, DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC,
                    surface, null, null);

            RecordScreenExecutors.getInstance().execute(() -> {
                mediaCodec.start();
                int videoTrackIndex = -1;
                while (isLiveing) {
//                    //todo: mediacodec的关键帧问题
//                    // 手动刷新关键帧
//                    if (timestamp != 0) {
//                        if (System.currentTimeMillis() - timestamp >= 2_000) {
//                            // 刷新一次关键帧
//                            Bundle bundle = new Bundle();
//                            //立即刷新 让下一帧是关键帧
//                            bundle.putInt(MediaCodec.PARAMETER_KEY_REQUEST_SYNC_FRAME, 0);
//                            mediaCodec.setParameters(bundle);
//                            timestamp = System.currentTimeMillis();
//                        }
//                    } else {
//                        timestamp = System.currentTimeMillis();
//                    }


                    MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
                    //获得编码之后的数据
                    //  队列下标
                    int index = mediaCodec.dequeueOutputBuffer(bufferInfo, 10);
                    if (index == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                        MediaFormat newFormat = mediaCodec.getOutputFormat();
                        videoTrackIndex = mMuxer.addTrack(newFormat);
                        mMuxer.start();
                    } else if (index >= 0) {
                        //成功取出了编码数据
                        Log.i(TAG, "startCapturing: 成功取出了编码数据");
                        ByteBuffer encodedData = mediaCodec.getOutputBuffer(index);
                        if ((bufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
                            bufferInfo.size = 0;
                            Log.i(TAG, "startCapturing:bufferInfo.size = 0");
                        }
                        if (bufferInfo.size == 0) {
                            encodedData = null;
                            Log.i(TAG, "startCapturing: encodedData = null");
                        }
                        if (encodedData != null) {
                            encodedData.position(bufferInfo.offset);
                            encodedData.limit(bufferInfo.offset + bufferInfo.size);
                            mMuxer.writeSampleData(videoTrackIndex, encodedData, bufferInfo);
                        }
                        mediaCodec.releaseOutputBuffer(index, false);
                    }
                }

                isLiveing = false;
                mediaCodec.stop();
                mediaCodec.release();
                virtualDisplay.release();
                mediaProjection.stop();
                stopForeground(true);
                stopSelf();


            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Uri getVideoUri(@NonNull Context context, @NonNull String videoName, @NonNull String mineType) throws FileNotFoundException {
        if (TextUtils.isEmpty(videoName)) {
            videoName = String.valueOf(System.currentTimeMillis());
        }
        ContentValues values = new ContentValues();
        values.put(MediaStore.Video.Media.DISPLAY_NAME, videoName);
        values.put(MediaStore.Video.Media.MIME_TYPE, mineType);
        values.put(MediaStore.Video.Media.DATE_MODIFIED, System.currentTimeMillis() / 1000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Video.Media.RELATIVE_PATH, Environment.DIRECTORY_MOVIES + File.separator + "ScreenMovies");
        } else {
            //通过显示路径方式共享媒体的时候，是需要指定文件后缀，要不然下载文件会没有后缀名
            if (!TextUtils.isEmpty(mineType)) {
                String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mineType);
                if (videoName.contains(".")) {
                    videoName = videoName.substring(0, videoName.indexOf(".")) + "." + extension;
                } else {
                    videoName += "." + extension;
                }
            }
            String moviesPath = getSavingDir().getAbsolutePath();
            String videoPath;
            if (moviesPath.endsWith(File.separator)) {
                videoPath = moviesPath + videoName;
            } else {
                videoPath = moviesPath + File.separator + videoName;
            }
            values.put(MediaStore.Video.Media.DATA, videoPath);
        }
        Uri uri = context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
        return uri;

    }

    public File getSavingDir() {
        return new File(getExternalFilesDir(Environment.DIRECTORY_MOVIES).getAbsolutePath() + File.separator + "ScreenMovies");
    }


    class ProjectionCallback extends MediaProjection.Callback {
        @Override
        public void onStop() {
            super.onStop();
            Log.i(TAG, "ProjectionCallback_onStop: ");
            stopForeground(true);
        }
    }

    class ServiceObject extends Binder {
        public RecordScreenService getService() {
            return RecordScreenService.this;
        }

    }

    private void createNotificationChannel() {
        Notification.Builder builder = new Notification.Builder(this.getApplicationContext()); //获取一个Notification构造器

        builder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher)) // 设置下拉列表中的图标(大图标)
                //.setContentTitle("SMI InstantView") // 设置下拉列表里的标题
                .setSmallIcon(R.mipmap.ic_launcher) // 设置状态栏内的小图标
                .setContentText("is running......") // 设置上下文内容
                .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间

        /*以下是对Android 8.0的适配*/
        //普通notification适配
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId("notification_id");
        }
        //前台服务notification适配
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel("notification_id", "notification_name", NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = builder.build(); // 获取构建好的Notification
        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
        startForeground(110, notification);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isLiveing = false;
        Log.i(TAG, "RecodScreenService_onDestroy: ");
    }
}