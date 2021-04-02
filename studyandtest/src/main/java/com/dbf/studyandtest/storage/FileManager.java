package com.dbf.studyandtest.storage;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by dbf on 2021/2/26
 * describe:
 */
public class FileManager {
    private static final String TAG = FileManager.class.getCanonicalName();
    public static String AppFileDir = "FC";
    public static final int getImagePathFlag = 0;
    public static final int getVideoPathFlag = 1;
    public static final String ImageMineType = "image/jpeg";
    public static final String VideoMineType = "video/3gp";

    /**
     * 保存Bitmap到本地相册
     */
    public static void saveImageToPhotos(Context context, Bitmap bmp) {
        String fileName = System.currentTimeMillis() + ".jpg";
        Uri uri = null;
        try {
            uri = getImageUri(context, fileName, "image/jpeg");
            FileOutputStream fos = getOutputStream(context, uri);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "saveImageToPhotos: e=" + e.getMessage());
        }
        if (uri == null) {
            return;
        }
        //没有兼容分区存储
        Luban.with(context)
                .load(uri)
                .ignoreBy(100)
                .filter(path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")))
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File zipFile) {
                        Log.i(TAG, "压缩后的封面地址：" + zipFile.getAbsolutePath());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "压缩封面失败~");
                    }
                }).launch();
    }

    public static FileOutputStream getOutputStream(@NonNull Context context, @NonNull Uri uri) throws FileNotFoundException {
        if (uri == null) {
            return null;
        }
        ParcelFileDescriptor fileDescriptor = context.getContentResolver().openFileDescriptor(uri, "w");
        FileOutputStream outputStream = new FileOutputStream(fileDescriptor.getFileDescriptor());
        return outputStream;
    }

    //创建获取私有目录
    public static String createPrivateFilePath(Context context, String fileName, int flag) {
        String dirType = null;
        switch (flag) {
            case getImagePathFlag:
                dirType = Environment.DIRECTORY_PICTURES;
                break;
            case getVideoPathFlag:
                dirType = Environment.DIRECTORY_MOVIES;
                break;
            default:
                break;
        }
        File imageDir = context.getExternalFilesDir(dirType);
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }
        return imageDir.getAbsolutePath() + File.separator + fileName;
    }

    //获取应用私有文件Uri
    public static Uri getPrivateFileUri(Context context, String fileName, int flag) {
        Uri uri = null;
        File file = null;
        if (fileName.contains(File.separator)) {
            file = new File(fileName);
        } else {
            file = new File(createPrivateFilePath(context, fileName, flag));
        }
        if (isDayuDengyuN()) {
            uri = FileProvider.getUriForFile(context, context.getPackageName(), file);//
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    //保存时注意使用全局的上下文，防止内存泄漏
    public static void saveFileToPrivate(Context context, Uri uri, String fileName, SaveComplete complete, int flag) {
        String filePath = createPrivateFilePath(context, fileName, flag);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Uri fileUri = (getPrivateFileUri(context, fileName, flag));
                    Log.i(TAG, "fileUri=" + fileUri.toString());
                    copyFile(context, uri, fileUri);
                    if (complete != null) {
                        complete.onComplete(fileUri, filePath);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i(TAG, "异常=" + e.getMessage());
                    if (complete != null) {
                        complete.onFiled(e);
                    }
                }
            }
        }).start();
    }

    //保存时注意使用全局的上下文，防止内存泄漏
    public static void saveFileToPrivate(Context context, Bitmap bitmap, String fileName, SaveComplete complete) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String fullFilePath = createPrivateFilePath(context, fileName, getImagePathFlag);
                    Uri fileUri = (getPrivateFileUri(context, fullFilePath, getImagePathFlag));
                    FileOutputStream fos = getOutputStream(context, fileUri);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                    if (!bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                    if (complete != null) {
                        complete.onComplete(fileUri, fullFilePath);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i(TAG, "异常=" + e.getMessage());
                    if (complete != null) {
                        complete.onFiled(e);
                    }
                }
            }
        }).start();
    }

    //保存时注意使用全局的上下文，防止内存泄漏
    public static void saveFileToGallery(Context context, Uri uri, Uri targetUri, SaveComplete complete) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i(TAG, "imageUri=" + targetUri.getPath());
                    copyFile(context, uri, targetUri);
                    if (complete != null) {
                        complete.onComplete(targetUri, targetUri.getPath());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i(TAG, "异常=" + e.getMessage());
                    if (complete != null) {
                        complete.onFiled(e);
                    }
                }
            }
        }).start();
    }

    //保存时注意使用全局的上下文，防止内存泄漏
    public static void saveFileToGallery(Context context, Bitmap bitmap, String fileName, SaveComplete complete) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Uri targetUri = (getImageUri(context, fileName, "image/jpeg"));
                    FileOutputStream fos = getOutputStream(context, targetUri);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                    if (!bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                    if (complete != null) {
                        complete.onComplete(targetUri, targetUri.getPath());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i(TAG, "异常=" + e.getMessage());
                    if (complete != null) {
                        complete.onFiled(e);
                    }
                }
            }
        }).start();
    }

    public interface SaveComplete {
        void onComplete(Uri fileUri, String filePath);

        void onFiled(Exception e);
    }

    public static void copyFile(Context context, Uri uri, Uri copyUri) throws Exception {
        InputStream is = context.getContentResolver().openInputStream(uri);
        FileOutputStream fos = getOutputStream(context, copyUri);
        byte[] buffer = new byte[1024];
        int byteread;
        while (-1 != (byteread = is.read(buffer))) {
            fos.write(buffer, 0, byteread);
        }
        is.close();
        fos.flush();
        fos.close();
    }

    /**
     * 视频-----------
     * 此接口用于获取保存共享视频的输出流，推荐！！！
     * <p>
     * 在低于29的系统上采用getSaveToGalleryVideoUri的方式保存共享视频，会有文件名不能定制、视频保存类型是.3gp、视频保存在video文件夹等问题
     * 所以在低版本上采用文件路径的方式写入数据。在低于29的系统上采用文件路径的方式是没有问题的，因为在这些系统上没有分区存储的概念
     * 以及，getExternalStoragePublicDirectory函数可用
     *
     * @param context
     * @param videoName
     * @param mineType
     * @return
     * @throws FileNotFoundException
     */
    public static Uri getVideoUri(@NonNull Context context, @NonNull String videoName, @NonNull String mineType) throws FileNotFoundException {
        //先在MediaStore中查询，有的话直接返回
        Uri uri = querySpecialVideoUri(context, videoName);
        if (uri != null) {
            return uri;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            uri = getSaveToGalleryVideoUri(context, videoName, mineType);
            if (uri != null) {
                return uri;
            }
        } else {
            if (TextUtils.isEmpty(videoName)) {
                videoName = String.valueOf(System.currentTimeMillis());
            }
            //通过显示路径方式共享媒体的时候，是需要指定文件后缀，要不然下载文件会没有后缀名
            if (!TextUtils.isEmpty(mineType)) {
                String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mineType);
                if (videoName.contains(".")) {
                    videoName = videoName.substring(0, videoName.indexOf(".")) + "." + extension;
                } else {
                    videoName += "." + extension;
                }
            }

            /**
             * 直接路径的方式，组合出的文件路径，路径中的文件夹一定要存在，否则转成FileOutputStream的时候会报FileNotFoundException
             * 即便是通过DATA注册到MediaStore中，也是如此
             */
            String rootPath = getMediaPath(getVideoPathFlag);
            String videoPath = null;
            if (rootPath.endsWith(File.separator)) {
                videoPath = rootPath + videoName;
            } else {
                videoPath = rootPath + File.separator + videoName;
            }

            //通过DATA字段在MediaStore中注册一下
            ContentValues values = new ContentValues();
            values.put(MediaStore.Video.Media.DISPLAY_NAME, videoName);
            values.put(MediaStore.Video.Media.MIME_TYPE, mineType);
            values.put(MediaStore.Video.Media.DATA, videoPath);
            values.put(MediaStore.Video.Media.DATE_MODIFIED, System.currentTimeMillis() / 1000);
            uri = context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
            if (uri != null) {
                return uri;
            }
        }
        return null;
    }


    /**
     * 保存共享媒体资源，必须使用先在MediaStore创建表示视频保存信息的Uri，然后通过Uri写入视频数据的方式。
     * 在"分区存储"模型中，这是官方推荐的，因为在Android 10禁止通过File的方式访问媒体资源，Android 11又允许了
     * 从Android 10开始默认是分区存储模型
     * <p>
     * <p>
     * 说明：
     * 此方法中MediaStore默认的保存目录是/storage/emulated/0/video
     * 而Environment.DIRECTORY_MOVIES的目录是/storage/emulated/0/Movies
     *
     * @param context
     * @return
     */
    public static Uri getSaveToGalleryVideoUri(Context context, String videoName, String mineType) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Video.Media.DISPLAY_NAME, videoName);
        values.put(MediaStore.Video.Media.MIME_TYPE, mineType);
        values.put(MediaStore.Video.Media.DATE_MODIFIED, System.currentTimeMillis() / 1000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Video.Media.RELATIVE_PATH, Environment.DIRECTORY_MOVIES);
        }

        Uri uri = context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
        return uri;
    }

    public static Uri querySpecialVideoUri(Context context, String fileName) {
        Cursor cursor = context.getApplicationContext().getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Video.Media._ID},
                MediaStore.Video.Media.DISPLAY_NAME + " = ?"
//                null
                ,
                new String[]{fileName}
//                null
                ,
                null
        );
        int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
        while (cursor.moveToNext()) {
            long id = cursor.getLong(idColumn);
            Uri contentUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);
            Log.i(TAG, "videoUri=" + contentUri.toString());
        }
        return null;
    }


    /**
     * 图片-----------
     */
    public static Uri getImageUri(@NonNull Context context, @NonNull String imageName, @NonNull String mineType) throws FileNotFoundException {
        //先在MediaStore中查询，有的话直接返回
        Uri uri = querySpecialImageUri(context, imageName);
        if (uri != null) {
            return uri;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            uri = getSaveToGalleryImageUri(context, imageName, mineType);
            if (uri != null) {
                return uri;
            }
        } else {
            if (TextUtils.isEmpty(imageName)) {
                imageName = String.valueOf(System.currentTimeMillis());
            }
            //通过显示路径方式共享媒体的时候，是需要指定文件后缀，要不然下载文件会没有后缀名
            if (!TextUtils.isEmpty(mineType)) {
                String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mineType);
                if (imageName.contains(".")) {
                    imageName = imageName.substring(0, imageName.indexOf(".")) + "." + extension;
                } else {
                    imageName += "." + extension;
                }
            }

            /**
             * 直接路径的方式，组合出的文件路径，路径中的文件夹一定要存在，否则转成FileOutputStream的时候会报FileNotFoundException
             * 即便是通过DATA注册到MediaStore中，也是如此
             */
            String rootPath = getMediaPath(getImagePathFlag);
            String imagePath = null;
            if (rootPath.endsWith(File.separator)) {
                imagePath = rootPath + imageName;
            } else {
                imagePath = rootPath + File.separator + imageName;
            }

            //通过DATA字段在MediaStore中注册一下
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName);
            values.put(MediaStore.Images.Media.MIME_TYPE, mineType);
            values.put(MediaStore.Images.Media.DATA, imagePath);
            values.put(MediaStore.Images.Media.DATE_MODIFIED, System.currentTimeMillis() / 1000);
            uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri != null) {
                return uri;
            }
        }
        return null;
    }

    /**
     * 图片-----------
     */
    public static Uri getSaveToGalleryImageUri(Context context, String imageName, String mineType) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName);
        values.put(MediaStore.Images.Media.MIME_TYPE, mineType);
        values.put(MediaStore.Images.Media.DATE_MODIFIED, System.currentTimeMillis() / 1000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
        }
        Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        return uri;
    }


    //IMG_20200726_184657.jpg 传名字+后缀
    public static Uri querySpecialImageUri(Context context, String fileName) {
        Cursor cursor = context.getApplicationContext().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DISPLAY_NAME + " = ?"
//                null
                ,
                new String[]{fileName}
//                null
                ,
                null
        );
        int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
        while (cursor.moveToNext()) {
            long id = cursor.getLong(idColumn);
            Uri contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
            Log.i(TAG, "imageUri=" + contentUri.toString());
        }
        return null;
    }

    private static String getMediaPath(int flag) {
        String dir = "";
        switch (flag) {
            case 0:
                dir = Environment.DIRECTORY_PICTURES;
                break;
            case 1:
                dir = Environment.DIRECTORY_MOVIES;
                break;
            default:
                break;
        }
        File path = Environment.getExternalStoragePublicDirectory(dir);
        if (!path.exists()) {
            path.mkdirs();
        }
        String pathStr = path.getAbsolutePath();
        File file = new File(pathStr);
        if (!file.exists()) {
            file.mkdirs();
        }
        return pathStr;
    }

    private static boolean isDayuDengyuN() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }
}
