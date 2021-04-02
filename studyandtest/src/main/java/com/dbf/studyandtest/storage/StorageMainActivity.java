package com.dbf.studyandtest.storage;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dbf.studyandtest.R;
import com.dbf.studyandtest.databinding.ActivityStorageMainBinding;

import java.io.File;
import java.io.FileNotFoundException;

public class StorageMainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = StorageMainActivity.class.getCanonicalName();
    private ActivityStorageMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStorageMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.reCordVideo.setOnClickListener(this);
        Glide.with(StorageMainActivity.this)
                .load(R.mipmap.ic_launcher)
                .into(binding.imageView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reCordVideo:
//                Glide.with(StorageMainActivity.this)
//                        .asBitmap()
//                        .load(R.mipmap.ic_launcher_round)
//                        .into(new CustomTarget<Bitmap>() {
//
//
//                            @Override
//                            public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
//                                int width = bitmap.getWidth();
//                                int height = bitmap.getHeight();
//                                Log.i(TAG, "width " + width); //200px
//                                Log.i(TAG, "height " + height); //200px
//                                Log.i(TAG, bitmap.getByteCount() + "---");
//                                FileManager.saveImageToPhotos(StorageMainActivity.this, bitmap);
//                            }
//
//                            @Override
//                            public void onLoadCleared(@Nullable Drawable placeholder) {
//
//                            }
//                        });


                goToRecordVideo();
                break;
            default:
                break;
        }
    }

    private Uri videoUri = null;
    private File videoFile = null;
    private String videoFileName = "";
    private String videoFileNameSuffix = ".mp4";

    private void goToRecordVideo() {

        Intent captureIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        // 判断是否有相机
        if (captureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                videoFileName = String.valueOf(System.currentTimeMillis());
//                videoUri = FileManager.getVideoUri(this, videoFileName, "video/mp4");
                videoUri = FileManager.getPrivateFileUri(this, videoFileName + videoFileNameSuffix, FileManager.getVideoPathFlag);
                Log.i(TAG, "goToRecordVideo:videoUri= " + videoUri);
            } catch (Exception e) {
                e.printStackTrace();
                Log.i(TAG, "goToRecordVideo: 异常" + e.getMessage());
            }
            if (videoUri != null) {
                registerForActivityResult(new ActivityResultContracts.TakeVideo() {
                }, new ActivityResultCallback<Bitmap>() {
                    @Override
                    public void onActivityResult(Bitmap result) {
                        try {
                            FileManager.saveFileToGallery(StorageMainActivity.this, videoUri, FileManager.getVideoUri(StorageMainActivity.this, videoFileName, "video/mp4"), new FileManager.SaveComplete() {
                                @Override
                                public void onComplete(Uri fileUri, String filePath) {
                                    Log.i(TAG, "onComplete: 共享到公共目录完成");
                                }

                                @Override
                                public void onFiled(Exception e) {
                                    Log.i(TAG, "onComplete: 共享到公共目录失败");
                                }
                            });
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Log.i(TAG, "onActivityResult: videoFilePath=" + videoUri);
                        Glide.with(StorageMainActivity.this)
                                .load(videoUri)
                                .into(binding.imageView);

                        Glide.with(StorageMainActivity.this)
                                .asBitmap()
                                .load(videoUri)
                                .into(new CustomTarget<Bitmap>() {


                                    @Override
                                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                                        int width = bitmap.getWidth();
                                        int height = bitmap.getHeight();
                                        Log.i(TAG, "width " + width); //200px
                                        Log.i(TAG, "height " + height); //200px
                                        Log.i(TAG, bitmap.getByteCount() + "---");
                                        FileManager.saveImageToPhotos(StorageMainActivity.this, bitmap);
                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {

                                    }
                                });

                    }
                }).launch(videoUri);
            }
        }
    }

}